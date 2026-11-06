package com.yuusyaasisutanto.bringme115.content.compat.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;


// JEIに伝える用のカスタムレシピ専門の型
public class BM115BrewRefiningRecipe {
    private final Ingredient potionInput;
    private final Ingredient ingredient;
    private final ItemStack output;


    public BM115BrewRefiningRecipe(Ingredient potionInput, Ingredient ingredient, ItemStack output) {
        this.potionInput = potionInput;
        this.ingredient = ingredient;
        this.output = output;
    }

    // JEI用のgetter


}
