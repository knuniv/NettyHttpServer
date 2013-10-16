
package nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import static io.netty.buffer.Unpooled.copiedBuffer;

public class HttpNettyServerHandler extends ChannelInboundHandlerAdapter {

    private static StatisticsCollector collector = new StatisticsCollector();


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {


            HttpRequest req = (HttpRequest) msg;


            urlController(ctx, req);
            String url = req.getUri();
            collector.IncreaseCount();
            collector.ProcessRequestParams(ctx);
           collector.processConnectionsLog(ctx,url);



        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

private void urlController( ChannelHandlerContext ctx, HttpRequest request)
{
    String url = request.getUri();

    if(url.equals("/"))
    {
        new HelloWorldHttpResponse(ctx,request).generateAnswerPage();
        return;
    }
    if(url.equals("/status")||url.equals("/status/"))
    {
        new StatusHttpResponse(ctx,request).generateAnswerPage();
        return;
    }
    String rUrl=  (String) url.subSequence(0,url.indexOf(61)+1);
    if (rUrl.equals("/redirect?url="))
    {
       collector.processRedirectRequest(url.substring(url.indexOf(61)+1));
       new    RedirectHttpResponse(ctx,request).generateAnswerPage();
        return;

    }
     writeErrorPage();

}

    private void writeErrorPage() {
    }
}
