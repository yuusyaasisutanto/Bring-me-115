package com.yuusyaasisutanto.bringme115.mixin.minecraft;

import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.yuusyaasisutanto.bringme115.content.config.BM115ConfigBuilder.EASTER_EGG_GET_LV115;

// 一部MITライセンスの元に参考・借用↓
// https://github.com/zhaijineet/Grindstone-Plus
// MIT Licence | https://github.com/zhaijineet/Grindstone-Plus/blob/1.20.1/LICENSE

@Mixin(GrindstoneMenu.class)
public abstract class BM115GrindstoneMenu extends AbstractContainerMenu {
    @Shadow
    public abstract ItemStack quickMoveStack(Player p_39588_, int p_39589_);

    protected BM115GrindstoneMenu(@Nullable MenuType<?> p_38851_, int p_38852_) {
        super(p_38851_, p_38852_);
    }

    @Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$2")
    public static class BM115GrindstoneMenu1 {
        @Inject(method = "mayPlace(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true, remap = true)
        private void onMayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            if (!stack.isEmpty() && stack.is(BM115ItemRegister.AETHERIUM_CRYSTAL.get())) {
                cir.setReturnValue(true);
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$3")
    public static class BM115GrindstoneMenu2 {

        @Inject(method = "mayPlace(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true, remap = true)
        private void onMayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            if (!stack.isEmpty() && stack.is(BM115ItemRegister.AETHERIUM_CRYSTAL.get())) {
                cir.setReturnValue(true);
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$4")
    public static class BM115GrindstoneMenu3 extends Slot {
        public BM115GrindstoneMenu3(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
            super(p_40223_, p_40224_, p_40225_, p_40226_);
        }

        @Override
        public boolean mayPickup(Player player) {
            GrindstoneMenu menu = (GrindstoneMenu) player.containerMenu;
            if (menu.getSlot(2).getItem().is(BM115ItemRegister.UNSTABLE_DIM_SHARD.get())) {
                return false;
            }
            return super.mayPickup(player);
        }
    }


}

