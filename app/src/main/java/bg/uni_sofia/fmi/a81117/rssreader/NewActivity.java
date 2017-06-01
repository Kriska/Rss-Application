package bg.uni_sofia.fmi.a81117.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Krissy on 5/3/2017.
 */

public class NewActivity extends Activity {


    private RecyclerView recyclerView;
    private EditText editText;
    private Button fetchFeedButton;
    private SwipeRefreshLayout swipeLayout;

    private List<RssItem> feedModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editText = (EditText) findViewById(R.id.rssFeedEditText);
        fetchFeedButton = (Button) findViewById(R.id.fetchFeedButton);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchFeedTask(swipeLayout, editText, feedModelList, recyclerView).execute((Void) null);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask(swipeLayout, editText, feedModelList, recyclerView).execute((Void) null);
            }
        });
    }


}
