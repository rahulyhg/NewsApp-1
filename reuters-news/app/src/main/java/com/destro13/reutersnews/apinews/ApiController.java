package com.destro13.reutersnews.apinews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pavlyknazariy on 25.05.17.
 */

public class ApiController {
    static final String BASE_URL = "https://newsapi.org";

    public static NewsService getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NewsService nbuService = retrofit.create(NewsService.class);
        return nbuService;

    }
}
