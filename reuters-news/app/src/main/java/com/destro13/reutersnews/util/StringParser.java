package com.destro13.reutersnews.util;

import com.destro13.reutersnews.model.Article;
import com.destro13.reutersnews.model.NewsReport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pavlyknazariy on 30.05.17.
 */

public final class StringParser {
    private StringParser(){
        throw new AssertionError();
    }


    public static String parseDate(String in){
        String out = in.substring(0,10);
        return out;
    }


    public static String setDateInFormat(String strDate){
        DateFormat inFormat = new SimpleDateFormat("yyyy-LL-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = inFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DateFormat outFormat = new SimpleDateFormat("EEEE, MMMM d", Locale.ENGLISH);

        String outString = outFormat.format(date.getTime());

        return outString;
    }

    public static NewsReport setParsedDate(NewsReport newsReport){
        for (Article article : newsReport.getArticles()) {
            article.setPublishedAt(StringParser.setDateInFormat(StringParser.parseDate(article.getPublishedAt())));
        }
        return newsReport;
    }

    public static String transformSourceId(String id){
        return id.replace("-","_");
    }


    public static String transformUrlToImage(String url){
        String head = url.substring(0,4);
        if(!head.equals("http"))
            return "http:" + url;
        else
            return url;
    }


}
