package com.yuusyaasisutanto.bringme115.content.compat.jei;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.compat.jei.Interface.I115BrewRefiningRecipe;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

// 醸造レシピのJEI対応
// @JeiPluginをつけ忘れてたので一瞬死んでた
// 参考にしようとしていたMODの実装がいささか迂遠だったのでGeminiに実装を聞いたら
// そちらの方で正常に動作したのでそうした

// 従来の醸造レシピだとEMI経由で表示されない為、JEIの実装を丸パクリした
// わからん事や忘れたことだらけなのでGeminiに聞いてばかりではある、つらいなぁ

@JeiPlugin
public class BM115JEICompats implements IModPlugin {

    // レシピタイプの定数指定
    public static final RecipeType<I115BrewRefiningRecipe> BREW_REFINING_RECIPE_TYPE = RecipeType.create(BringMe115.ID, "brewrefine", I115BrewRefiningRecipe.class);

    // このプラグイン用のUID発行
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BringMe115.ID, "jei_plugin");
    }

    // カテゴリの追加、わからなかったのでGeminiに聞いた
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BM115BrewRefiningRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ItemStack potionAwkward = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);

        // 醸造台経由の簡易精製レシピ（エレメント115）
        // 書き方を覚えておく
        registration.addRecipes(BREW_REFINING_RECIPE_TYPE, List.of(
                new I115BrewRefiningRecipe() {
                    // 元にするポーション
                    @Override
                    public @Unmodifiable List<ItemStack> getPotionInputs() {
                        return List.of(potionAwkward);
                    }

                    // 溶かす材料
                    @Override
                    public @Unmodifiable List<ItemStack> getIngredients() {
                        return List.of(new ItemStack(BM115ItemRegister.RAW_ELEMENT115.get()));
                    }

                    // 帰ってくるアイテム
                    @Override
                    public ItemStack getOutput() {
                        return new ItemStack(BM115ItemRegister.ELEMENT115_VIAL.get());
                    }

                    // レシピID、混線しないようにそれぞれにつけておく
                    @Override
                    public ResourceLocation getUid() {
                        return new ResourceLocation(BringMe115.ID,"brewrefine/115_vial");
                    }
                }
        ));
    }
}
