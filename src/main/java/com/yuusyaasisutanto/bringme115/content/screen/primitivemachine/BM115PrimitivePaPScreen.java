package com.yuusyaasisutanto.bringme115.content.screen.primitivemachine;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.network.BM115PrimitiveMachineButtonPacket;
import com.yuusyaasisutanto.bringme115.content.register.BM115ModMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class BM115PrimitivePaPScreen extends AbstractContainerScreen<BM115PrimitivePaPMenu> {

    private static final ResourceLocation BASE_TEXTURE =
            new ResourceLocation(BringMe115.ID, "textures/gui/primitive_machine_gui-assets/primitive_machine.png");
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

        this.inventoryLabelX = -9999;
        this.titleLabelX = -9999;

        this.addRenderableWidget(new ImageButton(
                this.leftPos + 150, this.topPos + 10,
                15, 45,
                0,0,
                0,
                BUTTON1_TEXTURE,
                (button) -> {
                    onButtonClicked();
                }))
                .setTooltip(Tooltip.create(Component.translatable("container.bringme115.primitive_pap.button1")));
    }

    private void onButtonClicked(){
        BM115ModMessages.sendToServer(new BM115PrimitiveMachineButtonPacket());
    }


    // ツールチップなどの描写
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        // 背景暗転
        this.renderBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // ツールチップの描写
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int p_282681_, int p_283686_) {
        guiGraphics.drawString(this.font, Component.translatable("container.bringme115.primitive_pap.title")
                , 4, 4, 0xFFFFFF, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {

        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            super.renderTooltip(guiGraphics, mouseX, mouseY);
        } else if (this.hoveredSlot != null) {
            Component customTooltip = null;

            int slotIndex = this.hoveredSlot.getSlotIndex();

            if (slotIndex == 0) {
                customTooltip = Component.translatable("container.bringme115.primitive_pap.tooltip.weaponslot");
            } else if (slotIndex == 1) {
                customTooltip = Component.translatable("container.bringme115.primitive_pap.tooltip.crystalslot");
            }

            if (customTooltip != null) {
                guiGraphics.renderTooltip(this.font, (Component) customTooltip, mouseX, mouseY);
            }

        } else {
            super.renderTooltip(guiGraphics, mouseX, mouseY);
        }
    }
}
