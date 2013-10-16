package nettyServer;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 10/16/13
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogTrafficCollectingData extends CollectingData {
    public LogTrafficCollectingData(String ip, String uri, long sentBytes, long receivedBytes, long speed) {
        this.ip = ip;
        this.uri = uri;
        this.sentBytes = sentBytes;
        this.receivedBytes = receivedBytes;
        this.speed = speed;

    }

    public String getIp() {
        return ip;
    }

    public String getUri() {
        return uri;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public long getSpeed() {
        return speed;
    }

    private  String ip;
    private  String uri;
    private long sentBytes;
    private long receivedBytes;
    private long speed;

}
