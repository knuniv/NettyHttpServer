package nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class HttpRestonse {
    protected HttpRestonse(ChannelHandlerContext handlerContext, HttpRequest request) {
        this.handlerContext = handlerContext;
        this.request = request;
    }

    protected ChannelHandlerContext handlerContext;
    protected HttpRequest request  ;
    public abstract void generateAnswerPage( );

    protected void sendResponse(StringBuilder genPage ){
        ByteBuf buf = copiedBuffer(genPage.toString(), CharsetUtil.UTF_8);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, buf.readableBytes());



        // Write the response.
        handlerContext.channel().writeAndFlush(response);
    };

}
