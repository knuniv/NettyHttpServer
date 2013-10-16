package nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static io.netty.handler.codec.http.HttpHeaders.getHost;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsCollector {
    private static  AtomicInteger count= new AtomicInteger();

    private static Map<String,RequestParamsCollectingData> ipMap = new ConcurrentHashMap();
    private static Map <String,Integer> urlMap = new ConcurrentHashMap();
    private static Deque<LogTrafficCollectingData> logRequestQue= new ConcurrentLinkedDeque<LogTrafficCollectingData>();
    private static AtomicInteger uniqueCount = new AtomicInteger();

    public Map<String, RequestParamsCollectingData> getIpMap() {
        return ipMap;
    }

    public static Deque<LogTrafficCollectingData> getLogRequestQue() {
        return logRequestQue;
    }
    public  Map<String, Integer> getUrlMap() {
        return urlMap;
    }
    public void processConnectionsLog (ChannelHandlerContext ctx,String url)
    {
        ChannelTrafficShapingHandler ch=(ChannelTrafficShapingHandler)((ChannelTrafficShapingHandler) ctx.channel().pipeline().get("shaper")) ;
        ch.trafficCounter().stop();

        if(logRequestQue.size()<16)
        {

            logRequestQue.addLast(new LogTrafficCollectingData(getClientIp(ctx), url, ch.trafficCounter().cumulativeWrittenBytes() ,
                    ch.trafficCounter().cumulativeReadBytes(), ch.trafficCounter().lastWriteThroughput() ));
        }
        else
        {
            logRequestQue.removeFirst();
            logRequestQue.addLast(new LogTrafficCollectingData(getClientIp(ctx), url, ch.trafficCounter().cumulativeWrittenBytes() ,
                    ch.trafficCounter().cumulativeReadBytes(), ch.trafficCounter().lastWriteThroughput() ));
        }
        ch.trafficCounter().resetCumulativeTime();
    }


    public  AtomicInteger getCount() {
        return count;
    }

    public AtomicInteger getUniqueCount() {
       return   uniqueCount;
    }

    void IncreaseCount (){
        count.incrementAndGet();

    }
    void ProcessRequestParams (ChannelHandlerContext ctx ){


             String inetAddr = getClientIp(ctx);

           if (!ipMap.containsKey(inetAddr))
           {

               ipMap.put(inetAddr,new RequestParamsCollectingData());
               uniqueCount.incrementAndGet();

           }
           else {
               ipMap.get(inetAddr).incrementCount();
               ipMap.get(inetAddr).updateTime();

           }
        }

    void processRedirectRequest( String redirectedUrl)
    {
        if (!urlMap.containsKey(redirectedUrl))
        {

            urlMap.put(redirectedUrl,1);


        }
        else {


            urlMap.put(redirectedUrl,urlMap.get(redirectedUrl)+1);


        }
    }

      private String getClientIp (ChannelHandlerContext ctx )
      {
          String socketAddress =ctx.channel().remoteAddress().toString();
          String inetAddr= (String) socketAddress.subSequence(1,socketAddress.indexOf(58));
          return inetAddr;
      }

}
