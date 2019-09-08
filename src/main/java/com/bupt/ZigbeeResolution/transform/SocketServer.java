package com.bupt.ZigbeeResolution.transform;

import com.bupt.ZigbeeResolution.service.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class SocketServer {

    @Autowired
    private UserService userService;

    @Autowired
    private GatewayGroupService gatewayGroupService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SceneRelationService sceneRelationService;

    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
    private static Map<String, byte[]> messageMap = new ConcurrentHashMap<String, byte[]>();

    private Channel serverChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Value("${socket.bind_address}")
    private String host;
    @Value("${socket.bind_port}")
    private Integer port;
//    @Value("${mqtt.adaptor}")
//    private String adaptorName;

    @Value("${socket.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${socket.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${socket.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;

    @PostConstruct
    public void start() throws Exception{
        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        // Decoders
                        //ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                        ch.pipeline().addLast("bytesDecoder", new ByteArrayDecoder());
                        // Encoder
                        //ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
                        ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());
                        ch.pipeline().addLast(new OutBoundHandler());
                        ch.pipeline().addLast(new IdleStateHandler(60,0,0, TimeUnit.SECONDS), new TransportHandler(userService,gatewayGroupService,deviceTokenRelationService, sceneService, sceneRelationService));
                    }
                });
        //b.bind(port);
        // Start the server.
        serverChannel = b.bind(port).sync().channel();
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        try {
            serverChannel.close().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static Map<String, Channel> getMap() {
        return map;
    }

    public static void setMap(Map<String, Channel> map) {
        SocketServer.map = map;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    /**
     * @return the messageMap
     */
    public static Map<String, byte[]> getMessageMap() {
        return messageMap;
    }

    /**
     * @param messageMap the messageMap to set
     */
    public static void setMessageMap(Map<String, byte[]> messageMap) {
        SocketServer.messageMap = messageMap;
    }
}
