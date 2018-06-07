package concretepage.com.androidtest;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Jiahui on 2018/5/28.
 */

public class MyResourceCursorAdapter extends ResourceCursorAdapter {
    Context context;

    public MyResourceCursorAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, 0);
        this.context=context;
    }
    // 呈現
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //圖片
        ImageView  tv_imageView=(ImageView)view.findViewById(R.id.thumb);
        //名稱
        TextView tv_name=(TextView)view.findViewById(R.id.name);
        //日期
        TextView tv_created_at=(TextView)view.findViewById(R.id.created_at);
        //評分
        TextView tv_rating=(TextView)view.findViewById(R.id.rating);

        // 在db 內 欄位 0
        //tv_imageView.setBackgroundResource(cursor.getInt(5));
        String pic=cursor.getString(5);
        //處理圖片
        Glide.with(context).load(pic).into(tv_imageView);
        //Log.d("圖片URI-->",""+pic);
       //名 2
        tv_name.setText("名稱  "+cursor.getString(2));
        //日期 4
        tv_created_at.setText("出版日期  "+cursor.getString(4));
        //評分 6
        tv_rating.setText("評分  "+String.valueOf(cursor.getDouble(6)));
    }
}
