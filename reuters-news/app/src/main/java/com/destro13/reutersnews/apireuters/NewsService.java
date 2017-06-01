package com.destro13.reutersnews.apireuters;

import com.destro13.reutersnews.model.NewsReport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pavlyknazariy on 25.05.17.
 */

public interface NewsService {
    @GET("/v1/articles")
    Call<NewsReport> getData(@Query("source") String source, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);
}
