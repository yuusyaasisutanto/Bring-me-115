package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.BringMe115;

import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BM115BlockStateGenerator extends BlockStateProvider {
    public BM115BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BringMe115.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 使いまわす用の115きらめき用テクスチャのpathを通す、ﾊﾟｽｯw（パスを通す音）
        String element115BaseGrow = "block/elemental115_base";
        // 破壊パーティクルはバニラのものも使えるように意図的に広めにとってある。
        String particleTexture = "";

        particleTexture = "minecraft:block/deepslate";
        // ↓カリングの都合上、鉱石ブロックにデュアルレイヤー方式はよろしくないと判断
        // dualLayeredBlock(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE, element115BaseGrow, "deepslate_element115_ore_mask", particleTexture );
            simpleBlock(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get());
//            ,
//                    models().withExistingParent(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.getId().getPath(), new ResourceLocation("block/block"))
//                            .texture("particle", new ResourceLocation(particleTexture))
//                            .texture("all", new ResourceLocation(BringMe115.ID, "block/" + BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.getId().getPath())));

        particleTexture = "bringme115:block/elemental115_base";
        dualLayeredBlock(BM115BlockRegister.RAW_ELEMENT115_BLOCK, element115BaseGrow, "raw_element115_block_mask", particleTexture );



        //Primitive machine用、わざわざメソッドを立てるほど使いまわさなさそうなので


        horizontalBlock(BM115BlockRegister.PRIMITIVE_MACHINE.get(), models().getExistingFile(new ResourceLocation(BringMe115.ID, "primitive_machine")));

    }

    // ブロック用2層レイヤー制作用ヘルパーメソッド
    private void dualLayeredBlock(RegistryObject<Block> blockRegistryObject,String underTextureName, String maskTextureName, String particleTexturePath){
        Block block = blockRegistryObject.get();
        String blockName = blockRegistryObject.getId().getPath();



        BlockModelBuilder modelBuilder = models().withExistingParent(blockName, new ResourceLocation("block/block"))

                .texture("particle", new ResourceLocation(particleTexturePath))
                .texture("under_texture", new ResourceLocation(BringMe115.ID, underTextureName))
                .texture("mask_texture", new ResourceLocation(BringMe115.ID,"block/" + maskTextureName))

                // ベーステクスチャの設定
                .element()
                    .from(0f, 0f, 0f).to(16,16,16)
                    .allFaces(((direction, faceBuilder) -> faceBuilder.texture("#under_texture")))
                .end()

                // マスク用のテクスチャの設定
                .element()
                    .from(0,0,0).to(16,16,16)
                    .allFaces(((direction, faceBuilder) -> faceBuilder.texture("#mask_texture")))
                .end();

        simpleBlock(block, modelBuilder);
    }
}
