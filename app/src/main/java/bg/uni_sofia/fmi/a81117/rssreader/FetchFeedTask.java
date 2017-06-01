package bg.uni_sofia.fmi.a81117.rssreader;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Xml;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krissy on 5/4/2017.
 */

public class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

    private String urlLink;
    private SwipeRefreshLayout mSwipeLayout;
    private EditText mEditText;
    private List<RssItem> mFeedModelList;
    private RecyclerView mRecyclerView;

    FetchFeedTask(SwipeRefreshLayout mSwipeLayout, EditText mEditText, List<RssItem> mFeedModelList, RecyclerView mRecyclerView) {
        this.mSwipeLayout= mSwipeLayout;
        this.mEditText = mEditText;
        this.mFeedModelList =mFeedModelList;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    protected void onPreExecute() {
        mSwipeLayout.setRefreshing(true);
        urlLink = mEditText.getText().toString();
    }

    public List<RssItem> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        List<RssItem> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = Jsoup.parse(result).text();
                }

                if (title != null && link != null && description != null) {
                    if(isItem) {
                        RssItem item = new RssItem(title, link, description);
                        items.add(item);
                    }
                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (TextUtils.isEmpty(urlLink))
            return false;

        try {
            if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                urlLink = "http://" + urlLink;

            URL url = new URL(urlLink);
            InputStream inputStream = url.openConnection().getInputStream();
            mFeedModelList = parseFeed(inputStream);
            return true;
        } catch (IOException e) {
            //Log.e(TAG, "Error", e);
        } catch (XmlPullParserException e) {
            // Log.e(TAG, "Error", e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        mSwipeLayout.setRefreshing(false);

        if (success) {
            mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
//        } else {
//            Toast.makeText(NewActivity.class,
//                    "Enter a valid Rss feed url",
//                    Toast.LENGTH_LONG).show();
        }
    }
}