package com.yuusyaasisutanto.bringme115.content.subscriber;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.yuusyaasisutanto.bringme115.content.pap.BM115PaPLevelToDamage.getPackAPunchedDamage;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
public class BM115ClientEventBusFORGESubscriber {

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event){
        ItemStack item = event.getItemStack();
        List<Component> tooltiplist = event.getToolTip();


        MutableComponent PAP_LEVEL_INDICATOR = Component.translatable("tooltip.bringme115.paplevel_indicator");
        MutableComponent PAP_LEVEL_NONE = Component.translatable("tooltip.bringme115.paplevel_indicator.none_pap");
        MutableComponent PAP_MULTIPLIER_INDICATOR = Component.translatable("tooltip.bringme115.paplevel_indicator.multiplier");


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
                        .append(Component.literal(String.valueOf(PaPLevel))));
                tooltiplist.add(PAP_MULTIPLIER_INDICATOR
                        .append(Component.literal(String.valueOf(getPackAPunchedDamage(PaPLevel)))));


            }
        } else {
            tooltiplist.add(PAP_LEVEL_INDICATOR.append(Component.literal(" ")).append(PAP_LEVEL_NONE));
        }
    }

}
