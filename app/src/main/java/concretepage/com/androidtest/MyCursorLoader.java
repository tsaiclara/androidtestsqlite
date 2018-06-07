package concretepage.com.androidtest;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jiahui on 2018/5/26.
 */


public class MyCursorLoader extends CursorLoader {
    private SQLiteDatabase db;

    public MyCursorLoader(Context context,SQLiteDatabase db) {
        super(context);
        this.db=db;
    }

    @Override
    protected Cursor onLoadInBackground() {
        // _id 0,drama_id 1,name 2 ,total_views 3  created_at 4, thumb 5, rating 6
        Cursor cursor= db.rawQuery("SELECT _id,drama_id,name,total_views,created_at,thumb,rating FROM video_list",null);
        return cursor;
    }
}
