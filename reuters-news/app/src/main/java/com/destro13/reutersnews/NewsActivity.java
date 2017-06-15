package com.destro13.reutersnews;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.destro13.reutersnews.adapter.NewsAdapter;
import com.destro13.reutersnews.mvp.model.Article;
import com.destro13.reutersnews.mvp.model.NewsReport;
import com.destro13.reutersnews.mvp.presenter.NewsPresenter;
import com.destro13.reutersnews.mvp.presenter.NewsPresenterImpl;
import com.destro13.reutersnews.mvp.view.NewsView;
import com.destro13.reutersnews.recyclerdecoration.RecyclerSectionItemDecoration;
import com.destro13.reutersnews.util.StringParser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements NewsView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.reuters_news_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerSectionItemDecoration mRecyclerSectionItemDecoration;
    private NewsPresenter mNewsPresenter;
    private NewsAdapter mNewsAdapter;
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mNewsPresenter = new NewsPresenterImpl(this);
        mSource = getIntent().getStringExtra("source");
        mNewsPresenter.getNews(mSource);
    }

    @Override
    public void onRefresh() {
        mNewsPresenter.getNews(mSource);
        mSwipeRefreshLayout.setRefreshing(true);

        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mNewsAdapter = new NewsAdapter(NewsActivity.this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    private void setDecoration(NewsReport newsReport) {
        mRecyclerSectionItemDecoration =
                new RecyclerSectionItemDecoration(
                        getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                        true,
                        getSectionCallback(newsReport.getArticles()));
        mRecyclerView.addItemDecoration(mRecyclerSectionItemDecoration);
    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<Article> articles) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0 || !(articles.get(position).getPublishedAt().equals(articles.get(position - 1).getPublishedAt()));
            }

            @Override
            public CharSequence getSectionHeader(int position) {
                return articles.get(position).getPublishedAt();
            }
        };
    }

    @Override
    public void setNews(NewsReport newsReport) {
        mNewsAdapter.setData(StringParser.setParsedDate(newsReport).getArticles());
        setDecoration(newsReport);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewsPresenter.unsubscribeCurrentSubscription();
    }
}
