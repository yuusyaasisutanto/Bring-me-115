package com.yuusyaasisutanto.bringme115.content.datagen;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.datagen.subdivide.BM115BlockStateGenerator;
import com.yuusyaasisutanto.bringme115.content.datagen.subdivide.BM115ItemModelGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BringMe115.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BM115DataGenHeadQuarter {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if (event.includeClient()){
            generator.addProvider(true, new BM115BlockStateGenerator(output, existingFileHelper));
            generator.addProvider(true, new BM115ItemModelGenerator(output, existingFileHelper));
        }

        if (event.includeServer()){
        }
    }
}
