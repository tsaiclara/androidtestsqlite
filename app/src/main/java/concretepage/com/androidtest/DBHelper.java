package concretepage.com.androidtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

/**
 * Created by Jiahui on 2018/5/21.
 */

//drama_id: 1,
//        name: "致我們單純的小美好",
//        total_views: 23562274,
//        created_at: "2017-11-23T02:04:39.000Z",
//        thumb: "https://i.pinimg.com/originals/61/d4/be/61d4be8bfc29ab2b6d5cab02f72e8e3b.jpg",
//        rating: 4.4526


// 建立db 建立 Table
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="video.db";
    public static final int DATABASE_VERSION=1;
    // _id 0,drama_id 1,name 2 ,total_views 3  created_at 4, thumb 5, rating 6

    public static final String CREATE_TABLE_SQL=
            "CREATE TABLE video_list (_id INTEGER PRIMARY KEY,drama_id NUMERIC,name TEXT ,total_views NUMERIC"+
                    ",created_at TEXT,thumb TEXT,rating NUMERIC,UNIQUE(name))";

   // 建立這db
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //建db 建table
    // 系統會自動執行它 它就是建立db 、table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
