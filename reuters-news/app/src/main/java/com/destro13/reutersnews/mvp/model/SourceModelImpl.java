package com.destro13.reutersnews.mvp.model;

import com.destro13.reutersnews.apinews.ApiController;
import com.destro13.reutersnews.apinews.NewsService;
import com.destro13.reutersnews.model.SourceReport;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pavlyknazariy on 07.06.17.
 */

public class SourceModelImpl implements SourceModel {
    NewsService mNewsService = ApiController.getApi();

    @Override
    public Observable<SourceReport> getSource() {
        return    mNewsService.getSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
