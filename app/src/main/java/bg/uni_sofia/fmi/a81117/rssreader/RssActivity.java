package bg.uni_sofia.fmi.a81117.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Krissy on 5/4/2017.
 */

public class RssActivity extends Activity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private List<RssItem> mFeedModelList;
    private EditText text;
    private static String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        text = (EditText) findViewById(R.id.text);
        text.setText(urlString);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new FetchFeedTask(swipeLayout, text, mFeedModelList, recyclerView).execute((Void) null);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask(swipeLayout, text, mFeedModelList, recyclerView).execute((Void) null);
            }
        });
    }
}
