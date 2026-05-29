package com.yuusyaasisutanto.bringme115.content.items.implemented.element115related;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

public class BM115Element115MaskedItem extends Item {
    public BM115Element115MaskedItem(Properties p_41383_) {
        super(p_41383_);
    }

    //一旦の動作確認コピペ、後で逐次聞きながら再実装
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new BlockEntityWithoutLevelRenderer(
                    Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                    Minecraft.getInstance().getEntityModels()
            ) {
                @Override
                public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
                    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                    BakedModel model = itemRenderer.getModel(stack, null, null, 0);

                    poseStack.pushPose();

                    // 1. まず普通にバニラのモデル（mask と overlay）を描画する
                    // これにより、画面のバッファに「マスクの形（アルファ値）」が書き込まれます
                    itemRenderer.render(stack, displayContext, false, poseStack, bufferSource, packedLight, packedOverlay, model);

                    // 2. グラフィックボードのブレンドモードを「マスク乗算」に切り替える
                    RenderSystem.enableBlend();
                    // 先に描画された目的の形（DST_ALPHA）がある場所にのみ、次の画像を適用する設定
                    RenderSystem.blendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);
                    RenderSystem.depthFunc(GL11.GL_EQUAL);

                    // 3. 使い回したいアニメーションテクスチャ（elemental115_base）を上から1枚スタンプする
                    // マイクラのシステムが mcmeta を読んで自動的にコマを進めてくれているテクスチャをそのまま使います
                    var animationBuffer = bufferSource.getBuffer(RenderType.text(
                            new net.minecraft.resources.ResourceLocation(BringMe115.ID, "textures/block/elemental115_base")
                    ));

                    // アイテムの四角い平面全体にアニメーションをベタ塗り（ブレンドの力で勝手に型抜きされます）
                    // ※RenderType.text などの平面描画バッファを利用して重ね合わせます

                    // 4. ブレンドモードを元の通常状態に戻す
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.depthFunc(GL11.GL_LEQUAL);
                    poseStack.popPose();
                }
            };

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}

