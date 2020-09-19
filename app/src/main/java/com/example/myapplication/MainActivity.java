package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
TextView sc;
Button btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sc = (TextView)findViewById(R.id.score);

        Button btn1 = (Button)findViewById(R.id.btn1);
        Button btn2 = (Button)findViewById(R.id.btn2);
        Button btn3 = (Button)findViewById(R.id.btn3);
        Button btn4 = (Button)findViewById(R.id.btn4);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = sc.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 3);
                sc.setText(String.valueOf(res));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String score = sc.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 2);
                sc.setText(String.valueOf(res));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = sc.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 1);
                sc.setText(String.valueOf(res));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sc.setText("0");
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}
