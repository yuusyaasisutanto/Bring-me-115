package com.yuusyaasisutanto.bringme115.content.datagen.subdivide;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;

public class BM115ItemModelGenerator extends ItemModelProvider {


    public BM115ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BringMe115.ID, existingFileHelper);
    }

    // メインのDatagen用メソッド
    @Override
    protected void registerModels() {

        simpleItem(BM115ItemRegister.ELEMENT115);

        //blockのアイテム化
        blockItem(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE);

        // BM115ItemRegister.AETHERIUM_CRYSTAL専用の枠
        forAetheriumCrystalBuilingUp();
    }



    // 以下設定用ヘルパーメソッド

    private void simpleItem(RegistryObject<Item> item){
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(BringMe115.ID, "item/" + item.getId().getPath()));
    }

    private void blockItem(RegistryObject<Block> block){
        String blockName = String.valueOf(block.getId().getPath());
        System.out.println(blockName);
        withExistingParent(blockName, new ResourceLocation(BringMe115.ID, "block/" + blockName));
    }

    private void forAetheriumCrystalBuilingUp(){

        // 先にヘルパーを呼び出す
        ItemModelBuilder modellvl1 = forAetheriumCrystalIndividualItem("aetherium_crystal_1");
        ItemModelBuilder modellvl2 = forAetheriumCrystalIndividualItem("aetherium_crystal_2");
        ItemModelBuilder modellvl3 = forAetheriumCrystalIndividualItem("aetherium_crystal_3");
        ItemModelBuilder modellvl4 = forAetheriumCrystalIndividualItem("aetherium_crystal_4_more");

        withExistingParent(BM115ItemRegister.AETHERIUM_CRYSTAL.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0",new ResourceLocation(BringMe115.ID, "item/aetherium_crystal_err"))

                .override()
                    .predicate(new ResourceLocation(BringMe115.ID, "pap_level_texture"), 0.99F)
                    .model(modellvl1)
                .end()

                .override()
                    .predicate(new ResourceLocation(BringMe115.ID, "pap_level_texture"), 1.99F)
                    .model(modellvl2)
                .end()

                .override()
                    .predicate(new ResourceLocation(BringMe115.ID, "pap_level_texture"), 2.99F)
                    .model(modellvl3)
                .end()

                .override()
                    .predicate(new ResourceLocation(BringMe115.ID, "pap_level_texture"), 3.99F)
                    .model(modellvl4)
                .end();
    }

        // Aetherum_Crystal専用の枠
        private ItemModelBuilder forAetheriumCrystalIndividualItem(String name){
            return withExistingParent(name, new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(BringMe115.ID, "item/" + name));
        }
        /*
         ↑がなぜそのままvoidでなくわざわざreturnで返しているか気になったので聞いた
         以下Geminiの意図

         あなたの言う通り、ただ単に withExistingParent を呼び出すだけでもJSONファイル自体は問題なく出力されます。
         そこをあえて return で返すのは、「呼び出し側のコードをスマートに1本のチェーンにまとめるため」、そして「文字列の手入力を減らして安全に開発するため」という
         Javaならではの「綺麗で堅牢なコードにするための工夫」だったわけです。

         もし void にして文字列で指定する方式をとると、条件分岐の数だけ "item/aetherium_crystal_1" と手書きすることになり
         どこか1箇所でもスペルがズレたらゲーム内でテクスチャがバグってしまいます。

         しかし、subCrystalItem が生成したモデルオブジェクトそのものを return で引き渡すようにしておけば
         Javaのシステムが「今作ったこのモデルを、そのままこの分岐に登録するね」とダイレクトに紐付けてくれるため
         名前の打ち間違いによるバグが起きる余地自体を完全に消し去ることができます。
        */

}
