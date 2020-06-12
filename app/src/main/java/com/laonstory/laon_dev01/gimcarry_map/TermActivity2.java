package com.laonstory.laon_dev01.gimcarry_map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by laon-dev01 on 2018-04-24.
 */

public class TermActivity2 extends AppCompatActivity {

    // 이용약관 및 개인정보 동의 화면
    //-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termofuselayout2);


        //온클릭리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                //체크박스 선언
                CheckBox c1 = (CheckBox)findViewById(R.id.term2check1);
                CheckBox c2 = (CheckBox)findViewById(R.id.term2check2);
                CheckBox c3 = (CheckBox)findViewById(R.id.term2check3);
                switch (view.getId()) {
                    //메인메뉴로이동버튼
                    case R.id.nextterm2:
                        //체크박스 체크
                        if(c1.isChecked()&&c2.isChecked()&&c3.isChecked()) {
                            //최초한번만
                            savePreferences();
                            Intent intent = new Intent(getApplicationContext(), ManuActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),R.string.모든항목동의,Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),R.string.모든항목동의2,Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        };

        Button btn1 = (Button)findViewById(R.id.nextterm2);
        btn1.setOnClickListener(onClickListener);




    }


    //데이터 저장하기
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("PREF_STRNAME", "최초실행");
        editor.commit();
    }

}
