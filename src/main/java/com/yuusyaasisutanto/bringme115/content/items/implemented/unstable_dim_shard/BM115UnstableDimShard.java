package com.yuusyaasisutanto.bringme115.content.items.implemented.unstable_dim_shard;

import com.yuusyaasisutanto.bringme115.content.items.interfaces.ICreativeTabVariantsItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BM115UnstableDimShard extends Item implements ICreativeTabVariantsItem {

    public BM115UnstableDimShard(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<ItemStack> getTabVariants() {
        return List.of();
    }

    @Override
    public boolean isIdleEyes() {
        return true;
    }
}
