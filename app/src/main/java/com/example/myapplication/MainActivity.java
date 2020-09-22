package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
TextView sca,scb;
Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sca = (TextView)findViewById(R.id.textView5);
        scb = (TextView)findViewById(R.id.textView7);

        Button btn1 = (Button)findViewById(R.id.button);
        Button btn2 = (Button)findViewById(R.id.button2);
        Button btn3 = (Button)findViewById(R.id.button3);
        Button btn4 = (Button)findViewById(R.id.button7);
        Button btn5 = (Button)findViewById(R.id.button4);
        Button btn6 = (Button)findViewById(R.id.button5);
        Button btn7 = (Button)findViewById(R.id.button6);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = sca.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 3);
                sca.setText(String.valueOf(res));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String score = sca.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 2);
                sca.setText(String.valueOf(res));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = sca.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 1);
                sca.setText(String.valueOf(res));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sca.setText("0");
                scb.setText("0");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = scb.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 3);
                scb.setText(String.valueOf(res));
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = scb.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 2);
                scb.setText(String.valueOf(res));
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String score = scb.getText().toString();
                double num =Double.parseDouble(score);
                int res;
                res = (int) (num + 1);
                scb.setText(String.valueOf(res));
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}
