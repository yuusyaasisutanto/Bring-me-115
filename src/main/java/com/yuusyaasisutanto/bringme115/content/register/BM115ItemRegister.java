package com.yuusyaasisutanto.bringme115.content.register;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.items.implemented.aetherium_crystal.BM115AetheriumCrystal;
import com.yuusyaasisutanto.bringme115.content.items.implemented.unstable_dim_shard.BM115UnstableDimShard;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BM115ItemRegister {
    public static final DeferredRegister<Item> REGISTRY
            = DeferredRegister.create(ForgeRegistries.ITEMS, BringMe115.ID);

    public static final List<RegistryObject<Item>> MAIN = new ArrayList<>();


    // Items
//    public static final RegistryObject<Item> CODINGPRACTICE
//            = register("codingpractice",()-> new BM115TestItemBomb(new Item.Properties().stacksTo(1)));

//    public static final RegistryObject<Item> TESTIMP
//            = register("testimp",()-> new Item(new Item.Properties()));


    // 内部用ダミーアイテム

    public static final RegistryObject<Item> UNSTABLE_DIM_SHARD
            = register("unstable_dim_shard",()-> new BM115UnstableDimShard(new Item.Properties()));

    public static final RegistryObject<Item> ELEMENT115_VIAL
            = register("element115_vial",()-> new Item(new Item.Properties()){
        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    });

    public static final RegistryObject<Item> RAW_ELEMENT115
            = register("raw_element115",()-> new Item(new Item.Properties()){
        @Override
        public boolean isFoil(ItemStack p_41453_) { return true; }
    });

    public static final RegistryObject<Item> ACTIVATED_ELEMENT115
            = register("activated_element115",()-> new Item(new Item.Properties()){
        @Override
        public boolean isFoil(ItemStack p_41453_) { return true; }
    });



    //PaPCristal
    public static final RegistryObject<Item> AETHERIUM_CRYSTAL
            = register("aetherium_crystal",()-> new BM115AetheriumCrystal(new Item.Properties()));




    // 以下登録用メソッドなど
    static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return BM115ItemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static RegistryObject<Item> register(String name, Supplier<Item> supplier){
        RegistryObject<Item> item = REGISTRY.register(name, supplier);
            MAIN.add(item);
        return item;
    }
}
