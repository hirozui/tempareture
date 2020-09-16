package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
TextView out;
EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = (TextView)findViewById(R.id.out);
        inp = (EditText)findViewById(R.id.inp);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Log.i("main","onClick msg....");
        String str = inp.getText().toString();
        double num = Double.parseDouble(str);
        double res = (num/5) *9 + 32;
        out.setText(String.valueOf(res));
    }
}