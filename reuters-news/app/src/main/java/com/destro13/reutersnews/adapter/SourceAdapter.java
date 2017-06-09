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
import com.destro13.reutersnews.model.Source;
import com.destro13.reutersnews.util.StringParser;

import java.util.Collections;
import java.util.List;


/**
 * Created by pavlyknazariy on 06.06.17.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceHolder> {
    private List<Source> mSources = Collections.emptyList();
    private Context mContext;

    Intent mIntent;
    Source source;

    public SourceAdapter(List<Source> sources, Context context) {
        mSources = sources;
        mContext = context;
    }

    public void setData(List<Source> sources){
        this.mSources = sources;
        notifyDataSetChanged();
    }

    @Override
    public SourceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater .inflate(R.layout.source_item, parent, false);
        return new SourceAdapter.SourceHolder(view);
    }

    @Override
    public void onBindViewHolder(SourceHolder holder, final int position) {
        source = mSources.get(position);

        holder.sourceTitleTextView.setText(source.getName());

        int id = mContext.getResources().getIdentifier(StringParser.transformSourceId(source.getId()), "drawable", mContext.getPackageName());

        holder.sourceLogoImageView.setImageResource(id);

        mIntent = new Intent(mContext, NewsActivity.class);
        holder.sourceLogoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("source", mSources.get(position).getId());
                mContext.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSources.size();
    }


    class SourceHolder extends RecyclerView.ViewHolder{
        private ImageView sourceLogoImageView;
        private TextView  sourceTitleTextView;
        

        public SourceHolder(View itemView) {
            super(itemView);
            sourceLogoImageView = (ImageView)itemView.findViewById(R.id.source_logo_image_item);
            sourceTitleTextView = (TextView) itemView.findViewById(R.id.source_title_textView);
        }
    }

}
