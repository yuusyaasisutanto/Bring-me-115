package com.yuusyaasisutanto.bringme115;

import com.yuusyaasisutanto.bringme115.content.items.testbomb.BM115TestItemBombCapability;
import com.yuusyaasisutanto.bringme115.content.items.testbomb.BM115TestItemBombHandler;
import com.yuusyaasisutanto.bringme115.content.network.BM115PrimitiveMachineButtonPacket;
import com.yuusyaasisutanto.bringme115.content.register.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
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

        // パケット通信の登録
        BM115ModMessages.register();

        BM115ItemRegister.REGISTRY.register(bus);
        BM115BlockRegister.REGISTRY.register(bus);
        BM115BlockEntityRegister.REGISTRY.register(bus);
        BM115TabRegister.REGISTRY.register(bus);
        BM115ScreenRegister.REGISTRY.register(bus);
        bus.<RegisterCapabilitiesEvent>addListener(BM115TestItemBombCapability::register);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class ,BM115TestItemBombHandler::attach);
    }
}
