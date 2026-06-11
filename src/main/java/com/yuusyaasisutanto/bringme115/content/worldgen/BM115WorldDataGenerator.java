package com.yuusyaasisutanto.bringme115.content.worldgen;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class BM115WorldDataGenerator {
    // 識別用のキー

    // Configured
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELEMENT115_ORE_KEY = registerKey("element115_ore");

    // Placed
    public static final ResourceKey<PlacedFeature> ELEMENT115_ORE_PLACED_KEY = registerPlacedKey("element115_ore");
    public static final ResourceKey<PlacedFeature> ELEMENT115_ORE_BONUS_PLACED_KEY = registerPlacedKey("element115_ore_bonus");

    // Configured_Featureの登録、主に何が、何個生成されるか
    public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> context){

        // 深層岩を置き換える、今回は石の出現範囲には登場しないのでここだけ
        OreConfiguration.TargetBlockState targetDeepSlateStone = OreConfiguration.target(
                new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get().defaultBlockState()
        );

        // 1鉱脈辺りの最大サイズ
        int veinSize = 3;
        List<OreConfiguration.TargetBlockState> targetBlockStateList = List.of(targetDeepSlateStone);

        context.register(ELEMENT115_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetBlockStateList, veinSize)));
    }

    // Placed_Featureの登録、どこに、どのくらいの高さで配置されるか
    public static void bootstrapPlaced(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(ELEMENT115_ORE_PLACED_KEY, new PlacedFeature(
                configuredFeature.getOrThrow(ELEMENT115_ORE_KEY),
                List.of(
                        CountPlacement.of(10), // chunk per try
                        InSquarePlacement.spread(), // 水平分布
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63),
                                VerticalAnchor.absolute(-9)), //一様に拡散
                        BiomeFilter.biome()
                )
        ));

        context.register(ELEMENT115_ORE_BONUS_PLACED_KEY, new PlacedFeature(
                configuredFeature.getOrThrow(ELEMENT115_ORE_KEY),
                List.of(
                        CountPlacement.of(3), // chunk per try
                        InSquarePlacement.spread(), // 水平分布
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63),
                                VerticalAnchor.absolute(-50)), //一様に拡散
                        BiomeFilter.biome()
                )
        ));
    }

    // ↑一先ずGeminiのを写経する形にしたが、Kaupenjoe氏のチュートリアルではConfiguredとPlacedを別classに分けていたりもした
    // なおBiomeModifierに関してはなんかJsonを組み立てようとしてたのでGeminiのを無視してKaupenjoe氏のを参考にした



    // ヘルパーメソッド共
    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(BringMe115.ID, name));
    }

    private static ResourceKey<PlacedFeature> registerPlacedKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BringMe115.ID, name));
    }
}
