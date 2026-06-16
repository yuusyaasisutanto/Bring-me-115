package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.google.gson.JsonObject;
import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BM115RecipeGenerator extends RecipeProvider {
    public BM115RecipeGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Shaped
        // 感覚としては1.7.10から変わってない

        // raw element115の圧縮レシピ
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BM115BlockRegister.RAW_ELEMENT115_BLOCK.get())
                // パターン列
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                // 記号の定義、冒頭はChar型である（ダブルクォーテーションはString型）
                .define('A', BM115ItemRegister.RAW_ELEMENT115.get())
                // レシピの解放条件、今回はraw_element115の入手時
                .unlockedBy("has_raw_element115", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                // すべてをsaveし書き出す
                .save(consumer);

        // primitive_machine
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BM115BlockRegister.PRIMITIVE_MACHINE.get())
                // パターン列
                .pattern(" A ")
                .pattern("BCB")
                .pattern("D D")
                // 記号の定義、冒頭はChar型である（ダブルクォーテーションはString型）
                .define('A', Items.BOOK)
                .define('B', BM115ItemRegister.RAW_ELEMENT115.get())
                .define('C', Items.OBSIDIAN)
                .define('D', Ingredient.of(ItemTags.WOODEN_FENCES))
                // レシピの解放条件、今回はraw_element115の入手時
                .unlockedBy("has_element115_vial", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                // すべてをsaveし書き出す
                .save(consumer);

        // raw element115の活性化レシピ
        // activated_element115
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BM115ItemRegister.ACTIVATED_ELEMENT115.get())
                // パターン列
                .pattern("ACA")
                .pattern("BDB")
                .pattern("ABA")
                // 記号の定義、冒頭はChar型である（ダブルクォーテーションはString型）
                .define('A', Items.CHORUS_FRUIT)
                .define('B', BM115ItemRegister.ELEMENT115_VIAL.get())
                .define('C', Items.JUKEBOX)
                .define('D', BM115ItemRegister.RAW_ELEMENT115.get())
                // レシピの解放条件、今回はraw_element115の入手時
                .unlockedBy("has_element115_vial", has(BM115ItemRegister.ELEMENT115_VIAL.get()))
                // すべてをsaveし書き出す
                .save(consumer);


        // -----------------------------------------------------------

        // Shapeless
        // カテゴリ,成果物,出力数
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BM115ItemRegister.RAW_ELEMENT115.get(), 9)
                // 要求
                .requires(BM115BlockRegister.RAW_ELEMENT115_BLOCK.get())
                .unlockedBy("has_raw_element115", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                // 同じ出力物でレシピ被りが起こった場合の内部での混乱を防ぐために独自IDを設定
                .save(consumer, BringMe115.ID + ":unpacking_raw_element115_block");

        // -----------------------------------------------------------

        // PaP Crystal

        // Level 1
        ItemStack outputPaP1Crystal = new ItemStack(BM115ItemRegister.AETHERIUM_CRYSTAL.get());
        outputPaP1Crystal.getOrCreateTagElement("BM115Modify").putInt("PaPlvl", 1);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, outputPaP1Crystal.getItem(), 1)
                // 要求
                .requires(BM115BlockRegister.RAW_ELEMENT115_BLOCK.get())
                .requires(Items.CLAY_BALL)
                .requires(Items.SAND)
                .requires(Items.PAPER)
                .unlockedBy("has_raw_element115", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                .save(new Consumer<>() {
                    @Override
                    public void accept(FinishedRecipe finishedRecipe) {
                        consumer.accept(new FinishedRecipe() {
                            @Override
                            public void serializeRecipeData(JsonObject json) {
                                finishedRecipe.serializeRecipeData(json);
                                json.getAsJsonObject("result").addProperty("nbt",outputPaP1Crystal.getTag().toString());
                            }

                            @Override
                            public ResourceLocation getId() {
                                // IDだけ変更、そのままだとアイテム名で登録されてしまうためNBTで管理できないため
                                return new ResourceLocation(BringMe115.ID, "aetherium_crystal_1");
                            }

                            @Override
                            public RecipeSerializer<?> getType() {
                                return finishedRecipe.getType();
                            }

                            @Override
                            public @Nullable JsonObject serializeAdvancement() {
                                return finishedRecipe.serializeAdvancement();
                            }

                            @Override
                            public @Nullable ResourceLocation getAdvancementId() {
                                return finishedRecipe.getAdvancementId();
                            }
                        });
                    }
                });

        // Level 2
        ItemStack outputPaP2Crystal = new ItemStack(BM115ItemRegister.AETHERIUM_CRYSTAL.get());
        outputPaP2Crystal.getOrCreateTagElement("BM115Modify").putInt("PaPlvl", 2);

        Ingredient ingredientPaP1Crystal = StrictNBTIngredient.of(outputPaP1Crystal);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outputPaP2Crystal.getItem())
                // パターン列
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                // 記号の定義、冒頭はChar型である（ダブルクォーテーションはString型）
                .define('A', BM115ItemRegister.ELEMENT115_VIAL.get())
                .define('B', Items.AMETHYST_CLUSTER)
                .define('C', ingredientPaP1Crystal)
                // レシピの解放条件、今回はraw_element115の入手時
                .unlockedBy("has_raw_element115", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                .save(new Consumer<>() {
                    @Override
                    public void accept(FinishedRecipe finishedRecipe) {
                        consumer.accept(new FinishedRecipe() {
                            @Override
                            public void serializeRecipeData(JsonObject json) {
                                finishedRecipe.serializeRecipeData(json);
                                json.getAsJsonObject("result").addProperty("nbt", outputPaP2Crystal.getTag().toString());
                            }

                            @Override
                            public ResourceLocation getId() {
                                // IDだけ変更、そのままだとアイテム名で登録されてしまうためNBTで管理できないため
                                return new ResourceLocation(BringMe115.ID, "aetherium_crystal_2");
                            }

                            @Override
                            public RecipeSerializer<?> getType() {
                                return finishedRecipe.getType();
                            }

                            @Override
                            public @Nullable JsonObject serializeAdvancement() {
                                return finishedRecipe.serializeAdvancement();
                            }

                            @Override
                            public @Nullable ResourceLocation getAdvancementId() {
                                return finishedRecipe.getAdvancementId();
                            }
                        });
                    }
                });

        // Level 3
        ItemStack outputPaP3Crystal = new ItemStack(BM115ItemRegister.AETHERIUM_CRYSTAL.get());
        outputPaP3Crystal.getOrCreateTagElement("BM115Modify").putInt("PaPlvl", 3);

        Ingredient ingredientPaP2Crystal = StrictNBTIngredient.of(outputPaP2Crystal);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outputPaP3Crystal.getItem())
                // パターン列
                .pattern("EBE")
                .pattern("ACA")
                .pattern("DBD")
                // 記号の定義、冒頭はChar型である（ダブルクォーテーションはString型）
                .define('A', BM115ItemRegister.ACTIVATED_ELEMENT115.get())
                .define('B', Items.AMETHYST_CLUSTER)
                .define('C', ingredientPaP2Crystal)
                .define('D', Items.SCULK_SENSOR)
                .define('E', Items.NETHER_STAR)
                // レシピの解放条件、今回はraw_element115の入手時
                .unlockedBy("has_raw_element115", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                .save(new Consumer<>() {
                    @Override
                    public void accept(FinishedRecipe finishedRecipe) {
                        consumer.accept(new FinishedRecipe() {
                            @Override
                            public void serializeRecipeData(JsonObject json) {
                                finishedRecipe.serializeRecipeData(json);
                                json.getAsJsonObject("result").addProperty("nbt", outputPaP3Crystal.getTag().toString());
                            }

                            @Override
                            public ResourceLocation getId() {
                                // IDだけ変更、そのままだとアイテム名で登録されてしまうためNBTで管理できないため
                                return new ResourceLocation(BringMe115.ID, "aetherium_crystal_3");
                            }

                            @Override
                            public RecipeSerializer<?> getType() {
                                return finishedRecipe.getType();
                            }

                            @Override
                            public @Nullable JsonObject serializeAdvancement() {
                                return finishedRecipe.serializeAdvancement();
                            }

                            @Override
                            public @Nullable ResourceLocation getAdvancementId() {
                                return finishedRecipe.getAdvancementId();
                            }
                        });
                    }
                });


        // 今回は使わないかもしれないが、覚えておきたいレシピの構築
        // Geminiよりコピペ、そのまま使う予定はない

        // ----------------------------------------------------------------------
        // パターン3: 精錬レシピ（かまど ＆ 溶鉱炉）
        // 原石を焼いて精製された素材等を作るレシピ
        // ----------------------------------------------------------------------
        // 焼く対象（原石）をリストにする
//        List<ItemLike> burnables = List.of(BM115ItemRegister.RAW_ELEMENT115.get());

        // 出力されるアイテム（ここでは仮にバニラのダイヤモンドにしていますが、精製結晶等に変更してください）
//        ItemLike resultItem = Items.DIAMOND;

        // A. 普通のかまど（Smelting）での精錬：調理時間 200個分（10秒）
//        oreSmelting(consumer, burnables, RecipeCategory.MISC, resultItem,
//                0.7F, 200, "element115_smelting");

        // B. 溶鉱炉（Blasting）での高速精錬：調理時間 100個分（5秒）
//        oreBlasting(consumer, burnables, RecipeCategory.MISC, resultItem,
//                0.7F, 100, "element115_blasting");
    }
}
