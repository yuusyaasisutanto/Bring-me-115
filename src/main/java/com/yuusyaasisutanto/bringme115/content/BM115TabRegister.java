package com.yuusyaasisutanto.bringme115.content;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BM115TabRegister {
    public static final DeferredRegister<CreativeModeTab> REGISTRY
            = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BringMe115.ID);

    public static final RegistryObject<CreativeModeTab> MAIN = REGISTRY.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.bringme115.main"))
            .icon(() -> new ItemStack(BM115ItemRegister.CODINGPRACTICE.get()))
            .displayItems((parameters, output) -> {
                BM115ItemRegister.MAIN.forEach(object -> output.accept(object.get()));
            })
            .build());

}
