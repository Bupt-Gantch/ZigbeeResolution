/*package com.bupt.ZigbeeResolution.transform;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class SocketServiceInitializer extends ChannelInitializer<SocketChannel>{

    public SocketServiceInitializer()
    {

    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception{
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder", new ByteArrayDecoder());
        pipeline.addLast("encoder", new ByteArrayEncoder());

        TransportHandler handler = new TransportHandler();
        pipeline.addLast(handler);
        socketChannel.closeFuture().addListener(handler);
    }
}*/
