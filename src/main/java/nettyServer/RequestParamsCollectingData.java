package nettyServer;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class RequestParamsCollectingData extends CollectingData {

    private int count;


    public RequestParamsCollectingData() {
        time=CurrentTimeStamp();
        count=1;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void incrementCount ()  {
        count++;
    }

}
