package com.destro13.reutersnews.mvp.model;

import com.destro13.reutersnews.model.NewsReport;

import java.util.List;

import rx.Observable;

/**
 * Created by pavlyknazariy on 06.06.17.
 */

public interface NewsModel extends Model{
    Observable<NewsReport> getData(String source, String sortBy, String apiKey);
}
