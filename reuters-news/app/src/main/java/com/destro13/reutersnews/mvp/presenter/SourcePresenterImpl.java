package com.destro13.reutersnews.mvp.presenter;

import com.destro13.reutersnews.model.SourceReport;
import com.destro13.reutersnews.mvp.model.SourceModel;
import com.destro13.reutersnews.mvp.model.SourceModelImpl;
import com.destro13.reutersnews.mvp.view.SourceView;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by pavlyknazariy on 07.06.17.
 */

public class SourcePresenterImpl implements SourcePresenter {
    private SourceModel mSourceModel = new SourceModelImpl();

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

        subscription = mSourceModel.getSource()
                .subscribe(new Observer<SourceReport>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SourceReport sourceReport) {
                        mSourceReport = sourceReport;
                        mSourceView.showSources(sourceReport);
                    }
                });
    }
}
