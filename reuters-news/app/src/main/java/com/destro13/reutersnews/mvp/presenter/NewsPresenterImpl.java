package com.destro13.reutersnews.mvp.presenter;

import com.destro13.reutersnews.model.Article;
import com.destro13.reutersnews.model.NewsReport;
import com.destro13.reutersnews.mvp.model.NewsModel;
import com.destro13.reutersnews.mvp.model.NewsModelImpl;
import com.destro13.reutersnews.mvp.view.NewsView;

import java.util.Collection;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by pavlyknazariy on 06.06.17.
 */

public class NewsPresenterImpl implements NewsPresenter {
    static final String API_KEY = "f2df02200e4e4a8d9f1ee75a00cd79fd";

    private NewsModel mNewsModel = new NewsModelImpl();

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

        subscription = mNewsModel.getData(source,"top", API_KEY)
                .subscribe(new Observer<NewsReport>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NewsReport newsReport) {
                        mNewsReport = newsReport;
                        mNewsView.setNews(newsReport);
                    }
                });

    }

    @Override
    public void getAdditionalNews() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        NewsModel newsModel = new NewsModelImpl();

        subscription = newsModel.getData("reuters","latest", API_KEY)
                .subscribe(new Observer<NewsReport>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NewsReport newsReport) {
                        List<Article> tempArticles = mNewsReport.getArticles();
                        tempArticles.addAll((Collection<? extends Article>) newsReport);
                        mNewsReport.setArticles(tempArticles);
                        mNewsView.setNews(newsReport);

                    }
                });
    }
}
