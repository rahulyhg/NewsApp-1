package com.destro13.reutersnews;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.destro13.reutersnews.adapter.NewsAdapter;
import com.destro13.reutersnews.apinews.ApiController;
import com.destro13.reutersnews.apinews.NewsService;
import com.destro13.reutersnews.model.Article;
import com.destro13.reutersnews.model.NewsReport;
import com.destro13.reutersnews.util.StringParser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    static final String API_KEY = "f2df02200e4e4a8d9f1ee75a00cd79fd";
    private NewsService mNewsService;
    private NewsReport mNewsReport;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerSectionItemDecoration mRecyclerSectionItemDecoration;

    private NewsAdapter mNewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.reuters_news_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mNewsService = ApiController.getApi();

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    final List<Article> tempArticles = mNewsReport.getArticles();
                    mNewsReport.setArticles(tempArticles);

                    mNewsService.getData("reuters","top",API_KEY).enqueue(new Callback<NewsReport>() {
                        @Override
                        public void onResponse(Call<NewsReport> call, Response<NewsReport> response) {
                            tempArticles.addAll(setParsedDate(response.body()).getArticles());
                            mNewsAdapter.setData(tempArticles);
                        }

                        @Override
                        public void onFailure(Call<NewsReport> call, Throwable t) {

                        }
                    });
                }
            }
        });

        mNewsService.getData("reuters","latest", API_KEY).enqueue(new Callback<NewsReport>() {
            @Override
            public void onResponse(Call<NewsReport> call, Response<NewsReport> response) {
                mNewsReport = setParsedDate(response.body());
                mNewsReport.setArticles(mNewsReport.getArticles());

                mNewsAdapter = new NewsAdapter(mNewsReport.getArticles(),NewsActivity.this);
                mRecyclerView.setAdapter(mNewsAdapter);

                RecyclerSectionItemDecoration mRecyclerSectionItemDecoration =
                        new RecyclerSectionItemDecoration(
                                getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                                true,
                                getSectionCallback(mNewsReport.getArticles()));
                mRecyclerView.addItemDecoration(mRecyclerSectionItemDecoration);
            }

            @Override
            public void onFailure(Call<NewsReport> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private NewsReport setParsedDate(NewsReport newsReport){
        for (Article article : newsReport.getArticles()) {
            article.setPublishedAt(StringParser.setDateInFormat(StringParser.parseDate(article.getPublishedAt())));
        }
        return newsReport;
    }

    @Override
    public void onRefresh() {
        mNewsService.getData("reuters","latest", API_KEY).enqueue(new Callback<NewsReport>() {
            @Override
            public void onResponse(Call<NewsReport> call, Response<NewsReport> response) {
                mNewsReport = setParsedDate(response.body());
                mNewsAdapter.setData(mNewsReport.getArticles());

                mRecyclerView.removeItemDecoration(mRecyclerSectionItemDecoration);
                mRecyclerSectionItemDecoration =
                        new RecyclerSectionItemDecoration(
                                getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                                true,
                                getSectionCallback(mNewsReport.getArticles()));
                mRecyclerView.addItemDecoration(mRecyclerSectionItemDecoration);

            }

            @Override
            public void onFailure(Call<NewsReport> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network error!", Toast.LENGTH_SHORT);
            }
        });

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


        //FOR TESTING
    private List<Article> setFakeObjects(List<Article> articles){
        articles.get(2).setPublishedAt("Thursday, June 1");
        articles.get(3).setPublishedAt("Friday, June 2");
        articles.get(4).setPublishedAt("Friday, June 2");
        articles.get(5).setPublishedAt("Friday, June 2");
        articles.get(6).setPublishedAt("Friday, June 2");
        articles.get(7).setPublishedAt("Friday, June 2");
        articles.get(8).setPublishedAt("Friday, June 2");
        articles.get(9).setPublishedAt("Friday, June 2");
        return mNewsReport.getArticles();
    }
}
