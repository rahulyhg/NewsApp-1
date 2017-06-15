package com.destro13.reutersnews.mvp.presenter;

import com.destro13.reutersnews.apinews.ApiController;
import com.destro13.reutersnews.mvp.model.SourceReport;
import com.destro13.reutersnews.mvp.view.SourceView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by pavlyknazariy on 07.06.17.
 */

public class SourcePresenterImpl implements SourcePresenter {
    private SourceReport mSourceReport;
    private Subscription subscription = Subscriptions.empty();
    private SourceView mSourceView;

    public SourcePresenterImpl(SourceView sourceView) {
        mSourceView = sourceView;
    }

    @Override
    public void getSources() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ApiController
                .getApi()
                .getSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SourceReport>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSourceView.showError("Error! Network error!");
                    }

                    @Override
                    public void onNext(SourceReport sourceReport) {
                        mSourceReport = sourceReport;
                        mSourceView.showSources(sourceReport);
                    }
                });
    }
}
