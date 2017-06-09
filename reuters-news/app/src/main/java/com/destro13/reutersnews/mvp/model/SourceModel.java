package com.destro13.reutersnews.mvp.model;

import com.destro13.reutersnews.model.NewsReport;
import com.destro13.reutersnews.model.Source;
import com.destro13.reutersnews.model.SourceReport;

import rx.Observable;

/**
 * Created by pavlyknazariy on 07.06.17.
 */

public interface SourceModel extends Model {
    Observable<SourceReport> getSource();
}
