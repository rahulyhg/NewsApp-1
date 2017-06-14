package com.destro13.reutersnews.mvp.model;

import com.destro13.reutersnews.apinews.ApiController;
import com.destro13.reutersnews.apinews.NewsService;
import com.destro13.reutersnews.model.NewsReport;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pavlyknazariy on 06.06.17.
 */

public class NewsModelImpl implements NewsModel {
    NewsService mNewsService = ApiController.getApi();

    @Override
    public Observable<NewsReport> getData(String source, String sortBy, String apiKey) {
          return    mNewsService.getData(source, sortBy, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
