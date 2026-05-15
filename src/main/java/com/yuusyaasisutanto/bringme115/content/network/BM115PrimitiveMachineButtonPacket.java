package com.yuusyaasisutanto.bringme115.content.network;


import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.yuusyaasisutanto.bringme115.content.pap.BM115PaPAddictionToItem.tryInfusing115toWeapon;

// おそらく今後制作していく中でInterfaceを用いたクラスの置き換えを行うと思うが
// 今はどうすれば「正しく置き換えられるか」が分からないため、泥臭く砂粒を手で数えるような非効率な方法を取る
// Geminiに聞いてその実装を自分なりに解釈して実装しているが、素人レベルのコードでしかないのは申し訳ない
// 本当にごめん

public class BM115PrimitiveMachineButtonPacket {
    public BM115PrimitiveMachineButtonPacket(){
        //必要なら「どのボタンか」などの引数を持たせるらしい
    }

    // デコード
    public BM115PrimitiveMachineButtonPacket(FriendlyByteBuf buffer){
    }

    // エンコード
    public void toBytes(FriendlyByteBuf buffer){

    }

    // サーバー処理内容
    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            // playerがnullではなく、おそらく開いているメニューがカスタムしたメニューである場合、みたいな感じだと思ったので
            // 今回はBM115PrimitivePaPMenuを用いている
            // instanceof（インスタンスであるか）という演算子を用いている？
            if (player != null) {
                tryInfusing115toWeapon(player);
            };
                // テスト用にplayerにシステムメッセージを送りつける
                player.sendSystemMessage(Component.literal("PaPing..."));
            });

        context.setPacketHandled(true);
    }
}
