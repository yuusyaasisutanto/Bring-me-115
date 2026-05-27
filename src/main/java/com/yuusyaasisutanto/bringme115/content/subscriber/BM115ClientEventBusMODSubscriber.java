package com.yuusyaasisutanto.bringme115.content.subscriber;


import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.pap.itemicon.BM115ItemIconPaPLevelindicator;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ScreenRegister;
import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.tacz.guns.init.ModItems.MODERN_KINETIC_GUN;
import static com.yuusyaasisutanto.bringme115.content.items.implemented.aetherium_crystal.BM115AetheriumCrystal.getPaPLevel;

//mod側
@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT )
public class BM115ClientEventBusMODSubscriber {

    // セットアップに乗じて設定する諸々
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            blockScreenRegister();
            setAetheriumCrystalTextureChangingByNBT();
            setMultiLayerBlockRenderTypeToTrans();
        });
    }


    // アイテムアイコンがGUIで表示されるときに発火するイベントに便乗してPaPレベルを登録させるイベント
    @SubscribeEvent
    public static void renderPaPLevelToPaPedItem(RegisterItemDecorationsEvent event){
        event.register(BM115ItemRegister.AETHERIUM_CRYSTAL.get(), new BM115ItemIconPaPLevelindicator());
        event.register(MODERN_KINETIC_GUN.get(), new BM115ItemIconPaPLevelindicator());
    }




    // メニュースクリーンの登録用の場所
    private static void blockScreenRegister(){
        MenuScreens.register(BM115ScreenRegister.PRIMITIVE_PAP_MENU.get(), BM115PrimitivePaPScreen::new);
    }

    // NBTによってテクスチャを変える用のアイテムの場所（主にAetheriumCrystal用）
    private static void setAetheriumCrystalTextureChangingByNBT(){
        // {return item.hasTag() ? (float)getPaPLevel(item) : 0.0F}
        // ↑って最後の所はやろうとしたんだけど、ラムダ式に置換可能って言われたのでそうしてる。
        // IDEAが優秀だから補完してくれたけど自分でも忘れないようにここにメモっておく。
        ItemProperties.register(BM115ItemRegister.AETHERIUM_CRYSTAL.get(),
                new ResourceLocation(BringMe115.ID, "pap_level_texture"),
                (item, level, entity, seed) -> item.hasTag() ? (float)getPaPLevel(item) : 0.0F
        );
    }

    // translucent用のブロックを収集するリスト変数を宣言作成
    private static final Set<Block> TRANS_BLOCK_LIST = new HashSet<>();

    // 多層レイヤー構造を持つブロックのレンダリングタイプをCutoutにする場所
    private static void setMultiLayerBlockRenderTypeToTrans(){

        // cutout(アルファ二値化)
        //深層元素115鉱石
        ItemBlockRenderTypes.setRenderLayer(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get(), RenderType.cutout());


        // translucent(生アルファ) - 視線を合わせた時に枠線に沿ってテクスチャが透明化する。現行だと多分解決しない。
        transBlockRenderTypeSetting(BM115BlockRegister.RAW_ELEMENT115_BLOCK);
    }

    // translusentの場合枠線用の登録と同時に枠線を透明化させない処理を行いたいため
    // 一括で処理するヘルパーメソッドをこっちで作成
    private static void transBlockRenderTypeSetting(RegistryObject<Block> blockRegistryObject){
        Block block = blockRegistryObject.get();
        ItemBlockRenderTypes.setRenderLayer(block,
                renderType -> renderType == RenderType.translucent() || renderType == RenderType.solid()
        );

        TRANS_BLOCK_LIST.add(block);
    }


    // 内部処理用インタークラス
    // 一つのヘルパーメソッドに処理を統合するためここで指定
    @Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
    public static class internalFORGESubscriber {

        @SubscribeEvent
        public static void onRenderHighlight(RenderHighlightEvent.Block event){
            BlockPos pos = event.getTarget().getBlockPos();
            BlockState state = event.getCamera().getEntity().level().getBlockState(pos);

            if (TRANS_BLOCK_LIST.contains(state.getBlock())){
                // System.out.println("getblock worked");
                // 視線を向けた時の枠線部分が透明になっちゃうのをどうにかしたかったけど
                // 無理だったのを経験として残すだけ残しておく場所
                // 今後のアプデとかで消す可能性は大
            }
        }

    }
}


