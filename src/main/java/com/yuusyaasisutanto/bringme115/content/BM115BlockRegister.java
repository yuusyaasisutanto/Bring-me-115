package com.yuusyaasisutanto.bringme115.content;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.blocks.blockentity.primitive_machine.BM115PrimitiveMachine;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.yuusyaasisutanto.bringme115.content.BM115ItemRegister.registerBlockItem;

public class BM115BlockRegister {
    public static final DeferredRegister<Block> REGISTRY
            = DeferredRegister.create(ForgeRegistries.BLOCKS, BringMe115.ID);

    public static final RegistryObject<Block> PRIMITIVE_MACHINE
            = registerBlock("primitive_machine", () -> new BM115PrimitiveMachine(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    public static void register(IEventBus eventBus){
        REGISTRY.register(eventBus);
    }
}
