package com.yuusyaasisutanto.bringme115.content.compat.jei.Interface;


import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;



// MIT Licence | https://github.com/mezz/JustEnoughItems/blob/1.20.1/CommonApi/src/main/java/mezz/jei/api/recipe/vanilla/IJeiBrewingRecipe.java
@ApiStatus.NonExtendable
public interface I115BrewRefiningRecipe {

    @Unmodifiable
    List<ItemStack> getPotionInputs();


    @Unmodifiable
    List<ItemStack> getIngredients();


    ItemStack getOutput();

    @Nullable
    default ResourceLocation getUid() {
        return null;
    }
}
