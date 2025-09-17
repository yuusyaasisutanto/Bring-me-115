package com.yuusyaasisutanto.bringme115.content;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BM115BlockRegister {
    public static final DeferredRegister<Block> BLOCK
            = DeferredRegister.create(ForgeRegistries.BLOCKS, BringMe115.ID);



    public static void register(IEventBus eventBus){
        BLOCK.register(eventBus);
    }
}
