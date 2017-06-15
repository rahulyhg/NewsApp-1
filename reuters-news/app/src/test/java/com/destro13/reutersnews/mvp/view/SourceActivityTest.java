package com.destro13.reutersnews.mvp.view;

import com.destro13.reutersnews.BuildConfig;
import com.destro13.reutersnews.SourcesActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
/**
 * Created by pavlyknazariy on 13.06.17.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SourceActivityTest {
    SourcesActivity mSourcesActivity;

    @Before
    public void setup() throws Exception{
        mSourcesActivity = Robolectric.buildActivity( SourcesActivity.class )
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull(mSourcesActivity);
    }

}
