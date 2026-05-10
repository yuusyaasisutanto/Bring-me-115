package com.yuusyaasisutanto.bringme115.content.subscriber;


import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.items.aetherium_crystal.BM115AetheriumCrystal;
import com.yuusyaasisutanto.bringme115.content.pap.itemicon.BM115ItemIconPaPLevelindicator;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ScreenRegister;
import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPScreen;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ItemDecoratorHandler;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.tacz.guns.init.ModItems.MODERN_KINETIC_GUN;
import static com.yuusyaasisutanto.bringme115.content.items.aetherium_crystal.BM115AetheriumCrystal.getPaPLevel;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT )
public class BM115ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            blockScreenRegister();
            setAetheriumCrystalTextureChangingByNBT();
        });
    }

    private static void blockScreenRegister(){
        MenuScreens.register(BM115ScreenRegister.PRIMITIVE_PAP_MENU.get(), BM115PrimitivePaPScreen::new);
    }

    private static void setAetheriumCrystalTextureChangingByNBT(){
        // {return item.hasTag() ? (float)getPaPLevel(item) : 0.0F}
        // ↑って最後の所はやろうとしたんだけど、ラムダ式に置換可能って言われたのでそうしてる。
        // IDEAが優秀だから補完してくれたけど自分でも忘れないようにここにメモっておく。
        ItemProperties.register(BM115ItemRegister.AETHERIUM_CRYSTAL.get(),
                new ResourceLocation(BringMe115.ID, "pap_level_texture"),
                (item, level, entity, seed) -> item.hasTag() ? (float)getPaPLevel(item) : 0.0F
        );

    }

    @SubscribeEvent
    public static void renderPaPLevelToPaPedItem(RegisterItemDecorationsEvent event){
        event.register(BM115ItemRegister.AETHERIUM_CRYSTAL.get(), new BM115ItemIconPaPLevelindicator());
        event.register(MODERN_KINETIC_GUN.get(), new BM115ItemIconPaPLevelindicator());
    }
}
