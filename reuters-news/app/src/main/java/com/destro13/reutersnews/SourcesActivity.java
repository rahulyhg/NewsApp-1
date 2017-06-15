package com.destro13.reutersnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.destro13.reutersnews.adapter.SourceAdapter;
import com.destro13.reutersnews.mvp.model.SourceReport;
import com.destro13.reutersnews.mvp.presenter.SourcePresenter;
import com.destro13.reutersnews.mvp.presenter.SourcePresenterImpl;
import com.destro13.reutersnews.mvp.view.SourceView;

public class SourcesActivity extends AppCompatActivity implements SourceView {
    private RecyclerView mSourcesRecyclerView;
    private SourceAdapter mSourceAdapter;
    private SourcePresenter mSourcePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);
        initRecyclerView();
        mSourcePresenter = new SourcePresenterImpl(this);
        mSourcePresenter.getSources();
    }

    @Override
    public void showSources(SourceReport sourceReport) {
        mSourceAdapter.setData(sourceReport.getSources());
    }

    private void initRecyclerView(){
        mSourcesRecyclerView = (RecyclerView) findViewById(R.id.reuters_news_recycler_view);
        mSourcesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mSourceAdapter = new SourceAdapter(this);
        mSourcesRecyclerView.setAdapter(mSourceAdapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSourcePresenter.unsubscribeCurrentSubscription();
    }
}
