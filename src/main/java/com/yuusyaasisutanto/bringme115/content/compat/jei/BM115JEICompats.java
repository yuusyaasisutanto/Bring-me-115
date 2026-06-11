package com.yuusyaasisutanto.bringme115.content.compat.jei;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

// 醸造レシピのJEI対応
// @JeiPluginをつけ忘れてたので一瞬死んでた
// 参考にしようとしていたMODの実装がいささか迂遠だったのでGeminiに実装を聞いたら
// そちらの方で正常に動作したのでそうした

@JeiPlugin
public class BM115JEICompats implements IModPlugin {

    // このプラグイン用のUID発行
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BringMe115.ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ItemStack potionAwkward = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);

        Ingredient ingredientItem = Ingredient.of(BM115ItemRegister.RAW_ELEMENT115.get());

        ItemStack outputItem = new ItemStack(BM115ItemRegister.ELEMENT115_VIAL.get());


        registration.addRecipes(RecipeTypes.BREWING, List.of(
                new IJeiBrewingRecipe() {
                    @Override
                    public @Unmodifiable List<ItemStack> getPotionInputs() {
                        return List.of(potionAwkward);
                    }

                    @Override
                    public @Unmodifiable List<ItemStack> getIngredients() {
                        return List.of(new ItemStack(BM115ItemRegister.RAW_ELEMENT115.get()));
                    }

                    @Override
                    public ItemStack getPotionOutput() {
                        return outputItem;
                    }

                    @Override
                    public int getBrewingSteps() {
                        return 1;
                    }
                }
        ));
    }
}
