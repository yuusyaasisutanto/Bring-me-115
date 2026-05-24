package com.yuusyaasisutanto.bringme115.content.pap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuusyaasisutanto.bringme115.content.config.BM115ConfigBuilder.DAMAGE_MULTIPLIERS;
import static com.yuusyaasisutanto.bringme115.content.config.BM115ConfigBuilder.SPECIAL_LEVEL_MULTIPLIERS;

public class BM115PaPLevelToDamage {
    // コンフィグから読み込んだものをおっぴろげるところ
    public static final Map<Integer, Float> PAP_DAMAGE_MAP = new HashMap<>();
    // こっちは特定数字用
    public static final Map<Integer, Float> SPECIFIED_PAP_DAMAGE_MAP = new HashMap<>();

    // 毎回ロード時やリロード時に呼び出される予定のメソッド
    public static void loadMultipliersFromConfig(){
        // 両マップを初期化
        PAP_DAMAGE_MAP.clear();
        SPECIFIED_PAP_DAMAGE_MAP.clear();

        // 通常PaPの数値を変数へ展開
        List<? extends Double> normalPaPConfigData = DAMAGE_MULTIPLIERS.get();
        for (int i = 0; i < normalPaPConfigData.size(); i++) {
            PAP_DAMAGE_MAP.put(i +1, normalPaPConfigData.get(i).floatValue());
        }

        List<? extends String> specifiedPaPConfigData = SPECIAL_LEVEL_MULTIPLIERS.get();
        // 拡張for文と呼ばれるものらしい、配列やコレクションを総ざらいする時に対して使えるfor文の書き方らしい
        // ちなみに当たり前のようにGemini君は解説無しで使ってました（ふんぬ）
        // 見る限り:の前が受け取る為の変数で、後者の配列を総ざらいしていくっぽい
        for (String entry : specifiedPaPConfigData) {
            try {
                // 配列から受け取った文字列を:で分割する
                String[] parts = entry.split(":");
                if (parts.length == 2){
                    int customPaPLevel = Integer.parseInt(parts[0].trim());
                    float customMultiplier = Float.parseFloat(parts[1].trim());

                    // MAPにぶち込む
                    SPECIFIED_PAP_DAMAGE_MAP.put(customPaPLevel, customMultiplier);
                }
            } catch (NumberFormatException e){
                System.err.println("[Bring Me 115!] NEIN! You put wrong style in Config - \"ADVANCED SETTING\"! Check the number, Mason!");
            }
        }
    }



    // NBTから得たPAPレベルをここで数値に変換する
    public static float getPackAPunchedDamage(int paplvl){
        //旧実装
//        return switch (paplvl) {
//            case 1 -> 2.0f;
//            // case10で0を返すことで見分けをつきやすくし、デバッグを容易にする。
//            case 10 -> 0.0f;
//            default -> 1.0f;
//        };

        if (SPECIFIED_PAP_DAMAGE_MAP.containsKey(paplvl)){
            return SPECIFIED_PAP_DAMAGE_MAP.get(paplvl);
        }
        return PAP_DAMAGE_MAP.getOrDefault(paplvl, 1.0F);
    }
}
