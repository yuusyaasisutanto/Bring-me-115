package com.yuusyaasisutanto.bringme115.content.register;

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.items.interfaces.ICreativeTabVariantsItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BM115TabRegister {
    public static final DeferredRegister<CreativeModeTab> REGISTRY
            = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BringMe115.ID);

    public static final RegistryObject<CreativeModeTab> MAIN = REGISTRY.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.bringme115.main"))
            .icon(() -> new ItemStack(BM115ItemRegister.ELEMENT115.get()))
            .displayItems((parameters, output) -> {
                BM115ItemRegister.MAIN.forEach(object -> {
                    // 一旦格納させる
                    Item item = object.get();

                    // 型チェックを挟むことで「バリアントが存在するアイテムならば個別に登録」の処理を行える
                    // サンキューGemini、フォーエバーGemini
                    if (item instanceof ICreativeTabVariantsItem variantsItem){
                        // 拡張for文
                        for (ItemStack itemStack : variantsItem.getTabVariants()){
                            output.accept(itemStack);
                        }
                    } else {
                        // そうじゃないならそのまま登録
                        output.accept(item);
                    }
                });
            })
            .build());

}
