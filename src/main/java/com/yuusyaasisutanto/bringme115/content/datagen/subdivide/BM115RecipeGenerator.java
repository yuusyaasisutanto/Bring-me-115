package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.List;
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

        // Shapeless
        // カテゴリ,成果物,出力数
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BM115ItemRegister.RAW_ELEMENT115.get(), 9)
                // 要求
                .requires(BM115BlockRegister.RAW_ELEMENT115_BLOCK.get())
                .unlockedBy("has_raw_element115", has(BM115ItemRegister.RAW_ELEMENT115.get()))
                // 同じ出力物でレシピ被りが起こった場合の内部での混乱を防ぐために独自IDを設定
                .save(consumer, BringMe115.ID + ":unpacking_raw_element115_block");


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
