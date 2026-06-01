package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.content.datagen.subdivide.loot.BM115BlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class BM115LootTableGenerator {
    public static LootTableProvider create(PackOutput output){
            return new LootTableProvider(
                    output,
                    Set.of(),
                    List.of(new LootTableProvider.SubProviderEntry(
                            BM115BlockLootTables::new,
                            LootContextParamSets.BLOCK
                    ))
            );
    };
}
