package nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.LOCATION;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class RedirectHttpResponse extends HttpRestonse {
    protected RedirectHttpResponse(ChannelHandlerContext handlerContext, HttpRequest request) {
        super(handlerContext, request);
    }

    @Override
    public void generateAnswerPage() {
        String url = request.getUri();
        StringBuilder redirectedUlr =  new StringBuilder();
                redirectedUlr.append("http://");
        redirectedUlr.append(url.substring(url.indexOf('=') + 1));

        this.sendResponse(redirectedUlr);

        //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    protected void sendResponse (StringBuilder url )
    {
        ByteBuf content = new EmptyByteBuf(new PooledByteBufAllocator());

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.TEMPORARY_REDIRECT, content);

        response.headers().set(LOCATION, url);
        response.headers().set(CONTENT_LENGTH,content.readableBytes());

        // Write the response.
        handlerContext.channel().writeAndFlush(response);
    }
}
