package com.yuusyaasisutanto.bringme115;

import com.yuusyaasisutanto.bringme115.content.BM115ItemRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BringMe115.ID)
public class BringMe115 {
    public static final String ID="bringme115";

    @SuppressWarnings("removal")
    public BringMe115(){
        this(FMLJavaModLoadingContext.get());
    }

    public BringMe115(FMLJavaModLoadingContext context){
        IEventBus bus = context.getModEventBus();
        BM115ItemRegister.REGISTRY.register(bus);
    }
}
