package com.destro13.reutersnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.destro13.reutersnews.adapter.NewsAdapter;
import com.destro13.reutersnews.model.Article;
import com.destro13.reutersnews.model.NewsReport;
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

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mNewsPresenter = new NewsPresenterImpl(this);

        Intent intent = getIntent();
        mSource = intent.getStringExtra("source");
 

        //FAIL: Entertaiment, Financial Times, Fortune,
        //      Handelsblatt, The Next Web,
        // LATEST: DerTegesspiel, DieZeit, Wirtschafts Woche
        //LOADED WITH ERROR: HeckerNews, NewsWeek, Reddit, WallStreetJournal,
        mNewsPresenter.getNews(mSource);

        //TO DO: replace deprecated
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(dy > 0){
//                    mNewsPresenter.getAdditionalNews();
//                }
//            }
//        });

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


        //--FOR TESTING
//    private List<Article> setFakeObjects(List<Article> articles){
////        articles.get(2).setPublishedAt("Thursday, June 1");
////        articles.get(3).setPublishedAt("Friday, June 2");
////        articles.get(4).setPublishedAt("Friday, June 2");
////        articles.get(5).setPublishedAt("Friday, June 2");
////        articles.get(6).setPublishedAt("Friday, June 2");
////        articles.get(7).setPublishedAt("Friday, June 2");
////        articles.get(8).setPublishedAt("Friday, June 2");
////        articles.get(9).setPublishedAt("Friday, June 2");
////        return mNewsReport.getArticles();
//    }
    //--FOR TESTING

    @Override
    public void setNews(NewsReport newsReport) {
                mNewsAdapter = new NewsAdapter(StringParser.setParsedDate(newsReport).getArticles(),NewsActivity.this);
                mRecyclerView.setAdapter(mNewsAdapter);

                RecyclerSectionItemDecoration mRecyclerSectionItemDecoration =
                        new RecyclerSectionItemDecoration(
                                getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                                true,
                                getSectionCallback(newsReport.getArticles()));
                mRecyclerView.addItemDecoration(mRecyclerSectionItemDecoration);
    }

    @Override
    public void addNews(NewsReport newsReport) {
        mNewsAdapter.setData(newsReport.getArticles());

        mRecyclerView.removeItemDecoration(mRecyclerSectionItemDecoration);
        RecyclerSectionItemDecoration mRecyclerSectionItemDecoration =
                new RecyclerSectionItemDecoration(
                        getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                        true,
                        getSectionCallback(newsReport.getArticles()));
        mRecyclerView.addItemDecoration(mRecyclerSectionItemDecoration);
    }
}
