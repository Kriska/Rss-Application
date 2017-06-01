package bg.uni_sofia.fmi.a81117.rssreader;

import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Krissy on 5/4/2017.
 */

public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

    private List<RssItem> rssItems;
    private TextView link;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter(List<RssItem> RssItems) {

        rssItems = RssItems;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        final RssItem RssItem = rssItems.get(position);
        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText(RssItem.title);
        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setText(RssItem.description);
        link = ((TextView)holder.rssFeedView.findViewById(R.id.linkText));
        link.setText(RssItem.link);
        Linkify.addLinks(link, Linkify.WEB_URLS);
    }

    @Override
    public int getItemCount() {

        return rssItems.size();
    }
}
