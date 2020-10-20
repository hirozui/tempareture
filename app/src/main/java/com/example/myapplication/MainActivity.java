package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{
    public static HashMap<String,Float> rate = new HashMap<>();
    public static MainActivity ma;
    private Timer timer;

    Handler handler = new Handler(){
      @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg){
          if(msg.what==5){
              String str = (String) msg.obj;
              //Log.i("TAG", str);
              Pattern pattern = Pattern.compile(".*美元(.*?</td>){5}.*?<td>(.*?)</td>.*");//(\\D*)(\\d+)(.*)");
              Matcher matcher = pattern.matcher(str);
              if (matcher.matches())
                  rate.put("dollar", Float.parseFloat(matcher.group(2))/100);
              pattern = Pattern.compile(".*欧元(.*?</td>){5}.*?<td>(.*?)</td>.*");//(\\D*)(\\d+)(.*)");
              matcher = pattern.matcher(str);
              if (matcher.matches())
                  rate.put("euro", Float.parseFloat(matcher.group(2))/100);
              pattern = Pattern.compile(".*韩元(.*?</td>){5}.*?<td>(.*?)</td>.*");//(\\D*)(\\d+)(.*)");
              matcher = pattern.matcher(str);
              if (matcher.matches())
                  rate.put("won", Float.parseFloat(matcher.group(2))/100);
          }
          super.handleMessage(msg);
      }
    };

    public void getrate(View view){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {//开启定时器
            @Override
            public void run() {
                Thread t = new Thread(new Runnable() {

                    InputStream is = null;
                    BufferedReader br = null;
                    HttpURLConnection http = null;

                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://www.usd-cny.com/bankofchina.htm");
                            http = (HttpURLConnection) url.openConnection();
                            is = http.getInputStream();
                            br = new BufferedReader(new InputStreamReader(is, "gb2312"));
                            StringBuffer sb = new StringBuffer();
                            String rl = br.readLine();
                            while (rl != null) {
                                sb.append(rl);
                                rl = br.readLine();
                            }
                            handler.sendMessage(handler.obtainMessage(5, new String(sb.toString().getBytes("UTF-8"),"UTF-8")));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (br != null) br.close();
                                if (is != null) is.close();
                                if (http != null) http.disconnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                t.start();
            }
        };
        timer.schedule(task,0,86400000);//设置执行周期
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_main,null);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ma = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        rate.put("dollar",sp.getFloat("dollar", 0.1477f));
        rate.put("euro",sp.getFloat("euro", 0.1256f));
        rate.put("won",sp.getFloat("won", 171.3421f));


    }

    public void open(View v){
        Intent config = new Intent(this,MainActivity2.class);
        config.putExtra("dollar",rate.get("dollar"));
        config.putExtra("euro",rate.get("euro"));
        config.putExtra("won",rate.get("won"));
        startActivityForResult(config,1);
    }

    public void change(View view){
        EditText et = findViewById(R.id.RMB);
        Pattern pattern = Pattern.compile("[0-9]+\\.?[0-9]*");
        Matcher matcher = pattern.matcher(et.getText());
        if(matcher.matches()){
            et.setText(String.valueOf(Float.parseFloat(matcher.group())*rate.get(view.getTag().toString())));
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == 2){
            rate.put("dollar",data.getFloatExtra("dollar",0f));
            rate.put("euro",data.getFloatExtra("euro",0f));
            rate.put("won",data.getFloatExtra("won",0f));
            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putFloat("dollar", data.getFloatExtra("dollar", 0f));
            ed.putFloat("euro", data.getFloatExtra("euro", 0f));
            ed.putFloat("won", data.getFloatExtra("won", 0f));
            ed.apply();
        }

    }



    /*@Override
    public boolean onCreateMenu(Menu menu){
        getMenuInflater().inflate(R.menu.first_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
    return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void showrate(View view) {
        startActivity(new Intent(this, RateListActivity.class));
    }
}

class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "myrate.db";
    private static final String TB_NAME = "tb_rates";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,
                    int version){
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME TEXT,CURRATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

class RateManager {

    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TN_NAME;
    }

    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("currate", item.getCurRate());
        db.insert(TBNAME,null,values);
        db.close();
    }

    public void addAll(List<RateItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(RateItem item : list){
            ContentValues values = new ContentValues();
            values.put("curname", item.getCurName());
            values.put("currate", item.getCurRate());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("currate", item.getCurRate());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<RateItem> listAll(){
        List<RateItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<RateItem>();
            while(cursor.moveToNext()){
                RateItem item = new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));

                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

    public RateItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RateItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
}