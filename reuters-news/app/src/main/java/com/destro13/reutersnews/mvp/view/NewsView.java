package com.destro13.reutersnews.mvp.view;

import com.destro13.reutersnews.mvp.model.NewsReport;

/**
 * Created by pavlyknazariy on 06.06.17.
 */

public interface NewsView extends View {
    void setNews(NewsReport newsReport);
}
