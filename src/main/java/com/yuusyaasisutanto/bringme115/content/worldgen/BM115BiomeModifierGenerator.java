package com.yuusyaasisutanto.bringme115.content.worldgen;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class BM115BiomeModifierGenerator {
    public static final ResourceKey<BiomeModifier> ADD_ELEMENT115_ORE = registerBiomeKey("add_element115_ore");
    public static final ResourceKey<BiomeModifier> ADD_ELEMENT115_ORE_BONUS = registerBiomeKey("add_element115_ore_bonus");

    public static void bootstrapBiomeModifier(BootstapContext<BiomeModifier> context){
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ELEMENT115_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), // Overworld限定
                HolderSet.direct(placedFeatures.getOrThrow(BM115WorldDataGenerator.ELEMENT115_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES // 生成ステップ
        ));

        context.register(ADD_ELEMENT115_ORE_BONUS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), // Overworld限定
                HolderSet.direct(placedFeatures.getOrThrow(BM115WorldDataGenerator.ELEMENT115_ORE_BONUS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES // 生成ステップ
        ));
    }


    private static ResourceKey<BiomeModifier> registerBiomeKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(BringMe115.ID, name));
    }

}
