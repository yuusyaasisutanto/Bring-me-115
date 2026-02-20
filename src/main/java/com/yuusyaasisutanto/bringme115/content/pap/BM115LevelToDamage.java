package com.yuusyaasisutanto.bringme115.content.pap;

public class BM115LevelToDamage {

    // NBTから得たPAPレベルをここで数値に変換する
    public static float getPackAPunchedDamage(int paplvl){
        return switch (paplvl) {
            case 1 -> 2.0f;
            // case10で0を返すことで見分けをつきやすくし、デバッグを容易にする。
            case 10 -> 0.0f;
            default -> 1.0f;
        };
    }
}
