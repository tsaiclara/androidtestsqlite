package concretepage.com.androidtest;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemClickListener {
    //使用db
    private static int LOADER_ID=1;
    private SQLiteDatabase mDB;
    SQLiteOpenHelper dbHelper =null;
    ResourceCursorAdapter mAdapter;
    TextView textview;
    EditText editText;
    Button btn;
    ListView mvideolist;
    // 只建立一次
    ArrayList<VideoInformation> arraylistvideo = new ArrayList<>();
    MyCursorLoader loader =null;

    //Myadpter myadpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview=(TextView)findViewById(R.id.textView);
        editText=(EditText)findViewById(R.id.editText);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(mOnClickListener1);
        mvideolist=(ListView)findViewById(R.id.videolist);
        mAdapter= new MyResourceCursorAdapter(this,R.layout.myitem,null);
        mvideolist.setAdapter(mAdapter);
        mvideolist.setOnItemClickListener(this);
       dbHelper = new DBHelper(this);
        //取得寫進db的物件
        mDB=dbHelper.getWritableDatabase();
        //上網下載資料 格是是json
       new TransTask().execute("http://www.mocky.io/v2/5a97c59c30000047005c1ed2");
        Log.d("onCreate","oncreate---");
        // 執行 onCreateLoader 、onLoadFinished
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }
    // 1 製作 載入器  要寫編號 要跟管理者說
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        MyCursorLoader loader = new MyCursorLoader(this,mDB);
        Log.d("TAG","onCreateLoader完成");
        //告訴 載入器管理者 getLoaderManager
        return loader;
    }
    // 2 從 db 裡 table 裡資料寫到 listiview 上
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // 換掉內容
        mAdapter.swapCursor(data);
        //通知內容已改變
        mAdapter.notifyDataSetChanged();
        Log.d("TAG","onLoadFinished完成");

    }
    // 3 LISTVIEW 內容改變時 更新畫面
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        Log.d("TAG","onLoaderReset完成");

    }
   //按下按鈕 查詢要的影片
    private View.OnClickListener mOnClickListener1 = new View.OnClickListener(){
        public void onClick(View v){
            String edittext=editText.getText().toString();
            //String sql= "select * from video_list where name LIKE %"+edittext+"%";
            //btn.setText("textview1");
            //Log.d("textview1",sql);
            //獅子王強大
            Cursor cursor= mDB.rawQuery("SELECT _id,drama_id,name,total_views,created_at,thumb,rating FROM video_list WHERE name LIKE '%"+edittext+"%'",null);
            if(cursor.getCount()>0)
            {

               mAdapter.swapCursor(cursor);
                mAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast toast = Toast.makeText(MainActivity.this,
                        "查無此影片名!", Toast.LENGTH_LONG);
                //顯示Toast
                toast.show();
            }

        }
    };


    // _id 0,drama_id 1,name 2 ,total_views 3  created_at 4, thumb 5, rating 6
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //***** 裝資料的 mAdapter 取出對應點到的位置資料
        Cursor cursor=(Cursor)mAdapter.getItem(position);
        //取出點的這筆資料 table 的值 欄位 0 開始
        String picurl=cursor.getString(5);
        String name=cursor.getString(2);
        Double rating=cursor.getDouble(6);
        String created_at=cursor.getString(4);
        String total_views=cursor.getString(3);
        Bundle bundle= new Bundle();
        bundle.putString("picurl",picurl);
        bundle.putString("name",name);
        bundle.putDouble("rating",rating);
        bundle.putString("created_at",created_at);
        bundle.putString("total_views",total_views);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,ItemActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }

    //上網抓取資料回來本地端
    class TransTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {

                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while(line!=null){
                    Log.d("HTTP", line);
                    sb.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }
        private void parseJSON(String s) {

            arraylistvideo.clear();
            try {
                //*** //data  取得json物件
                JSONObject jObject = new JSONObject(s);
                JSONArray jArray = jObject.getJSONArray("data");
                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        int drama_id=oneObject.getInt("drama_id");
                        String name = oneObject.getString("name");
                        int total_views=oneObject.getInt("total_views");
                        String created_at=oneObject.getString("created_at");
                        String thumb = oneObject.getString("thumb");
                        double rating=oneObject.getDouble("rating");
                        VideoInformation videoInformation=new VideoInformation(drama_id,name,total_views,created_at,thumb,rating);
                        arraylistvideo.add(videoInformation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i=0; i<arraylistvideo.size();i++)
            {
                //int drama_id=arraylistvideo.get(i).drama_id;
                int drama_id=arraylistvideo.get(i).drama_id;
                String name=arraylistvideo.get(i).name;
               // int total_views=arraylistvideo.get(i).total_views;
                int total_views=arraylistvideo.get(i).total_views;
                String created_at=arraylistvideo.get(i).created_at;
                String thumb=arraylistvideo.get(i).thumb;
                //int rating=arraylistvideo.get(i).rating;
                double rating=arraylistvideo.get(i).rating;

                String sql=
                        String.format("INSERT  OR ignore INTO video_list (drama_id,name,total_views,created_at,thumb,rating) VALUES(%d,'%s',%d,'%s','%s',%f)",drama_id,name,total_views,created_at,thumb,rating);
                Log.d("TAG",sql);
                //寫進db了
                mDB.execSQL(sql);

                getLoaderManager().restartLoader(LOADER_ID,null,MainActivity.this);

            }
        }

        }
    }











