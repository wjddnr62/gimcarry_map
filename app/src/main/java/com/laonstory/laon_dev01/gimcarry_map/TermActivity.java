package com.laonstory.laon_dev01.gimcarry_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by laon-dev01 on 2018-04-24.
 */

public class TermActivity extends AppCompatActivity {

    // 이용안내 화면
    //-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termofuselayout);

        LinearLayout l1 = (LinearLayout)findViewById(R.id.termlinear);
        String country = getResources().getString(R.string.전화번호국적);

        //나라에따른 다른 이미지
        if(country.equals("KR")){
            l1.setBackgroundResource(R.drawable.termimg1);

        }
        else {
            l1.setBackgroundResource(R.drawable.termimg2);
        }

        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    //다음버튼
                    case R.id.nextterm1:
                        //다음설명으로넘어감
                            Intent intent = new Intent(getApplicationContext(), TermActivity2.class);
                            startActivity(intent);
                            finish();

                        break;
                }
            }
        };

        //리스너셋팅
        Button btn1 = (Button)findViewById(R.id.nextterm1);
        btn1.setOnClickListener(onClickListener);


    }



}