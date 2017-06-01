package com.destro13.reutersnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.destro13.reutersnews.R;
import com.destro13.reutersnews.model.Article;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by pavlyknazariy on 25.05.17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> implements StickyRecyclerHeadersAdapter<NewsAdapter.HeaderViewHolder> {
    private List<Article> mArticles = Collections.emptyList();
    private Context mContext;
    private int mLastDatePosition;
    private int mNewsByDate = 0;

    public NewsAdapter(List<Article> articles, Context context) {
        mArticles = articles;
        mContext = context;
    }

    public void setData(List<Article> articles){
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater .inflate(R.layout.item_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, final int position) {
        Article article = mArticles.get(position);
        Picasso.with(mContext)
                .load("http:" + (mArticles.get(position).getUrlToImage()))
                .into(holder.itemImageView);

        holder.titleTextView.setText(article.getTitle());

        View.OnClickListener openInBrowser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(mArticles.get(position).getUrl()));
                mContext.startActivity(browser);
            }
        };

        holder.titleTextView.setOnClickListener(openInBrowser);
        holder.itemImageView.setOnClickListener(openInBrowser);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getHeaderId(int position) {
            if (isDateChanged(position)) {
             //   mLastDatePosition = position;
                mNewsByDate = 0;
                return position ;
            }
            else {
                ++mNewsByDate;
                return -1;//position;//-1;//mLastDatePosition;
            }
    }

    private boolean isDateChanged(int position){
        if(position == 0)
            return true;

            if(mArticles.get(position).getPublishedAt().equals(mArticles.get(position - 1).getPublishedAt()))
                return false;

        return true;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final HeaderViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            if (mArticles.get(position).getPublishedAt() != null) {
                String header = mArticles.get(position).getPublishedAt();
                ((HeaderViewHolder) holder).dateHeader.setText(header);
            }
        }

//        holder.topStoriesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  setData(mArticles);
//            }
//        });

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView dateHeader;
        ImageButton topStoriesButton;

        public HeaderViewHolder(View view) {
            super(view);
            dateHeader = (TextView) view.findViewById(R.id.news_date_textView);
            topStoriesButton = (ImageButton) view.findViewById(R.id.top_story_image_button);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        private ImageView itemImageView;
        private TextView  titleTextView;

        public NewsHolder(View itemView) {
            super(itemView);
            itemImageView = (ImageView) itemView.findViewById(R.id.news_image_item);
            titleTextView = (TextView) itemView.findViewById(R.id.title_textView);
        }
    }

}


