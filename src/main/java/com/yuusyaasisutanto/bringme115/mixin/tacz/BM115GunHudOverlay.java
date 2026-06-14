package com.yuusyaasisutanto.bringme115.mixin.tacz;

import com.tacz.guns.client.gui.overlay.GunHudOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GunHudOverlay.class)
public class BM115GunHudOverlay {

    @Inject(method = "render", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"),remap = false)
    public void BM115InjectedPaPHUDtoRender(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height, CallbackInfo ci){
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        LocalPlayer player = mc.player;
        ItemStack stack = player.getMainHandItem();

        CompoundTag tag = stack.getTag();
        int paplvl = tag != null ? tag.getCompound("BM115Modify").getInt("PaPlvl") : 0;

        if (paplvl > 0) {
            Component papedTextIndicator = Component.translatable("hud.bringme115.tacz.mixin.is_paped");
            Component papedLevelIndicator = Component.translatable("hud.bringme115.tacz.mixin.is_paped.level")
                                                        .append(Component.literal(String.valueOf(paplvl)));

            float scale = 0.5F;
            float backTo1 = 1.0F / scale;

            graphics.pose().pushPose();
                graphics.pose().scale(scale, scale, 1.0F);
                // height - 44
                graphics.drawString(font,papedTextIndicator , (int) ((width - 100) * backTo1), (int) ((height - 58) * backTo1), 0xFFFFFF, false);
            graphics.pose().popPose();

            graphics.pose().pushPose();
                graphics.drawString(font,papedLevelIndicator , width - 55, height - 53, 0xFFFFFF, false);
            graphics.pose().popPose();
        }
    }
}
