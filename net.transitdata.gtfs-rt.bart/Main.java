import java.io.IOException;
import java.net.URL;
import com.google.transit.realtime.GtfsRealtime;

public class Main {
    static URL url;
    public static void main (String[] args) {
        try {
            url = new URL("http://api.bart.gov/gtfsrt/tripupdate.aspx");
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("getting feed from " + url.toString());

        GtfsRealtime.FeedMessage feed = null;
        try {
            feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (feed != null) {
            //This is a Java 8 Stream version of a filtered for loop
            feed.getEntityList()
                    .stream()
                    .filter(GtfsRealtime.FeedEntity::hasTripUpdate)
                    .forEach(entity -> {
                        System.out.println(entity.getTripUpdate().toString());
                    });
        }
    }

}