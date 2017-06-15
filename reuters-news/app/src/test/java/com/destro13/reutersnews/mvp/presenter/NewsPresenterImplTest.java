package com.destro13.reutersnews.mvp.presenter;

import com.destro13.reutersnews.BuildConfig;
import com.destro13.reutersnews.mvp.model.NewsReport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Observer;

import static org.junit.Assert.assertTrue;
/**
 * Created by pavlyknazariy on 13.06.17.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class NewsPresenterImplTest {
    @Before
    public void setup() throws Exception{
    }

    @Test
    public void checkGetNews() throws Exception{
//        mNewsModel.getData("reuters", "top", "f2df02200e4e4a8d9f1ee75a00cd79fd").subscribe(new Observer<NewsReport>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(NewsReport newsReport) {
//                assertTrue(!newsReport.getArticles().isEmpty());
//            }
//        });
    }
}
