package com.yuusyaasisutanto.bringme115.content.items.interfaces;

import net.minecraft.world.item.ItemStack;

import java.util.List;


// クリエイティブタブでバリアントを表示しなければならないアイテムのインターフェース
public interface ICreativeTabVariantsItem {
    List<ItemStack> getTabVariants();
}
