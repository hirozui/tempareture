package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity implements Runnable{

    private static final String TAG = "MainActivity2";

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        final EditText dollar2 = findViewById(R.id.dollar2);
        dollar2.setText(String.valueOf(intent.getFloatExtra("dollar",0f)));
        final EditText euro2 = findViewById(R.id.euro2);
        euro2.setText(String.valueOf(intent.getFloatExtra("euro",0f)));
        EditText won2 = findViewById(R.id.won2);
        won2.setText(String.valueOf(intent.getFloatExtra("won",0f)));

        Thread t = new Thread(this);
        t.start();


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str =(String)msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg = " + str);
                    dollar2.setText(str);
                }
                super.handleMessage(msg);
            }
        };


    }

    public void Return(View view){
        Intent data = getIntent();
        EditText dollar2 = findViewById(R.id.dollar2);
        Pattern pattern = Pattern.compile("[0-9]+\\.?[0-9]*");
        Matcher matcher = pattern.matcher(dollar2.getText());
        if(!matcher.matches()){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
            return;
        }
        data.putExtra("dollar",Float.parseFloat(matcher.group()));

        EditText euro2 = findViewById(R.id.euro2);
        matcher = pattern.matcher(euro2.getText());
        if(!matcher.matches()){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
            return;
        }
        data.putExtra("euro",Float.parseFloat(matcher.group()));

        EditText won2 = findViewById(R.id.won2);
        matcher = pattern.matcher(won2.getText());
        if(!matcher.matches()){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
            return;
        }
        data.putExtra("won",Float.parseFloat(matcher.group()));
        setResult(2,data);

        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        float dollarRate = sharedPreferences.getFloat("dollar2", 0.0f);
        float euroRate = sharedPreferences.getFloat("euro2", 0.0f);
        float wonRate = sharedPreferences.getFloat("won2", 0.0f);

        SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_Rate",dollarRate);
        editor.putFloat("euro_Rate",euroRate);
        editor.putFloat("won_Rate",wonRate);
        editor.apply();

        finish();
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run()......");
        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what = 5;
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);
        URL url = null;
        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG,"run:html=" + html);
    }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz < 0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
    }