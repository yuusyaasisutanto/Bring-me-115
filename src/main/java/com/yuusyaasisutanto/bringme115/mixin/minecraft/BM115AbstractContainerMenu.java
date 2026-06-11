package com.yuusyaasisutanto.bringme115.mixin.minecraft;

import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.yuusyaasisutanto.bringme115.content.config.BM115ConfigBuilder.EASTER_EGG_GET_LV115;

@Mixin(AbstractContainerMenu.class)
public class BM115AbstractContainerMenu {
    @Inject(method = "clicked", at = @At("HEAD"), cancellable = true, remap = true)
    public void clicked(int slot, int p_150401_, ClickType p_150402_, Player player, CallbackInfo ci) {
        AbstractContainerMenu currentMenu = (AbstractContainerMenu) (Object) this;

        // 開かれてるメニューが砥石のメニューではない場合即返す
        if (!(currentMenu instanceof GrindstoneMenu)){
            return;
        }

        // コンフィグから状態を取得……このmixinが何をしているかそろそろわかる人もいるだろう
        Boolean flag = EASTER_EGG_GET_LV115.get();

        if (slot == 2) {
            GrindstoneMenu menu = (GrindstoneMenu) player.containerMenu;
            ItemStack stack = menu.getSlot(2).getItem();

            if (stack.is(BM115ItemRegister.UNSTABLE_DIM_SHARD.get())) {
                if (!player.level().isClientSide) {
                    ItemStack topItem = menu.getSlot(0).getItem();
                    ItemStack bottomItem = menu.getSlot(1).getItem();

                    if (topItem.is(BM115ItemRegister.AETHERIUM_CRYSTAL.get()) && bottomItem.isEmpty() && flag) {
                        CompoundTag tag = topItem.getTag();
                        int paplvl = tag != null ? tag.getCompound("BM115Modify").getInt("PaPlvl") : 0;

                        if (paplvl == 3) {
                            topItem.shrink(1);
                            // 確率判定、intにキャストすることで小数点以下第4位までを切り捨てる
                            int chance = (int) (Math.random() * 10000);
                            //int chance = 0; //確認用
                            //System.out.println(chance);

                            if (chance < 115) {
                                ItemStack crystal = new ItemStack(BM115ItemRegister.AETHERIUM_CRYSTAL.get());
                                CompoundTag crystalTag = new CompoundTag();
                                crystal.getOrCreateTagElement("BM115Modify").putInt("PaPlvl", 115);
                                menu.getSlot(1).set(crystal);
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 1.0F);
                                //crystalGot = true;
                            } else {
                                // 残念
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 0.3F, 1.0F);
                            }
                        }
                    }
                }
                ci.cancel();
            }
        }

    }
}
