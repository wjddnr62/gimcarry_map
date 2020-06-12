package com.laonstory.laon_dev01.gimcarry_map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash00_Activity extends AppCompatActivity {

    public String keyword;

    // 첫 화면 스플레시 화면
    //-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash00_layout);

        getPreferences();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // 이용동의 화면으로 이동
                if(keyword.equals("")) {
                    Intent intent = new Intent(Splash00_Activity.this, TermActivity.class);
                    startActivity(intent);
                    finish();
                }
                // 이용안내로 이동
                else{
                    Intent intent = new Intent(Splash00_Activity.this, Splash01_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);


    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    //데이터 가져오기
    private void getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        keyword = pref.getString("PREF_STRNAME", ""); //key, value(defaults)
    }

}
