package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BM115BlockTagGenerator extends BlockTagsProvider {
    public BM115BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BringMe115.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // ピッケルで採集可能
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        BM115BlockRegister.RAW_ELEMENT115_BLOCK.get(),
                        BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get(),
                        BM115BlockRegister.PRIMITIVE_MACHINE.get()
                );

        // ダイヤツールが必須
        // やっぱ鉄ツール要求にする、片手間に集められるけど量が要求されるって感じ
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        BM115BlockRegister.RAW_ELEMENT115_BLOCK.get(),
                        BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get()
                );

        // element115鉱石のタグをフォージ側に作成
        TagKey<Block> tagElement115 = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores/element115"));

        // ↑に登録させる
        this.tag(tagElement115).add(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get());

    }
}
