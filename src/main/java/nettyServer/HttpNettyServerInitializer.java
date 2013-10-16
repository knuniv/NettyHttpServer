
package nettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

public class HttpNettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast("shaper", new ChannelTrafficShapingHandler(1000));
        p.addLast("codec", new HttpServerCodec());

        p.addLast("handler", new HttpNettyServerHandler());
    }
}
