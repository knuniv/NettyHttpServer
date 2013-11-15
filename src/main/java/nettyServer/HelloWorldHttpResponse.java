package nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldHttpResponse extends HttpRestonse {
    protected HelloWorldHttpResponse(ChannelHandlerContext handlerContext, HttpRequest request) {
        super(handlerContext, request);
    }

    @Override
    public void generateAnswerPage() {
        StringBuilder  responseContent = new StringBuilder();
        responseContent.append("<html>\n" +
                "<header><title>This is title</title></header>\n" +
                "<body>\n" + "Hello world\n" +
                 "</body>\n" + "</html>");
        responseContent.append("<html>\n" +
                "<header><title>This is title</title></header>\n" +
                "<body>\n" + "Hello world\n" +
                "</body>\n" + "</html>");
        try {


            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        sendResponse(responseContent);
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
