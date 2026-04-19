package com.yuusyaasisutanto.bringme115.content.subscriber;


import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115ScreenRegister;
import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT )
public class BM115ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        blockScreenRegister();
    }

    private static void blockScreenRegister(){
        MenuScreens.register(BM115ScreenRegister.PRIMITIVE_PAP_MENU.get(), BM115PrimitivePaPScreen::new);
    }
}
