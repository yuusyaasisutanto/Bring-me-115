package com.yuusyaasisutanto.bringme115.content.worldgen;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;


// WorldGenを纏めるプロバイダークラス……らしいがイマイチ感覚をつかみきれてない
public class BM115WorldGenHeadQuarter extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, BM115WorldDataGenerator::bootstrapConfigured)
            .add(Registries.PLACED_FEATURE, BM115WorldDataGenerator::bootstrapPlaced)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BM115BiomeModifierGenerator::bootstrapBiomeModifier);


    public BM115WorldGenHeadQuarter(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BringMe115.ID));
    }
}
