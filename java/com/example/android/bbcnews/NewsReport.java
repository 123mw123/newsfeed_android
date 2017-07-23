package com.example.android.bbcnews;

/**
 * Created  by Sai Teja on 2/25/2017.
 */

public class NewsReport {
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private String mUrlToImage;
    private String mDate;
public  NewsReport(String Title,String Description,String Url,String Date,String UrlToImage){
mTitle=Title;
    mDescription=Description;
    mUrl=Url;
    mDate=Date;
    mUrlToImage=UrlToImage;
}
    public String getTitle(){
        return mTitle;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getUrl(){
        return mUrl;
    }
    public String getDate(){
        return mDate;
    }
    public String getUrlToImage(){
        return mUrlToImage;
    }
}
