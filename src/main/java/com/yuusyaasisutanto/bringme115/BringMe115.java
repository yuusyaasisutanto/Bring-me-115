package com.yuusyaasisutanto.bringme115;

import com.yuusyaasisutanto.bringme115.content.config.BM115ConfigBuilder;
import com.yuusyaasisutanto.bringme115.content.items.implemented.testbomb.BM115TestItemBombCapability;
import com.yuusyaasisutanto.bringme115.content.items.implemented.testbomb.BM115TestItemBombHandler;
import com.yuusyaasisutanto.bringme115.content.pap.BM115PaPLevelToDamage;
import com.yuusyaasisutanto.bringme115.content.register.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jline.utils.Log;

@Mod(BringMe115.ID)
public class BringMe115 {

    public static final String ID="bringme115";

    @SuppressWarnings("removal")
    public BringMe115(){
        this(FMLJavaModLoadingContext.get());
    }

    public BringMe115(FMLJavaModLoadingContext context){
        IEventBus bus = context.getModEventBus();

        // コンフィグファイルの登録
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BM115ConfigBuilder.SPEC, "bringme115-common.toml");

        // パケット通信の登録
        BM115ModMessages.register();

        BM115ItemRegister.REGISTRY.register(bus);
        BM115BlockRegister.REGISTRY.register(bus);
        BM115BlockEntityRegister.REGISTRY.register(bus);
        BM115TabRegister.REGISTRY.register(bus);
        BM115ScreenRegister.REGISTRY.register(bus);
        BM115SoundRegister.REGISTRY.register(bus);
        bus.<RegisterCapabilitiesEvent>addListener(BM115TestItemBombCapability::register);
        bus.addListener(this::onConfigLoad);
        bus.addListener(this::onConfigReload);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class ,BM115TestItemBombHandler::attach);
    }

    // コンフィグ（ロード時）
    private void onConfigLoad(final ModConfigEvent.Loading event){
        if (event.getConfig().getSpec() == BM115ConfigBuilder.SPEC){
            BM115PaPLevelToDamage.loadMultipliersFromConfig();
        }
    }

    // コンフィグ（リロード時）
    private void onConfigReload(final ModConfigEvent.Reloading event){
        if (event.getConfig().getSpec() == BM115ConfigBuilder.SPEC){
            BM115PaPLevelToDamage.loadMultipliersFromConfig();
        }
    }
}
