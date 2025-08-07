package com.yuusyaasisutanto.bringme115.content.testbomb;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.BM115ItemRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class BM115TestItemBombHandler {
    public static void attach(AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().is(BM115ItemRegister.CODINGPRACTICE.get())){
            event.addCapability(ResourceLocation.fromNamespaceAndPath(BringMe115.ID, "testbomb"), new BM115TestItemBombCapability());
        }
    }
}
