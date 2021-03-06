package com.destro13.reutersnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.destro13.reutersnews.R;
import com.destro13.reutersnews.mvp.model.Article;
import com.destro13.reutersnews.util.StringParser;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pavlyknazariy on 25.05.17.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mArticles = Collections.emptyList();
    private Context mContext;

    public NewsAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsHolder) {
            Article article = mArticles.get(position);
            Picasso.with(mContext)
                    .load(StringParser.transformUrlToImage(mArticles.get(position).getUrlToImage()))
                    .into(((NewsHolder) holder).itemImageView);
            ((NewsHolder) holder).titleTextView.setText(article.getTitle());
            View.OnClickListener openInBrowser = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(mArticles.get(position).getUrl()));
                    mContext.startActivity(browser);
                }
            };
            ((NewsHolder) holder).titleTextView.setOnClickListener(openInBrowser);
            ((NewsHolder) holder).itemImageView.setOnClickListener(openInBrowser);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_image_item)
        ImageView itemImageView;
        @BindView(R.id.title_textView)
        TextView titleTextView;

        public NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


