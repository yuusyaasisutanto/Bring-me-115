package com.yuusyaasisutanto.bringme115.content.items.implemented.unstable_dim_shard;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.yuusyaasisutanto.bringme115.content.config.BM115ConfigBuilder.EASTER_EGG_GET_LV115;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BM115UnstableDimShardRecipes {



    // 砥石を経由するレシピ
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void putPaPCrystalOnGrindStone(GrindstoneEvent.OnPlaceItem event){
        // コンフィグから該当レシピの状態を確認
        Boolean flag = EASTER_EGG_GET_LV115.get();

        ItemStack topItem = event.getTopItem();
        ItemStack bottomItem = event.getBottomItem();

        if (topItem.is(BM115ItemRegister.AETHERIUM_CRYSTAL.get()) && bottomItem.isEmpty() && flag){
            CompoundTag tag = topItem.getTag();
            int paplvl = tag != null ? tag.getCompound("BM115Modify").getInt("PaPlvl") : 0;

            if (paplvl == 3){
                ItemStack dummy = new ItemStack(BM115ItemRegister.UNSTABLE_DIM_SHARD.get());
                dummy.getOrCreateTag().putString("reason","Lv115CrystalEE");

                event.setOutput(dummy);
                //
                event.setXp(0);
                // このレシピを勝手にキャンセルさせないようにする
                event.setCanceled(false);
            }
        }
    }



}
