package com.yuusyaasisutanto.bringme115.content.pap.itemicon;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class BM115ItemIconPaPLevelindicator implements IItemDecorator {

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        // nullチェックの簡易化のために先んじて定義
        CompoundTag nbt = stack.getTag();

        if (nbt != null && nbt.contains("BM115Modify")){

            int papLevel = nbt.getCompound("BM115Modify").getInt("PaPlvl");

            if (papLevel > 0){
                String papLevelText = String.valueOf(papLevel);

                // アイコンを一旦
                guiGraphics.pose().pushPose();
                // ちょっと奥に行ってて
                guiGraphics.pose().translate(0 ,0 ,200);
                //描写
                guiGraphics.drawString(font, papLevelText, xOffset + 1, yOffset + 1, 0xFFFFFF, true);

                guiGraphics.pose().popPose();
            }

        }

        return false;
    }
}
