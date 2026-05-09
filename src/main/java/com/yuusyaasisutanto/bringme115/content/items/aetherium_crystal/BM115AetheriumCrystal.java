package com.yuusyaasisutanto.bringme115.content.items.aetherium_crystal;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BM115AetheriumCrystal extends Item {

    public BM115AetheriumCrystal(Properties p_41383_) {
        super(p_41383_);

    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level level, List<Component> tooltiplist, TooltipFlag flag) {
        MutableComponent PAP_LEVEL_INDICATOR = Component.translatable("tooltip.bringme115.aetherium_crystal.paplevel_indicator");
        MutableComponent PAP_LEVEL_NONE = Component.translatable("tooltip.bringme115.aetherium_crystal.paplevel_indicator.none_pap");

        //NBTの書き方を忘れてしまっていたので、Compound格納複数値の書き方をここにメモとして残しておく
        //{BM115Modify:{PaPlvl:3, Example:500}}
        //{BM115Modify:[{PaPlvl:3, PaPExp:500}]}とすると配列型となってしまい別扱い

        if(item.hasTag()) {
            CompoundTag nbt = item.getTag();
            if(nbt != null && nbt.contains("BM115Modify")){

                //NBTにBM115ModifyのCompoundが含まれている場合
                CompoundTag BM115Modify = nbt.getCompound("BM115Modify");
                int PaPLevel = BM115Modify.getInt("PaPlvl");
                tooltiplist.add(PAP_LEVEL_INDICATOR
                           .append(Component.literal(" "))
                           .append(Component.literal(String.valueOf(PaPLevel)))
                               );
            }
        } else {
            tooltiplist.add(PAP_LEVEL_INDICATOR.append(Component.literal(" ")).append(PAP_LEVEL_NONE));
        }
    }

    @Override
    public Component getName(ItemStack item) {

        int PaPLevel = getPaPLevel(item);

        //PaPLevelが4を超えた場合、?:演算子を利用して改めてレベルを取得して名前に組み込む
        MutableComponent papNameFinallizer =
                PaPLevel == 4 ?
                Component.translatable(this.getDescriptionId(item) + ".paplevel." + PaPLevel)
                        .append(Component.literal(
                                String.valueOf(item.getTag().getCompound("BM115Modify").getInt("PaPlvl")
                                )))
                : //演算子の一部
                Component.translatable(this.getDescriptionId(item) + ".paplevel." + PaPLevel);

        return papNameFinallizer;
    }

    public static int getPaPLevel(ItemStack item) {
        //先に定義、nullる（動詞）を避けるため
        int PaPLevel = 0;

        if(item.hasTag()) {
            CompoundTag nbt = item.getTag();
            if (nbt != null && nbt.contains("BM115Modify")) {
                //NBTにBM115ModifyのCompoundが含まれている場合、PaPlvlを抽出
                CompoundTag BM115Modify = nbt.getCompound("BM115Modify");
                PaPLevel = Math.min(BM115Modify.getInt("PaPlvl"), 4);
            }
        }
        return PaPLevel;
    }
}
