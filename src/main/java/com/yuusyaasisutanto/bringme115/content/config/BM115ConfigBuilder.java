package com.yuusyaasisutanto.bringme115.content.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class BM115ConfigBuilder {
    // ForgeConfigSpecを用いてコンフィグの生成をするらしい。

    public static final ForgeConfigSpec.Builder BUILDER =new ForgeConfigSpec.Builder();
    // 多分busに乗せる用の詳細を詰めこむ変数
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> DAMAGE_MULTIPLIERS;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> SPECIAL_LEVEL_MULTIPLIERS;

    static{
        BUILDER.push("Bring me 115! Damage Settings");

        DAMAGE_MULTIPLIERS = BUILDER
                .comment("--- NORMAL SETTING ---")
                .comment("These numbers will be the PaP Level multipliers.")
                .comment("The number of elements will be MAX PaP level.")
                .comment("If you put the out of range PaP level (ex. direct editing NBT), its multiplier will be x1.0")
                .defineListAllowEmpty(
                        List.of("damage_multipliers"),
                        () -> List.of(2.0, 4.0, 8.0, 16.0),
                        element -> element instanceof Double
                );

        SPECIAL_LEVEL_MULTIPLIERS = BUILDER
                .comment("--- ADVANCED SETTING ---")
                .comment("If you need to set specific multiplier to specific PaP level, write to this.")
                .comment("\"935:115.0\" means PaP level 935 will be x115.0 damage multiplier.")
                .comment("This setting will override normal setting.")
                .comment("ex. If you set \"1:115.0\" in this, PaP level 1 will force be x115.0 damage multiplier what you set in normal setting.")
                .comment("This section mostly for modpack author.")
                .defineListAllowEmpty(
                        List.of("special_level_multipliers"),
                        () -> List.of("115:115.0","935:935.0"),
                        element -> element instanceof String
                );

        BUILDER.pop();
        SPEC = BUILDER.build();

    }
    
}
