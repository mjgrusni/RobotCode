package robotserver;

import java.util.Scanner;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RobotServerMain {

    private final int port;

    public RobotServerMain(int port){
        this.port = port;
    }

    public void run() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //client thread
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //server thread
        try{
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .localAddress(new InetSocketAddress(port))
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{
                            ch.pipeline().addLast(
                                    new RobotServerDecoder(),
                                    new RobotServerEncoder(),
                                    new RobotServerHandler());
                        }
                    });
            // Start the server.
            ChannelFuture f = b.bind().sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("HEY");
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
            System.out.println("Port Number: " + port);
        } else {
            System.out.println("Give me Port Number");
            port = sc.nextInt();
            System.out.println("Port Number: " + port);
        }
        new RobotServerMain(port).run();
    }
}
