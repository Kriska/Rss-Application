package bg.uni_sofia.fmi.a81117.rssreader;

/**
 * Created by Krissy on 5/4/2017.
 */

public class RssItem {
    public String title;
    public String link;
    public String description;

    public RssItem(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }
}
