package com.yuusyaasisutanto.bringme115.content.items.testbomb;

// このアイテムは自分がJavaとModdingを学ぶために追加した物です。本実装では取り除かれる可能性があります。
// This item's purpose is practicing Java and Modding only. It'll may remove on releasing.

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BM115TestItemBomb extends Item {
    public BM115TestItemBomb(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.isSecondaryUseActive()) {
            context.getItemInHand().getCapability(BM115TestItemBombCapability.TOKEN).ifPresent(capability ->{capability.set(context.getLevel().dimension(), context.getClickedPos());});
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isShiftKeyDown()){
            stack.getCapability(BM115TestItemBombCapability.TOKEN).ifPresent(capability -> {
                if (capability.getDimension() != null && capability.getPos() != null && level.dimension().equals(capability.getDimension())){
                    Vec3 center = Vec3.atCenterOf(capability.getPos());
                    level.explode(player, center.x(), center.y(), center.z(), 2, Level.ExplosionInteraction.BLOCK);
                    capability.clear();
                }
            });

            return InteractionResultHolder.success(stack);
        }
        return  InteractionResultHolder.pass(stack);
    }
}
