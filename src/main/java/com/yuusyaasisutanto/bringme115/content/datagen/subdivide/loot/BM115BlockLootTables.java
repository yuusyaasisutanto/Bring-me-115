package com.yuusyaasisutanto.bringme115.content.datagen.subdivide.loot;

import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Collectors;

public class BM115BlockLootTables extends BlockLootSubProvider {

    public BM115BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // そのものをドロップさせる
        this.dropSelf(BM115BlockRegister.PRIMITIVE_MACHINE.get());
        this.dropSelf(BM115BlockRegister.RAW_ELEMENT115_BLOCK.get());

        // 鉱石
        this.add(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get(),
                block -> createRawOreDrops(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get(),
                        BM115ItemRegister.RAW_ELEMENT115.get()));

    }

    protected LootTable.Builder createRawOreDrops(Block block, Item item){
        return createSilkTouchDispatchTable(block,
                    this.applyExplosionDecay(block,
                            LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                ));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BM115BlockRegister.REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
