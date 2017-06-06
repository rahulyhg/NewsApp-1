package com.destro13.reutersnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.destro13.reutersnews.adapter.NewsAdapter;
import com.destro13.reutersnews.adapter.SourceAdapter;
import com.destro13.reutersnews.apinews.ApiController;
import com.destro13.reutersnews.apinews.NewsService;
import com.destro13.reutersnews.model.SourceReport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesActivity extends AppCompatActivity {
    private RecyclerView mSourcesRecyclerView;
    private NewsService mNewsService;
    private SourceReport mSourceReport;
    private SourceAdapter mSourceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        NewsService mNewsService  = ApiController.getApi();

        mSourcesRecyclerView = (RecyclerView) findViewById(R.id.reuters_news_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mSourcesRecyclerView.setLayoutManager(llm);


        mNewsService.getSources().enqueue(new Callback<SourceReport>() {
            @Override
            public void onResponse(Call<SourceReport> call, Response<SourceReport> response) {
                mSourceReport = new SourceReport();
                mSourceAdapter = new SourceAdapter(mSourceReport.getSources());
                mSourcesRecyclerView.setAdapter(mSourceAdapter);
            }

            @Override
            public void onFailure(Call<SourceReport> call, Throwable t) {

            }
        });
    }
}
