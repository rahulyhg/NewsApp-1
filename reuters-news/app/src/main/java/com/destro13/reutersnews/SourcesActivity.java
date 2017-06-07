package com.destro13.reutersnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.destro13.reutersnews.adapter.SourceAdapter;
import com.destro13.reutersnews.apinews.NewsService;
import com.destro13.reutersnews.model.SourceReport;
import com.destro13.reutersnews.mvp.presenter.SourcePresenter;
import com.destro13.reutersnews.mvp.presenter.SourcePresenterImpl;
import com.destro13.reutersnews.mvp.view.SourceView;

public class SourcesActivity extends AppCompatActivity implements SourceView {
    private RecyclerView mSourcesRecyclerView;
    private NewsService mNewsService;
    private SourceReport mSourceReport;
    private SourceAdapter mSourceAdapter;
    private SourcePresenter mSourcePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

      //  NewsService mNewsService  = ApiController.getApi();

        mSourcesRecyclerView = (RecyclerView) findViewById(R.id.reuters_news_recycler_view);
        mSourcesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mSourcePresenter = new SourcePresenterImpl(this);
        mSourcePresenter.getSources();


//        mNewsService.getSources().enqueue(new Callback<SourceReport>() {
//            @Override
//            public void onResponse(Call<SourceReport> call, Response<SourceReport> response) {
//                mSourceReport = new SourceReport();
//                mSourceAdapter = new SourceAdapter(mSourceReport.getSources());
//                mSourcesRecyclerView.setAdapter(mSourceAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<SourceReport> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void showSources(SourceReport sourceReport) {
        mSourceAdapter = new SourceAdapter(sourceReport.getSources(), this);
        mSourcesRecyclerView.setAdapter(mSourceAdapter);
    }
}
