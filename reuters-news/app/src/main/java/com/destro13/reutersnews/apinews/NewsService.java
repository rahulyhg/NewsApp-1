package com.destro13.reutersnews.apinews;

import com.destro13.reutersnews.model.NewsReport;
import com.destro13.reutersnews.model.Source;
import com.destro13.reutersnews.model.SourceReport;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pavlyknazariy on 25.05.17.
 */

public interface NewsService {
    @GET("/v1/articles")
    Observable<NewsReport> getData(@Query("source") String source, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);
    @GET("/v1/sourses")
    Call<SourceReport> getSources();
}
