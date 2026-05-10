package com.yuusyaasisutanto.bringme115.content.pap.itemicon;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.event.TickEvent;

public class BM115ItemIconPaPLevelindicator implements IItemDecorator {

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        //test
        //System.out.println("Time: " + (System.currentTimeMillis() / 20));
        // nullチェックの簡易化のために先んじて定義
        CompoundTag nbt = stack.getTag();

        if (nbt != null && nbt.contains("BM115Modify")){

            int papLevel = nbt.getCompound("BM115Modify").getInt("PaPlvl");

            if (papLevel > 0){
                String papLevelText = String.valueOf(papLevel);
                int textWidth = font.width(papLevelText);

                // アイコンを一旦
                guiGraphics.pose().pushPose();
                // ちょっと奥に行ってて
                guiGraphics.pose().translate(0 ,0 ,200);
                //背景

                // サイズ縮小
                guiGraphics.pose().scale(0.5F, 0.5F, 1.0F);
                //描写

                float scaledXoffset = (xOffset + 1) * 2F;
                float scaledYoffset = (yOffset + 1) * 2F;
                //guiGraphics.fill((int) (scaledXoffset), (int) (scaledYoffset), (int) (scaledXoffset + textWidth + 1), (int) (scaledYoffset + 9),0xFFFFFFFF);
                //縁取り
                int timeBasedEdgeColor = getDarkAetherTimeBasedPhasingColor();

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue; // 中央（メイン文字）は飛ばす
                        guiGraphics.drawString(font, papLevelText, scaledXoffset + (dx * 0.8F), scaledYoffset + (dy * 0.8F), timeBasedEdgeColor, false);
                    }
                }
                guiGraphics.drawString(font, papLevelText, scaledXoffset, scaledYoffset, 0xffffff, false);

                guiGraphics.pose().popPose();
            }

        }

        return false;
    }

    private int getDarkAetherTimeBasedPhasingColor(){
        //システム時間を取得してtickに合わせる
        long time = System.currentTimeMillis() % 10000;

        //sin関数を利用して0.0~1.0の間で波打つ関数を作成
        float phase = (float)((Math.sin(time * 0.005F) + 1.0F) / 2.0F);
        phase = phase * phase;

        //System.out.println("Phase: " + phase);

        //以下はカラーをphaseに合わせていい感じに増減させるヤツ
        int colorR = (int) (100 + (60 * phase));
        int colorG = (int) (40 + (60 * phase));
        int colorB = (int) (100 + (155 * phase));

        // ARGB形式で返す (不透明度はFF)
        // Geminiに任せっぱなしなんだけど、さすがにビットシフト演算子は今の自分じゃ扱える気がしなかった
        return (255 << 24) | (colorR << 16) | (colorG << 8) | colorB;
    }


}
