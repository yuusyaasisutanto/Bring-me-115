package com.yuusyaasisutanto.bringme115.content.compat.jei;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.compat.jei.Interface.I115BrewRefiningRecipe;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.common.Internal;
import mezz.jei.common.gui.textures.Textures;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// 基本的に醸造台レシピと同じの為、本家JEIからコピペのち改変
// わからんところはGeminiに聞きながらやった
// MIT Licence | https://github.com/mezz/JustEnoughItems/blob/1.20.1/Library/src/main/java/mezz/jei/library/plugins/vanilla/brewing/BrewingRecipeCategory.java
// これに伴って、あまり行儀はよろしくないが、JEIの内部用クラスを利用している
// 元の醸造台レシピ表示からStep数のみ削除、使わないので
public class BM115BrewRefiningRecipeCategory extends AbstractRecipeCategory<I115BrewRefiningRecipe> {
    private final IDrawable background;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated bubbles;
    private final IDrawableStatic blazeHeat;

    public BM115BrewRefiningRecipeCategory(IGuiHelper guiHelper) {
        super(
                // BM115用に編集
                RecipeType.create(BringMe115.ID, "brewrefine", I115BrewRefiningRecipe.class),
                // 翻訳キーも
                Component.translatable("gui.jei.category.bringme115.brewrefining"),
                guiHelper.createDrawableItemStack(new ItemStack(BM115ItemRegister.ELEMENT115_VIAL.get())),
                114,
                61
        );
        Textures textures = Internal.getTextures();
        background = textures.getBrewingStandBackground();

        arrow = guiHelper.createAnimatedDrawable(textures.getBrewingStandArrow(), 400, IDrawableAnimated.StartDirection.TOP, false);

        ITickTimer bubblesTickTimer = new BrewingBubblesTickTimer(guiHelper);
        bubbles = guiHelper.createAnimatedDrawable(textures.getBrewingStandBubbles(), bubblesTickTimer, IDrawableAnimated.StartDirection.BOTTOM);

        blazeHeat = textures.getBrewingStandBlazeHeat();
    }



    @Override
    public void draw(I115BrewRefiningRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 1);
        blazeHeat.draw(guiGraphics, 5, 30);
        bubbles.draw(guiGraphics, 9, 1);
        arrow.draw(guiGraphics, 43, 3);
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, I115BrewRefiningRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> potionInputs = recipe.getPotionInputs();

        builder.addInputSlot(1, 37)
                .addItemStacks(potionInputs);

        builder.addInputSlot(24, 44)
                .addItemStacks(potionInputs);

        builder.addInputSlot(47, 37)
                .addItemStacks(potionInputs);

        builder.addInputSlot(24, 3)
                .addItemStacks(recipe.getIngredients());

        builder.addOutputSlot(81, 3)
                .addItemStack(recipe.getOutput())
                .setStandardSlotBackground();
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(I115BrewRefiningRecipe recipe) {
        return recipe.getUid();
    }


    private static class BrewingBubblesTickTimer implements ITickTimer {
        /**
         * Similar to {@link BrewingStandScreen#BUBBLELENGTHS}
         */
        @SuppressWarnings("JavadocReference")
        private static final int[] BUBBLE_LENGTHS = new int[]{29, 23, 18, 13, 9, 5, 0};
        private final ITickTimer internalTimer;

        public BrewingBubblesTickTimer(IGuiHelper guiHelper) {
            this.internalTimer = guiHelper.createTickTimer(14, BUBBLE_LENGTHS.length - 1, false);
        }

        @Override
        public int getValue() {
            int timerValue = this.internalTimer.getValue();
            return BUBBLE_LENGTHS[timerValue];
        }

        @Override
        public int getMaxValue() {
            return BUBBLE_LENGTHS[0];
        }
    }
}