package com.yuusyaasisutanto.bringme115.content.testbomb;

// このアイテムは自分がJavaとModdingを学ぶために追加した物です。本実装では取り除かれる可能性があります。
// This item's purpose is practicing Java and Modding only. It'll may remove on releasing.

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BM115TestItemBomb extends Item {
    public BM115TestItemBomb(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
            Vec3 center = Vec3.atCenterOf(context.getClickedPos());
            context.getLevel().explode(context.getPlayer(), center.x(), center.y(), center.z(), 2, Level.ExplosionInteraction.BLOCK);
        return InteractionResult.SUCCESS;
    }
}
