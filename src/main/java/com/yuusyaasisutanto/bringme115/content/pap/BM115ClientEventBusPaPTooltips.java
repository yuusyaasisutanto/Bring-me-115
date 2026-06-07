package com.yuusyaasisutanto.bringme115.content.pap;

import com.tacz.guns.init.ModItems;
import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

import static com.yuusyaasisutanto.bringme115.content.pap.BM115PaPLevelToDamage.getPackAPunchedDamage;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
public class BM115ClientEventBusPaPTooltips {

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event){
        ItemStack itemStack = event.getItemStack();
        List<Component> tooltiplist = event.getToolTip();
        List<Item> itemList = new java.util.ArrayList<>(List.of());
        // 表示するアイテムをここで指定
        itemList.addAll(
                Arrays.asList(
                        BM115ItemRegister.AETHERIUM_CRYSTAL.get(),
                        // ↓TaCZ
                        ModItems.MODERN_KINETIC_GUN.get()
                ));


        MutableComponent PAP_LEVEL_INDICATOR = Component.translatable("tooltip.bringme115.paplevel_indicator");
        MutableComponent PAP_LEVEL_DETAIL = Component.translatable("tooltip.bringme115.paplevel_detail");
        MutableComponent PAP_LEVEL_NONE = Component.translatable("tooltip.bringme115.paplevel_indicator.none_pap");
        MutableComponent PAP_MULTIPLIER_INDICATOR = Component.translatable("tooltip.bringme115.paplevel_indicator.multiplier");


        //NBTの書き方を忘れてしまっていたので、Compound格納複数値の書き方をここにメモとして残しておく
        //{BM115Modify:{PaPlvl:3, Example:500}}
        //{BM115Modify:[{PaPlvl:3, PaPExp:500}]}とすると配列型となってしまい別扱い

        if(itemList.contains(itemStack.getItem())){
            if (itemStack.hasTag()) {
                CompoundTag nbt = itemStack.getTag();
                if (nbt != null && nbt.contains("BM115Modify")) {

                    //NBTにBM115ModifyのCompoundが含まれている場合
                    CompoundTag BM115Modify = nbt.getCompound("BM115Modify");
                    int PaPLevel = BM115Modify.getInt("PaPlvl");
                    tooltiplist.add(PAP_LEVEL_DETAIL);
                    tooltiplist.add(PAP_LEVEL_INDICATOR
                            .append(Component.literal(" §b" + String.valueOf(PaPLevel) + "§r")));
                    tooltiplist.add(PAP_MULTIPLIER_INDICATOR
                            .append(Component.literal(" §4x" + String.valueOf(getPackAPunchedDamage(PaPLevel)) + "§r")));


                }
            } else {
                tooltiplist.add(PAP_LEVEL_INDICATOR.append(Component.literal(" ")).append(PAP_LEVEL_NONE));
            }
        }
    }

}
