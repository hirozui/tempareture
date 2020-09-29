package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{
    public HashMap<String,Float> rate = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rate.put("dollar",0.1477f);
        rate.put("euro",0.1256f);
        rate.put("won",171.3421f);
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
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
        }
    }
}
