package com.yuusyaasisutanto.bringme115;

import com.yuusyaasisutanto.bringme115.content.BM115ItemRegister;
import com.yuusyaasisutanto.bringme115.content.BM115TabRegister;
import com.yuusyaasisutanto.bringme115.content.testbomb.BM115TestItemBombCapability;
import com.yuusyaasisutanto.bringme115.content.testbomb.BM115TestItemBombHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
        BM115TabRegister.REGISTRY.register(bus);
        bus.<RegisterCapabilitiesEvent>addListener(BM115TestItemBombCapability::register);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class ,BM115TestItemBombHandler::attach);
    }
}
