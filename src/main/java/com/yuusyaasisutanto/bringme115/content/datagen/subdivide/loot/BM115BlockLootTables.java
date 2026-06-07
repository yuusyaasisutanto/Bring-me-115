package com.yuusyaasisutanto.bringme115.content.datagen.subdivide.loot;

import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Collectors;

public class BM115BlockLootTables extends BlockLootSubProvider {

    // バニラの基本機能を有効化してコンストラクタに渡すって解説されたけどどういう事やねん
    // Gemini曰く第一引数は除外設定、Set.of()で空を返しており
    // 第二引数はマイクラの機能を有効化しながらDatagenを動かす設定らしい
    // 以下コピペ↓
    // マインクラフトは、バージョンアップの際に「まだ実験中の新機能（例：1.21のトライアルチャンバーや特定のアイテムなど）」を
    // ゲーム内の機能フラグ（Feature Flags）というスイッチで管理しています。
    // FeatureFlags.REGISTRY.allFlags() を渡すことで、マイクラに対して
    // 「現在このバージョンのバニラに搭載されている、実験中を含むすべての機能やアイテムのデータをフル活用して、ドロップのJSONを計算してください」
    // という許可証を渡していることになります。
    public BM115BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // そのものをドロップさせる
        this.dropSelf(BM115BlockRegister.PRIMITIVE_MACHINE.get());
        this.dropSelf(BM115BlockRegister.RAW_ELEMENT115_BLOCK.get());

        // 鉱石
        this.add(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get(),
                block -> createRawOreDrops(BM115BlockRegister.DEEPSLATE_ELEMENT115_ORE.get(),
                        BM115ItemRegister.RAW_ELEMENT115.get()
                        , 1.0F, 2.0F)
        );

    }

    // RawOreをドロップするタイプの鉱石、シルクタッチ対応を添えて
    // 大体2~5がバニラの銅ドロップの割合
    protected LootTable.Builder createRawOreDrops(Block block, Item item, Float min, Float max){
        return createSilkTouchDispatchTable(block,
                    this.applyExplosionDecay(block,
                            LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                ));
    }

    // modが実装してるブロックすべてを拾って、不足分がある場合コンパイラから中指が郵送される（エラー）
    @Override
    protected Iterable<Block> getKnownBlocks() {
        // .map(RegistryObject::get)::iterator;という書き方も見かけたが、個人的にこっちの方が視覚的に分かりやすいと思ったのでこっちに
        return BM115BlockRegister.REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
