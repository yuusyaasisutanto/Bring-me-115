package com.yuusyaasisutanto.bringme115.content.screen.primitivemachine;

import com.yuusyaasisutanto.bringme115.BringMe115;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BM115PrimitivePaPScreen extends AbstractContainerScreen<BM115PrimitivePaPMenu> {

    private static final ResourceLocation BASE_TEXTURE =
            new ResourceLocation(BringMe115.ID, "textures/gui/primitive_machine_gui-assets/base.png");
    private static final ResourceLocation BUTTON1_TEXTURE =
            new ResourceLocation(BringMe115.ID, "textures/gui/primitive_machine_gui-assets/button1.png");

    public BM115PrimitivePaPScreen(BM115PrimitivePaPMenu papMenu, Inventory inventory, Component component) {
        super(papMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int w, int h) {
        int posW = (this.width - this.imageWidth)/2;
        int posH = (this.height - this.imageHeight)/2;
        guiGraphics.blit(BASE_TEXTURE, posW, posH, 0,0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void init(){
        super.init();

        this.addRenderableWidget(new ImageButton(
                this.leftPos + 150, this.topPos + 10,
                15, 45,
                0,0,
                0,
                BUTTON1_TEXTURE,
                (button) -> {

                }))
                .setTooltip(Tooltip.create(Component.translatable("container.bringme115.primitive_pap.button1")));
    }
}
