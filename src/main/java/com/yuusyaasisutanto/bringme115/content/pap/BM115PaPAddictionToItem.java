package com.yuusyaasisutanto.bringme115.content.pap;

import com.yuusyaasisutanto.bringme115.content.items.aetherium_crystal.BM115AetheriumCrystal;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.concurrent.Computable;

import static com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPMenu.TE_INVENTORY_FIRST_SLOT_INDEX;

public class BM115PaPAddictionToItem {
    // 武器にPaPを付与する時の処理
    // やらかしたバグを以下に羅列、同じ轍を踏まないように
    // - アイテムスロットの位置の把握ミス
    //      →BM115PrimitivePaPMenuではインベントリのスロットを全て用意してからGUI用のスロットを用意していたので、そこで参照元を間違えた
    // - NBTタグの名前間違い
    //      →PaPlvlをpaplvlと表記、NBTは大小厳格に区別する為、死。
    //       何らかで頻出する単語を予め定数として用意する方がいいのかも？
    // - CompoundTag weaponTag = weaponSlot.getOrCreateTag().getCompound("BM115Modify");
    //      →正しくはCompoundTag weaponTag = weaponSlot.getOrCreateTagElement("BM115Modify");
    //       初心者にとっちゃ違いがわからなさすぎる
    //       Gemini曰く
    //       　.getOrCreateTag(): ここまではOKです。アイテムにルートタグ {} を作ります。
    //        .getCompound("BM115Modify"): ここが問題です。もし BM115Modify が存在しない場合、このメソッドは「どこにも繋がっていない、浮いた状態の空のタグ」を生成して返します。
    //        戻り値: アイテムとは絶縁された「ただの空の箱」が返ってきます。
    //        返ってきた「空の箱」に .putInt で値を書き込んでも、それはアイテムとは繋がっていない独立したデータなので、アイテム本体のNBTには反映されません。
    //        これを反映させるには、手動で rootTag.put("BM115Modify", newTag) のように書き戻す作業が必要になります。
    //       との事。
    public static void tryInfusing115toWeapon(ServerPlayer player) {
        if (player.containerMenu instanceof BM115PrimitivePaPMenu menu) {
            // 処理
//            System.out.println("containerMenuPassed");
            // スロットの定義
            // 銃スロット(TE_INVENTORY_FIRST_SLOT_INDEX + 0)
            ItemStack weaponSlot = menu.getSlot(TE_INVENTORY_FIRST_SLOT_INDEX + 0).getItem();
            // クリスタルスロット(TE_INVENTORY_FIRST_SLOT_INDEX + 1)
            ItemStack crystalSlot = menu.getSlot(TE_INVENTORY_FIRST_SLOT_INDEX + 1).getItem();

//            System.out.println("Checking:"+Boolean.toString(isValidCrystal(crystalSlot) && isValidWeapon(weaponSlot, crystalSlot)));
//            System.out.println("Checking Crystal:"+Boolean.toString(isValidCrystal(crystalSlot)));
//            System.out.println("Checking Weapon:"+Boolean.toString(isValidWeapon(weaponSlot, crystalSlot)));

            //クリスタルと銃（武器）が使用可能な物であるかを判断し、そうであるなら以下の内容を実行する
            if (isValidCrystal(crystalSlot) && isValidWeapon(weaponSlot, crystalSlot)){

                // クリスタルからNBTの読み取り
                CompoundTag crystalTag = crystalSlot.getTagElement("BM115Modify");
                int papLevel = crystalTag != null ? crystalTag.getInt("PaPlvl") : 0;
//                System.out.println("Checking papLevel int:"+ Integer.toString(papLevel));
                // 銃のNBTへPaPレベル書き込み
                CompoundTag weaponTag = weaponSlot.getOrCreateTagElement("BM115Modify");
                weaponTag.putInt("PaPlvl", papLevel);
//                System.out.println("Checking Weapon:"+ weaponTag);
                // クリスタルの消費
                crystalSlot.shrink(1);

                // 効果音再生
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    // 以下の二つはチート防止などの観点によるアイテムチェック。
    // クリスタルがまともなものであるかの確認
    private static boolean isValidCrystal(ItemStack stack){
        // スロットに入っているのがエーテリウムクリスタルかどうか
//        System.out.println("isValidCrystal: slot check " + stack.getItem());
        if (!stack.is(BM115ItemRegister.AETHERIUM_CRYSTAL.get())) return false;
//        System.out.println("isValidCrystal: slot check passed");
        // 個数が0以下などの不正な数値になっていないか
        if (stack.getCount() <= 0) return false;
//        System.out.println("isValidCrystal: Count check passed");

        // 0以外のNBTが含まれているかどうか
        CompoundTag nbt = stack.getTagElement("BM115Modify");
//        System.out.println("isValidCrystal: " + Boolean.toString(nbt != null && nbt.getInt("PaPlvl") > 0));
        return nbt != null && nbt.getInt("PaPlvl") > 0;
    }

    //　銃がまともなものであるか、レベルが丸被りしていないか
    private static boolean isValidWeapon(ItemStack weapon, ItemStack crystal) {
        // 空だったら中指立てて突き返す
        if (weapon.isEmpty()) return false;

        // TaCZの銃かどうか、一先ずTaCZ銃限定
        // TaCZ銃の場合IGunインターフェースを通してるからそこでチェックするのが確実だって
        // Geminiが言ってたので、それを自分の責任の下に採用
        // 代替案としては、そもそも同じアイテムIDを別NBTで管理しているのがTaCZの銃なので、そこで判断しても良くね？とは思う
        if (!(weapon.getItem() instanceof com.tacz.guns.api.item.IGun)) return false;

        // PaPクリスタルのPaPlvlと同値じゃなければtrueを返す
        CompoundTag weaponTag = weapon.getTagElement("BM115Modify");
        int weaponPaPLevel = 0;
        if (weaponTag != null) {
            weaponPaPLevel = weaponTag.getInt("PaPlvl");
        }

        CompoundTag crystalTag = crystal.getTagElement("BM115Modify");
        int crystalPaPLevel = 0;
        if (crystalTag != null) {
            crystalPaPLevel = crystalTag.getInt("PaPlvl");
        }

        // 値の照合
//        System.out.println("isValidWeapon: " + Boolean.toString(crystalPaPLevel != weaponPaPLevel));

        return crystalPaPLevel != weaponPaPLevel ;
    }
}
