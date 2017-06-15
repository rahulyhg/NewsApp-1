package com.destro13.reutersnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.destro13.reutersnews.NewsActivity;
import com.destro13.reutersnews.R;
import com.destro13.reutersnews.mvp.model.Source;
import com.destro13.reutersnews.util.StringParser;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by pavlyknazariy on 06.06.17.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceHolder> {
    private List<Source> mSources = Collections.emptyList();
    private Context mContext;

    public SourceAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Source> sources) {
        this.mSources = sources;
        notifyDataSetChanged();
    }

    @Override
    public SourceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.source_item, parent, false);
        return new SourceAdapter.SourceHolder(view);
    }

    @Override
    public void onBindViewHolder(SourceHolder holder, final int position) {
        holder.sourceTitleTextView.setText(mSources.get(position).getName());
        holder.sourceLogoImageView.setImageResource(
                mContext.getResources().getIdentifier(StringParser.transformSourceId(mSources.get(position).getId()),
                        "drawable",
                        mContext.getPackageName()));
        holder.sourceLogoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, NewsActivity.class)
                        .putExtra("source", mSources.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSources.size();
    }

    class SourceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.source_logo_image_item)
        ImageView sourceLogoImageView;
        @BindView(R.id.source_title_textView)
        TextView sourceTitleTextView;

        public SourceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
