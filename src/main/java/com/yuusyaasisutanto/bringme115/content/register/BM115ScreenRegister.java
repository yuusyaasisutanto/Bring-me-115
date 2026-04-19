package com.yuusyaasisutanto.bringme115.content.register;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BM115ScreenRegister {
    public static final DeferredRegister<MenuType<?>> REGISTRY
            = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BringMe115.ID);

    public static final RegistryObject<MenuType<BM115PrimitivePaPMenu>> PRIMITIVE_PAP_MENU
            = registerMenuType("primitive_pap_menu", BM115PrimitivePaPMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, MenuType.MenuSupplier<T> factory){
        return REGISTRY.register(name, () ->  new MenuType(factory, FeatureFlags.DEFAULT_FLAGS));
    }

    public static void register(IEventBus eventBus){
        REGISTRY.register(eventBus);
    }
}
