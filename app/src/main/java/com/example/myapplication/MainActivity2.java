package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        EditText dollar2 = findViewById(R.id.dollar2);
        dollar2.setText(String.valueOf(intent.getFloatExtra("dollar",0f)));
        EditText euro2 = findViewById(R.id.euro2);
        euro2.setText(String.valueOf(intent.getFloatExtra("euro",0f)));
        EditText won2 = findViewById(R.id.won2);
        won2.setText(String.valueOf(intent.getFloatExtra("won",0f)));
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
        finish();
    }
}