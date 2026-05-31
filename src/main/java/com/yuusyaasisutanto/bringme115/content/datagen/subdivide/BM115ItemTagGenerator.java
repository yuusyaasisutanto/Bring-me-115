package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BM115ItemTagGenerator extends ItemTagsProvider {
    public BM115ItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, BringMe115.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Block側で作成したタグをItem側にコピー
        TagKey<Block> blockForgeTag = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores/element115"));
        TagKey<Item> itemForgeTag = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ores/element115"));

        this.copy(blockForgeTag, itemForgeTag);

        // raw element115用タグ
        TagKey<Item> rawMaterialElement115 = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_materials/element115"));

        this.tag(rawMaterialElement115).add(BM115ItemRegister.RAW_ELEMENT115.get());
    }
}
