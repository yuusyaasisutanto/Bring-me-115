package com.yuusyaasisutanto.bringme115.content.register;


// ネットワーク周りの把握のために使用する仮ファイル

import com.yuusyaasisutanto.bringme115.BringMe115;
import com.yuusyaasisutanto.bringme115.content.network.BM115PrimitiveMachineButtonPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class BM115ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    // しれっとGeminiはこのメソッドを変数のようになんの説明もなく入れてて笑った
    // いや、もしかしたらこういう使い方こそがオブジェクト指向の考え方の素なのかもしれない
    // 内容はわかる、このメソッドを呼び出すと値を返したのちにポストインクリメントを行いpacketIdの数値を1増加させる
    private static int id(){ return packetId++; }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(BringMe115.ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        //パケットの登録、らしい
        net.messageBuilder(BM115PrimitiveMachineButtonPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BM115PrimitiveMachineButtonPacket::new)
                .encoder(BM115PrimitiveMachineButtonPacket::toBytes)
                .consumerMainThread(BM115PrimitiveMachineButtonPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }
}
