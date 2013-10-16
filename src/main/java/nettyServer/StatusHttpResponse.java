package nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.Deque;
import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class StatusHttpResponse extends HttpRestonse {
    protected StatusHttpResponse(ChannelHandlerContext handlerContext, HttpRequest request) {
        super(handlerContext, request);
    }
  private StringBuilder responseContent = new StringBuilder();
   StatisticsCollector collector = new StatisticsCollector();
    @Override
    public void generateAnswerPage() {
        responseContent.setLength(0);
            AppendTable();
            sendResponse(responseContent);

        }
    void AppendTable()
    {

        responseContent.append("<html>\n" +
                "<head>\n"  +
                "<title></title>\n"+
                "<body>\n"  +
                "<h2> Total request number =   " +collector.getCount() +"</h2>"+
                "<h2> Number of the unique requests  =   " +collector.getUniqueCount() +"</h2>"+
                "<h2> Request Counter Table by IP</h2>"+
                "<p><table border=\"1\">\n" +
                "<tr>\n"+
                "<th>Host IP</th>\n"+
                "<th> Request Number</th>\n"+
                "<th> Time of last Request</th> \n"+
                "</tr>\n");
            Map<String,RequestParamsCollectingData> ipMap = collector.getIpMap();
        for (String key : ipMap.keySet())
        {
            responseContent.append(              "<tr>\n" +
                    "<td>"+key+"</td>\n" +
                    "<td>"+ipMap.get(key).getCount()+"</td>\n" +
                    "<td>"+ipMap.get(key).getTime()+"</td>\n" +
                    "</tr>\n" ) ;


        }
        responseContent.append("</table>  "+"</p>\n");
        responseContent.append("<h2> Redirect  Table by Url</h2>"+
                "<table border=\"1\">\n" +
                "<tr>\n"+
                "<th>Redirect URL</th>\n"+
                "<th> Redirect Count</th>\n"+

                "</tr>\n");
       Map <String,Integer > urlMap = collector.getUrlMap();
        for (String key : urlMap.keySet())
        {
            responseContent.append(              "<tr>\n" +
                    "<td>"+key+"</td>\n" +
                    "<td>"+urlMap.get(key)+"</td>\n" +

                    "</tr>\n" ) ;


        }
        responseContent.append("</table>  "+"</p>\n");
        responseContent.append("<h2> Log last 16 requests detail Table</h2>"+
                "<table border=\"1\">\n" +
                "<tr>\n"+
                "<th>Client Ip</th>\n"+
                "<th> Uri</th>\n"+
                "<th> Time</th>\n"+
                "<th> Sent Bytes</th>\n"+
                "<th> Received Bytes</th>\n"+
                "<th> Speed</th>\n"+
                "</tr>\n");
        Deque<LogTrafficCollectingData> datas = StatisticsCollector.getLogRequestQue();
        for (LogTrafficCollectingData d : datas)
        {
            responseContent.append(              "<tr>\n" +
                    "<td>"+d.getIp()+"</td>\n" +
                    "<td>"+d.getUri()+"</td>\n" +
                    "<td>"+d.getTime()+"</td>\n" +
                    "<td>"+d.getSentBytes()+"</td>\n" +
                    "<td>"+d.getReceivedBytes()+"</td>\n" +
                    "<td>"+d.getSpeed()+"</td>\n" +

                    "</tr>\n" ) ;


        }

        responseContent.append(
                "</table>  "+"</p>\n" + "</body>\n" + "</html>");
    }

        //To change body of implemented methods use File | Settings | File Templates.

}
