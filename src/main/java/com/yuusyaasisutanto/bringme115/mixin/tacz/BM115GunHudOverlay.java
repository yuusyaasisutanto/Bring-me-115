package com.yuusyaasisutanto.bringme115.mixin.tacz;

import com.tacz.guns.client.gui.overlay.GunHudOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GunHudOverlay.class)
public class BM115GunHudOverlay {

    // 元々ここのコードはINVOKEでのInjectを行っていた
    // その場合通常環境での動作が正常に行われなかった
    // 以下はGeminiに聞いた「回答」の要約である。

    // 原因は「remap = false を指定していること」と、「ターゲットにしている blit メソッドのシグネチャ（引数の型と数）が、製品版で別物に変わってしまうこと」のダブルパンチです。
    // 開発環境では、TaCZ側もこのパターンの blit を呼んでいるためマッチします。
    // しかし、製品版（難読化環境）にビルドされる際、マイクラバニラの GuiGraphics クラスのメソッド名はすべて m_xxxxx_ という機械用の名前にリマップされます。

    // ここで remap = false を指定しているため、Mixinシステムは製品版の中でも「blit（人間の名前）のまま」メソッドを探そうとします。
    // しかし、製品版に blit という名前のメソッドは存在せず、すべて難読化名に変わっているため、ターゲットが見つからずにインジェクションが静かに無視されてしまうのです。

    // 「じゃあ remap = true にすればいいのでは？」となりますが
    // そうすると今度は相手側（TaCZ）のクラス名やメソッド名（GunHudOverlay や render）が、開発環境のコンパイル時点で
    // 『これはサードパーティ製のModだから、マイクラバニラのように自動リマップできないよ』とエラーを出してしまうというジレンマに陥ります。

    // この問題を回避する最もスマートで頑丈な方法は、前回の代替案でも少し触れた @At("TAIL")（メソッドの一番最後）へのインジェクション に切り替えることです。
    // TAIL を指定すれば、バニラの blit のような難読化の影響を強く受ける内部メソッドをピンポイントで指定する必要がなくなります。
    // また、HUDの一番最後に上書き描画する形になるため、文字が他のUIに隠れる心配もありません。


    // Q.if文でのネストがかなりあるのだが、TAILで大丈夫なのだろうか

    // @At("TAIL") がターゲットにするのは、プログラム的な「条件分岐の終わり」ではなく、そのメソッドの一番最後にある return 命令（バイトコードの RETURN）の直前です。
    // TaCZの GunHudOverlay.render メソッドがどれだけ複雑に if 文で分岐していようとも、最終的にメソッドの処理が終わる出口（} の場所）は必ず1つに収束します。

    // TAIL を使う上で、1点だけコードの構造上注意しなければいけない仕様があります。
    // それは、メソッドの途中で処理を切り上げる 「途中抜けの return;」 が相手のメソッド内にあるかどうかです。
    // それがある場合はTAILに到達しないので、処理が行われない。しかし今回は処理が行われないことが正解なので問題ない。


    @Inject(method = "render", at = @At(value = "TAIL") ,remap = false)
    public void BM115InjectedPaPHUDtoRender(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height, CallbackInfo ci){
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        LocalPlayer player = mc.player;
        // 対ぬるぽ装甲
        if (player == null){return;}
        ItemStack stack = player.getMainHandItem();

        CompoundTag tag = stack.getTag();
        int paplvl = tag != null ? tag.getCompound("BM115Modify").getInt("PaPlvl") : 0;

        if (paplvl > 0) {
            Component papedTextIndicator = Component.translatable("hud.bringme115.tacz.mixin.is_paped");
            Component papedLevelIndicator = Component.translatable("hud.bringme115.tacz.mixin.is_paped.level")
                                                        .append(Component.literal(String.valueOf(paplvl)));

            float scale = 0.5F;
            float backTo1 = 1.0F / scale;

            graphics.pose().pushPose();
                graphics.pose().scale(scale, scale, 1.0F);
                // height - 44
                graphics.drawString(font,papedTextIndicator , (int) ((width - 100) * backTo1), (int) ((height - 58) * backTo1), 0xFFFFFF, false);
            graphics.pose().popPose();

            graphics.pose().pushPose();
                graphics.drawString(font,papedLevelIndicator , width - 55, height - 53, 0xFFFFFF, false);
            graphics.pose().popPose();
        }
    }
}
