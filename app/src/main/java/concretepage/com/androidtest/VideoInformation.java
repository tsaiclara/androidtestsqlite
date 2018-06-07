package concretepage.com.androidtest;

import android.graphics.Bitmap;

/**
 * Created by Jiahui on 2018/5/21.
 */

/*
drama_id: 1,
name: "致我們單純的小美好",
total_views: 23562274,
created_at: "2017-11-23T02:04:39.000Z",
thumb: "https://i.pinimg.com/originals/61/d4/be/61d4be8bfc29ab2b6d5cab02f72e8e3b.jpg",
rating: 4.4526
*/
public class VideoInformation {
    public int drama_id;
    public String name;
    public int total_views;
    public String created_at;
    public String thumb;
    public double rating;
    public Bitmap bitmap;

    public VideoInformation(int drama_id,String name,int total_views,String created_at,String thumb,double rating)
    {
        this.drama_id=drama_id;
        this.name=name;
        this.total_views=total_views;
        this.created_at=created_at;
        this.thumb=thumb;
        this.rating=rating;
    }
}
