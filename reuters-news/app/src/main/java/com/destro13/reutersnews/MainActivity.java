package com.destro13.reutersnews;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.destro13.reutersnews.adapter.NewsAdapter;
import com.destro13.reutersnews.apireuters.ApiController;
import com.destro13.reutersnews.apireuters.NewsService;
import com.destro13.reutersnews.model.Article;
import com.destro13.reutersnews.model.NewsReport;
import com.destro13.reutersnews.util.StringParser;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    static final String API_KEY = "f2df02200e4e4a8d9f1ee75a00cd79fd";
    private NewsService mNewsService;
    private NewsReport mNewsReport;
    private RecyclerView mRecyclerView;
    private TextView mTopStoryTextView;
    private TextView mDateTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageButton mTopStoryImageButton;

    private NewsAdapter mNewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTopStoryImageButton = (ImageButton) findViewById(R.id.top_story_image_button);
//        mTopStoryImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRecyclerView.scrollToPosition(0);
//            }
//        });

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
                mNewsReport.setArticles(setFakeObjects(mNewsReport.getArticles()));

                mNewsAdapter = new NewsAdapter(mNewsReport.getArticles(),MainActivity.this);
                mRecyclerView.setAdapter(mNewsAdapter);

                final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mNewsAdapter);
                mRecyclerView.addItemDecoration(headersDecor);
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


    private void setDate(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateTextView.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()) + ", ");
        mDateTextView.append(new SimpleDateFormat("MMMM", Locale.ENGLISH).format(date.getTime()) + " ");
        mDateTextView.append(Integer.toString(day));
    }

    @Override
    public void onRefresh() {
        mNewsService.getData("reuters","latest", API_KEY).enqueue(new Callback<NewsReport>() {
            @Override
            public void onResponse(Call<NewsReport> call, Response<NewsReport> response) {
                mNewsReport = setParsedDate(response.body());
                mNewsAdapter.setData(mNewsReport.getArticles());
            }

            @Override
            public void onFailure(Call<NewsReport> call, Throwable t) {
                t.printStackTrace();
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
