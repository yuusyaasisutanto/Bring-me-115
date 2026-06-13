package com.yuusyaasisutanto.bringme115.content.register;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BM115SoundRegister {
    public static final DeferredRegister<SoundEvent> REGISTRY
            = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BringMe115.ID);

    public static final RegistryObject<SoundEvent> U_FOUND_EE =
            register("u_found_ee");


    // 他のイベントと同じように何らかでリストを必要とする場合に備えて冗長に
    public static RegistryObject<SoundEvent> register(String name){
        RegistryObject<SoundEvent> sound = REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent( new ResourceLocation(BringMe115.ID, name)));
        return sound;
    }


}
