package com.yuusyaasisutanto.bringme115.content.datagen;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.datagen.subdivide.*;
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

        BM115BlockTagGenerator blockTags = new BM115BlockTagGenerator(output, lookupProvider, existingFileHelper);

        if (event.includeClient()){
            generator.addProvider(true, new BM115BlockStateGenerator(output, existingFileHelper));
            generator.addProvider(true, new BM115ItemModelGenerator(output, existingFileHelper));
        }

        if (event.includeServer()){
            generator.addProvider(true, BM115LootTableGenerator.create(output));
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new BM115ItemTagGenerator(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
            generator.addProvider(true, new BM115RecipeGenerator(output));
        }
    }
}
