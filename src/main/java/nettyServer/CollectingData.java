package nettyServer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: brave
 * Date: 15.10.13
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class CollectingData {
    protected String time;

    protected CollectingData() {
        time=CurrentTimeStamp();
    }

    public String getTime() {
        return time;
    }
    public void updateTime() {
        time=CurrentTimeStamp();
    }
    public  String CurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
