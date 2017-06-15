package com.destro13.reutersnews.mvp.view;

import com.destro13.reutersnews.mvp.model.SourceReport;

/**
 * Created by pavlyknazariy on 07.06.17.
 */

public interface SourceView extends View{
    void showSources(SourceReport sourceReport);
}
