package com.destro13.reutersnews.mvp.presenter;

import com.destro13.reutersnews.apinews.ApiController;
import com.destro13.reutersnews.mvp.model.NewsReport;
import com.destro13.reutersnews.mvp.view.NewsView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by pavlyknazariy on 06.06.17.
 */

public class NewsPresenterImpl implements NewsPresenter {
    static final String API_KEY = "f2df02200e4e4a8d9f1ee75a00cd79fd";
    private NewsReport mNewsReport;
    private String mSource;
    private Subscription subscription = Subscriptions.empty();
    private NewsView mNewsView;

    public NewsPresenterImpl(NewsView view) {
        this.mNewsView = view;
    }

    @Override
    public void getNews(String source) {
        mSource = source;

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ApiController
                        .getApi()
                        .getData(source, "top", API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<NewsReport>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mNewsView.showError("Error! Bad request!");
                            }

                            @Override
                            public void onNext(NewsReport newsReport) {
                                mNewsReport = newsReport;
                                mNewsView.setNews(newsReport);
                            }
                        });
    }

    @Override
    public void unsubscribeCurrentSubscription() {
        subscription.unsubscribe();
    }
}
