package com.destro13.reutersnews.mvp.presenter;

import com.destro13.reutersnews.mvp.model.NewsModel;
import com.destro13.reutersnews.mvp.model.NewsModelImpl;
import com.destro13.reutersnews.mvp.view.NewsView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by pavlyknazariy on 06.06.17.
 */

public class NewsPresenterImpl implements NewsPresenter {
    private NewsModel mNBUModel = new NewsModelImpl();

    private Subscription subscription = Subscriptions.empty();

    private NewsView view;


    public NewsPresenterImpl() {
    }

    @Override
    public void getNews() {

    }
}
