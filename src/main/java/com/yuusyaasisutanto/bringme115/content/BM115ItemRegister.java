package com.yuusyaasisutanto.bringme115.content;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.testbomb.BM115TestItemBomb;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> CODINGPRACTICE
            = register("codingpractice",()->new BM115TestItemBomb(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TESTIMP
            = register("testimp",()->new Item(new Item.Properties()));

    public static RegistryObject<Item> register(String name, Supplier<Item> supplier){
        RegistryObject<Item> item = REGISTRY.register(name, supplier);
            MAIN.add(item);
        return item;
    }
}
