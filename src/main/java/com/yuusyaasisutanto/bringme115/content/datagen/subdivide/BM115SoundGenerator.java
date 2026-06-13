package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115SoundRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class BM115SoundGenerator extends SoundDefinitionsProvider {

    public BM115SoundGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, BringMe115.ID, helper);
    }

    @Override
    public void registerSounds() {
        add(BM115SoundRegister.U_FOUND_EE.get(), definition()
                .subtitle("subtitle.bringme115.u_found_ee")
                .with(sound(new ResourceLocation(BringMe115.ID, "u_found_ee")))
        );

    }

}
