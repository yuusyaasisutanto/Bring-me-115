package com.yuusyaasisutanto.bringme115.content.items.implemented.aetherium_crystal;

import com.yuusyaasisutanto.bringme115.content.items.interfaces.ICreativeTabVariantsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.yuusyaasisutanto.bringme115.content.pap.BM115PaPLevelToDamage.PAP_DAMAGE_MAP;

public class BM115AetheriumCrystal extends Item implements ICreativeTabVariantsItem {

    public BM115AetheriumCrystal(Properties p_41383_) {
        super(p_41383_);

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
        });
        super.initializeClient(consumer);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
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

    // クリエイティブタブ用に
    @Override
    public List<ItemStack> getTabVariants() {
        List<ItemStack> variants = new ArrayList<>();

        // コンフィグによってPaPレベルが増減するのを想定して増減したものをタブに並ばせようとしたが
        // コンフィグのロードとクリエイティブタブの初期化が必ずしも想定順に並ぶとは限らないため、一先ず固定数を表示することにした
        // ワールドのリログによって再読み込みさせる機構を組めるらしいが、あまり開発者的には胃に優しくなさそうだ
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(this);

            stack.getOrCreateTagElement("BM115Modify").putInt("PaPlvl", i + 1);

            variants.add(stack);
        }

        return variants;
    }
}
