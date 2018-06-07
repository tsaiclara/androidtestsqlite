package concretepage.com.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Jiahui on 2018/5/28.
 */

public class ItemActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_item);
        ImageView tv_mpic=(ImageView)findViewById(R.id.thumb);
        TextView tv_name=(TextView)findViewById(R.id.name);
        TextView tv_rating=(TextView)findViewById(R.id.rating);
        TextView tv_created_at=(TextView)findViewById(R.id.created_at);
        TextView tv_total_views=(TextView)findViewById(R.id.total_views);
        //處理圖片
        //取值方法
        Bundle bundle = getIntent().getExtras();
        String picurl = bundle.getString("picurl");
        String name = bundle.getString("name");
        Double rating=bundle.getDouble("rating");
        String created_at=bundle.getString("created_at");
        String total_views=bundle.getString("total_views");

        Glide.with(ItemActivity.this).load(picurl).into(tv_mpic);
        tv_name.setText("名稱  "+name);
        tv_rating.setText("評分  "+String.valueOf(rating));
        tv_created_at.setText("出版日期  "+created_at);
        tv_total_views.setText("觀看次數  "+total_views);

    }


}
