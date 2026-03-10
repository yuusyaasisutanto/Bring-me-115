package com.yuusyaasisutanto.bringme115.content.screen.primitivemachine;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BM115PrimitivePaPScreen extends AbstractContainerScreen<BM115PrimitivePaPMenu> {

    //次回以降Screenのクラスを書ききって動作確認まで
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BringMe115.ID, "textures/gui/primitive_machine_gui.png");

    public BM115PrimitivePaPScreen(BM115PrimitivePaPMenu papMenu, Inventory inventory, Component component) {
        super(papMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {

    }
}
