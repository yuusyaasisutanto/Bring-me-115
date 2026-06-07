package com.yuusyaasisutanto.bringme115.content.items.implemented.unstable_dim_shard;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.List;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
public class BM115UnstableDimShardTooltips {

    @SubscribeEvent
    public static void onViewingTooltip(ItemTooltipEvent event){
        ItemStack itemStack = event.getItemStack();

        if (itemStack.is(BM115ItemRegister.UNSTABLE_DIM_SHARD.get())){
            CompoundTag nbt = itemStack.getTag();
            List<Component> tooltip = event.getToolTip();

            if (nbt != null && nbt.contains("reason")){
                String type = nbt.getString("reason");


                // 後の拡張性を考えてswitch文
                switch (type){
                    case "Lv115CrystalEE":
                        // R.I.P serious, you save the BO3 modding community...
                        tooltip.add(1, Component.translatable("uds.tooltip.case1.line1"));
                        tooltip.add(2, Component.translatable("uds.tooltip.case1.line2"));

                    default:
                        tooltip.add(1, Component.translatable("uds.tooltip.unknown.line1"));
                }

            } else {
                tooltip.add(1, Component.translatable("uds.tooltip.unknown.line2"));
            }

        }
    }

}
