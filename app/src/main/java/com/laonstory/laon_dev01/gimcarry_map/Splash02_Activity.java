package com.laonstory.laon_dev01.gimcarry_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Splash02_Activity extends AppCompatActivity {

    // 비용안내 화면
    //-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash02_layout);


        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    //다음버튼
                    case R.id.nextterm1:
                        //다음설명으로넘어감
                        Intent intent = new Intent(getApplicationContext(), ManuActivity.class);
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
