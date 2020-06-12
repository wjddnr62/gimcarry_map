package com.laonstory.laon_dev01.gimcarry_map;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Created by laon-dev01 on 2018-04-10.
 */

public class DetailInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String date1 = "0000-00-00"; //탑승일
    public String time1 = "00:00"; //탑승시간
    public String date2 = "0000-00-00"; //방문수거일
    public String time2 = "00:00"; //방문희망시간
    public String placewrite = "none"; //상세주소
    public String airportkind = "XX000"; //항공편
    public String placeall = "none"; //전체주소
    public long mNow;
    public Year yeartv;
    public int count = 0;
    //키보드입력스타일 대문자로변경
    protected InputFilter filter = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
    // 오늘기준 날짜담을 변수
    int mYear, mMonth, mDay, mHour, mMinute;
    // 방문수거일시 날짜담을 변수
    int mYear2, mMonth2, mDay2, mHour2, mMinute2;
    // 날짜담을 변수
    int nYear, nMonth, nDay, nHour;
    TextView mTxtDate1;
    TextView mTxtDate2;
    InputMethodManager imm;
    EditText et;
    EditText et1;
    //탑승일 리스너
    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            //사용자가 입력한 값을 가져온뒤
            int bordingYear = year;
            int bordingMonth = monthOfYear;
            int bordingDay = dayOfMonth;

            mTxtDate1 = (TextView) findViewById(R.id.daytv1);

            String dateTemp = "0000-00-00";

            if (bordingMonth <= 8 && bordingDay <= 9) {
                dateTemp = String.format("%d" + "-" + "0" + "%d" + "-0" + "%d", bordingYear, bordingMonth + 1, bordingDay);
            } else if (bordingMonth <= 8) {
                dateTemp = String.format("%d" + "-" + "0" + "%d-%d", bordingYear, bordingMonth + 1, bordingDay);
            } else if (bordingDay <= 9) {
                dateTemp = String.format("%d" + "-%d" + "-0" + "%d", bordingYear, bordingMonth + 1, bordingDay);
            } else {
                dateTemp = String.format("%d-%d-%d", bordingYear, bordingMonth + 1, bordingDay);
            }

            mTxtDate1.setText(dateTemp);
            date1 = dateTemp;


//            mTxtDate2 = (TextView)findViewById(R.id.daytv2);
//            String tv = mTxtDate2.getText().toString();
//
//            //텍스트뷰의 값을 업데이트함
//
//            if(tv.equals("")){
//                Toast.makeText(getApplicationContext(), R.string.방문수거일먼저입력,Toast.LENGTH_LONG).show();
//            }
//            else{
//                UpdateNow1();
//
//            }

        }
    };
    // 방문수거일 리스너
    DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            //사용자가 입력한 값을 가져온뒤
            int visitYear = year;
            int visitMonth = monthOfYear;
            int visitDay = dayOfMonth;

            mTxtDate2 = (TextView) findViewById(R.id.daytv2);

            String dateTemp = "0000-00-00";

            if (visitMonth <= 8 && visitDay <= 9) {
                dateTemp = String.format("%d" + "-" + "0" + "%d" + "-0" + "%d", visitYear, visitMonth + 1, visitDay);
            } else if (visitMonth <= 8) {
                dateTemp = String.format("%d" + "-" + "0" + "%d-%d", visitYear, visitMonth + 1, visitDay);
            } else if (visitDay <= 9) {
                dateTemp = String.format("%d" + "-%d" + "-0" + "%d", visitYear, visitMonth + 1, visitDay);
            } else {
                dateTemp = String.format("%d-%d-%d", visitYear, visitMonth + 1, visitDay);
            }


            mTxtDate2.setText(dateTemp);
            date2 = dateTemp;


//            date2 = String.format("%d-%d-%d", visitYear, visitMonth + 1, visitDay);
//            mTxtDate2.setText(String.format("%d-%d-%d", visitYear, visitMonth + 1, visitDay));

            // 텍스트뷰의 값을 업데이트함
//                    UpdateNow2();
        }
    };

    //날짜 대화상자 리스너 부분
    // 탑승시간 시간 설정
    // ---------------------------------------------------------------------------------------------------------------
    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

        @Override

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            // 탑승시간 선택
            TextView tv2 = (TextView) findViewById(R.id.time1);
            String country = getResources().getString(R.string.전화번호국적);

            //시간 텍스트 업데이트
            if (minute <= 9) {
                tv2.setText(+hourOfDay + ":" + "0" + minute);
            } else {
                tv2.setText(+hourOfDay + ":" + minute);
            }
            time1 = tv2.getText().toString();


            /*
            //같을날일때
            if(mDay2 == nDay){
                //지금시각이 11시이후일때
                if (nHour >= 11){
                    Toast.makeText(getApplicationContext(), R.string.당일예약은, Toast.LENGTH_SHORT).show();
                }
                //아닐경우
                else{
                    //6시이후예약가능
                     if(hourOfDay>18){
                         //한국일때
                         if(country.equals("KR")) {
                             //오전일경우
                             if (hourOfDay - 12 < 0) {
                                 Toast.makeText(getApplicationContext(), R.string.방문수거일과, Toast.LENGTH_LONG).show();
                             } else {
                                 //시간 텍스트 업데이트
                                 if (minute <= 9) {
                                     tv2.setText(+hourOfDay + ":" + "0" + minute);
                                 } else {
                                     tv2.setText(+hourOfDay + ":" + minute);
                                 }
                                 time1 = tv2.getText().toString();
                             }
                         }
                         //다른나라일경우
                         else {
                             if (hourOfDay - 12 < 0) {
                                 Toast.makeText(getApplicationContext(), R.string.방문수거일과, Toast.LENGTH_LONG).show();
                             } else {
                                 if (minute <= 9) {
                                     tv2.setText(+hourOfDay+ ":" + "0" + minute);
                                 } else {
                                     tv2.setText(+hourOfDay+ ":" + minute);
                                 }
                                 time1 = tv2.getText().toString();
                             }
                         }
                     }
                     //오후6시이상이 아닐경우
                     else {
                         Toast.makeText(getApplicationContext(), R.string.당일예약시, Toast.LENGTH_LONG).show();
                     }
                }

            }
            //같은날이 아닐때
            else {
                //방문수거일과 탑승일이 동일시
                if(mYear2 == mYear && mMonth2 == mMonth && mDay2 == mDay){
                    //한국일떄
                    if(country.equals("KR")) {
                        //오후인지 오전인지
                        if (hourOfDay - 12 < 0) {
                            Toast.makeText(getApplicationContext(), R.string.방문수거일과, Toast.LENGTH_LONG).show();
                        } else {
                            //시간 업데이트
                            if (minute <= 9) {
                                tv2.setText(+hourOfDay + ":" + "0" + minute);
                            } else {
                                tv2.setText(+hourOfDay + ":" + minute);
                            }
                            time1 = tv2.getText().toString();
                        }
                    }
                    //다른나라일때
                    else {
                        //오후인지 오전인지
                        if (hourOfDay - 12 < 0) {
                            Toast.makeText(getApplicationContext(), R.string.방문수거일과, Toast.LENGTH_LONG).show();
                        } else {
                            //시간 업데이트
                            if (minute <= 9) {
                                tv2.setText(+hourOfDay+ ":" + "0" + minute);
                            } else {
                                tv2.setText(+hourOfDay+ ":" + minute);
                            }
                            time1 = tv2.getText().toString();
                        }
                    }
                }
                //방문수거일과 탑승일이 동일하지않을때
                else {
                    //한국일때
                    if(country.equals("KR")) {
                        //시간업데이트
                        if (minute <= 9) {
                            tv2.setText(+hourOfDay + ":" + "0" + minute);
                        } else {
                            tv2.setText(+hourOfDay + ":" + minute);
                        }
                        time1 = tv2.getText().toString();
                    }
                    //다른나라일때
                    else {
                        //시간업데이트
                        if (minute <= 9) {
                            tv2.setText(+hourOfDay  + ":" + "0" + minute);
                        } else {
                            tv2.setText(+hourOfDay + ":" + minute );
                        }
                        time1 = tv2.getText().toString();
                    }
                }
            }
            */

// 설정버튼 눌렀을 때


        }

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailinfoactivity);

        //TextView tv1 = (TextView)findViewById(R.id.placename);
        TextView tvPlace = (TextView) findViewById(R.id.placename);
        // 항공편
        EditText flightname = (EditText) findViewById(R.id.airname);

        //항공편 입력스타일지정
        flightname.setFilters(new InputFilter[]{filter});
        //키보드 서비스, 화면 터치 시 키보드를 내리기 위한 선언
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        String country = getResources().getString(R.string.전화번호국적);

        // 주소입력 : 상세주소 입력 받음
        if (country.equals("US")) {
            //미국이면 호텔이름을 받는다
            Intent intent2 = getIntent();
            String hotel = intent2.getStringExtra("hotel");
            tvPlace.setText(hotel);
        } else {
            //나머지면 다음주소api에서 받아온 장소 기입
            Intent intent = getIntent();
            final String place1 = intent.getStringExtra("place");
            tvPlace.setText(place1);
        }


        // "다음" 버튼 : 예약 정보 전부 입력하면 다음 화면으로 넘어갈때 사용하는 버튼
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btn3 = (Button) findViewById(R.id.btn1);  // 인천공항 1 터미널
                Button btn4 = (Button) findViewById(R.id.btn2);  // 인천공항 1 터미널
                EditText airname = (EditText) findViewById(R.id.airname);    // 편명

                TextView tv_place = (TextView) findViewById(R.id.placename);

                TextView tv1 = (TextView) findViewById(R.id.placename);      // 기본주소
                EditText plwred = (EditText) findViewById(R.id.placewrite);  // 상세주소

                //plwred에서 텍스트를 받아와 placewrite에 넣기
                placewrite = plwred.getText().toString();
                //주소+상세주소
                placeall = tv1.getText().toString() + " / " + placewrite;


                //입력값이 널인지 비교
                if (airname.getText().toString().equals("")) {
                    count = 0;
                } else if (plwred.getText().toString().equals("")) {
                    count = 0;
                } else if (airportkind.equals("")) {
                    count = 0;
                } else if (date1.equals("")) {
                    count = 0;
                } else if (date2.equals("")) {
                    count = 0;
                } else if (time1.equals("")) {
                    count = 0;
                } else if (time2.equals("")) {
                    count = 0;
                } else {
                    count = 1;
                }

                count = 1;

                switch (view.getId()) {
                    //다음버튼 Detailinfo2 로 정보를 보내면서 넘어간다
                    case R.id.next1:
                        if (count == 1) {
                            Intent intent = new Intent(getApplicationContext(), DetailInfoActivity2.class);
                            //인텐트에 입력정보 넣기
                            intent.putExtra("placeall", placeall);
                            intent.putExtra("date1", date1);
                            intent.putExtra("time1", time1);
                            intent.putExtra("date2", date2);
                            intent.putExtra("time2", time2);
                            intent.putExtra("airname", airname.getText().toString());
                            intent.putExtra("airportkind", airportkind);

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            String format_date = format.format(calendar.getTime());
                            int check = date2.compareTo(format_date);
                            int check2 = date1.compareTo(format_date);
                            Log.e("check", String.valueOf(check));
                            if ((check == 0 && check2 == 0 && (placeall.contains("서울") || placeall.contains("인천") || placeall.contains("경기"))) || placeall.contains("서울") || placeall.contains("인천") || placeall.contains("경기")) {
                                if (check == 0) {
                                    intent.putExtra("now_day", check);
                                }
                                if (check2 == 0) {
                                    intent.putExtra("now_day2", check2);
                                }
                                Log.e("placeall", placeall);
                                Log.e("date1", date1);
                                Log.e("time1", time1);
                                Log.e("date2", date2);
                                Log.e("time2", time2);
                                Log.e("airname", airname.getText().toString());
                                Log.e("airportkind", airportkind);
                                startActivity(intent);
                            } else if ((check == 0 && check2 == 0 && (!placeall.contains("서울") || !placeall.contains("인천") || !placeall.contains("경기")))) {
                                Toast.makeText(DetailInfoActivity.this, "지방은 당일배송 서비스를 하지 않습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(intent);
                            }
                            // time1 : 탑승일시 시간, date1 : 탑승일시 날짜, time2 : 방문일시 시간, date2 : 방문일시 날짜, airportkind : 도착 공항, airname : 항공편 명


                        } else {
                            //모든사항입력토스트
                            Toast.makeText(getApplicationContext(), R.string.입력되지않은항목, Toast.LENGTH_LONG).show();
                        }
                        break;
                    //뒤로가기버튼
                    case R.id.backbtn1:
                        //지도부분으로이동
                        Intent intent2 = new Intent(getApplicationContext(), daummapapiActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    //도착공항1터미널 버튼
                    case R.id.btn1:
                        //눌렀을때 토글되면서 버튼색상변경과 선택된값 string에 저장
                        btn3.setBackgroundColor(Color.parseColor("#F36F24"));
                        btn3.setTextColor(Color.WHITE);
                        btn4.setBackgroundResource(R.drawable.squarewhiteorange);
                        btn4.setTextColor(Color.parseColor("#888888"));
                        if (btn3.getText().toString().equals("Incheon Airport Terminal 1")) {
                            airportkind = "인천공항 1 터미널";
                        } else {
                            airportkind = btn3.getText().toString();
                        }
                        break;
                    //도착공항2버튼
                    case R.id.btn2:
                        //눌렀을때 토글되면서 버튼색상변경과 선택된값 string에 저장
                        btn4.setBackgroundColor(Color.parseColor("#F36F24"));
                        btn4.setTextColor(Color.WHITE);
                        btn3.setBackgroundResource(R.drawable.squarewhiteorange);
                        btn3.setTextColor(Color.parseColor("#888888"));
                        if (btn4.getText().toString().equals("Incheon Airport Terminal 2")) {
                            airportkind = "인천공항 2 터미널";
                        } else {
                            airportkind = btn4.getText().toString();
                        }
                        break;
                }
            }
        };

        //버튼리스너 지정
        Button btn1 = (Button) findViewById(R.id.next1);
        btn1.setOnClickListener(onClickListener);
        Button btn2 = (Button) findViewById(R.id.backbtn1);
        btn2.setOnClickListener(onClickListener);
        Button btn3 = (Button) findViewById(R.id.btn1);
        btn3.setOnClickListener(onClickListener);
        Button btn4 = (Button) findViewById(R.id.btn2);
        btn4.setOnClickListener(onClickListener);

        //오늘 날짜 받아오기
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        //스피너 선언
        Spinner spinner = findViewById(R.id.spinner1);

        //스피너에 아이템 넣기
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //스피너 아답터설정
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    //화면터치시 키보드를 내린다
    public void linearOnClick(View v) {
        et = (EditText) findViewById(R.id.placewrite);
        et1 = (EditText) findViewById(R.id.airname);
        // 키보드 숨겨주는거
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
    }

    //온클릭 리스너
    public void mOnClick(View v) {
        et = (EditText) findViewById(R.id.placewrite);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

        switch (v.getId()) {

            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌
            //탑승일 선택 버튼
//            case R.id.datesel1:
//                //여기서 리스너도 등록함
//                //날짜다이얼로그보여줌
//                new DatePickerDialog(this, mDateSetListener1, mYear, mMonth, mDay).show();
//                break;

            //탑승일 선택 텍스트
            case R.id.daytv1:
                //날짜다이얼로그보여줌
                new DatePickerDialog(this, mDateSetListener1, mYear, mMonth, mDay).show();

                break;


            //시간 대화상자 버튼이 눌리면 대화상자를 보여줌
//            case R.id.timesel:
//                TimePickerDialog dialog = new TimePickerDialog(this, listener, mHour, mMinute, false);
//
//                mTxtDate1 = (TextView)findViewById(R.id.daytv1);
//                //탑승일 값받아옴
//                String tv = mTxtDate1.getText().toString();
//                //탑승일먼저입력조건
//                if(tv.equals("")){
//                    Toast.makeText(getApplicationContext(),R.string.탑승일먼저입력,Toast.LENGTH_LONG).show();
//                }
//                else{
//                    dialog.show();
//                }
//
//
//
//                break;

            case R.id.time1:
                TimePickerDialog dialog2 = new TimePickerDialog(this, listener, mHour, mMinute, false);

                mTxtDate1 = (TextView) findViewById(R.id.daytv1);
                //탑승일 값받아옴
                String tv2 = mTxtDate1.getText().toString();

                //탑승일먼저입력조건
                if (tv2.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.탑승일먼저입력, Toast.LENGTH_LONG).show();
                } else {
                    dialog2.show();
                }

                break;

//            case R.id.datesel2:
//                //여기서 리스너도 등록함
//                new DatePickerDialog(this,  mDateSetListener2, mYear,
//                        mMonth, mDay).show();
//                break;

            case R.id.daytv2:
                new DatePickerDialog(this, mDateSetListener2, mYear,
                        mMonth, mDay).show();
                break;


        }

    }


    // 영문만 허용 (숫자 포함)

    //탑승일 업데이트
    void UpdateNow1() {

        mTxtDate1 = (TextView) findViewById(R.id.daytv1);
        // 월이 같을때는 탑승일은 방문수거일과 같거나 많아야함
        if (mYear >= mYear2 && mMonth + 1 == mMonth2 + 1 && mDay >= mDay2) {

            date1 = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);

            mTxtDate1.setText(String.format("%d-%d-%d", mYear,
                    mMonth + 1, mDay));
        } else {
            Toast.makeText(getApplicationContext(), R.string.탑승일은, Toast.LENGTH_LONG).show();
        }
    }

    //방문수거일 업데이트
    void UpdateNow2() {
        // 방문수거일 날짜 선택
        mTxtDate2 = (TextView) findViewById(R.id.daytv2);

        //날짜생성
        Calendar calendar = new GregorianCalendar();
        nYear = calendar.get(Calendar.YEAR);
        nMonth = calendar.get(Calendar.MONTH) + 1;
        nDay = calendar.get(Calendar.DAY_OF_MONTH);
        nHour = calendar.get(Calendar.HOUR_OF_DAY);
        Toast.makeText(getApplicationContext(), String.valueOf(mMonth) + "_" + String.valueOf(nMonth), Toast.LENGTH_LONG).show();

        //같은달일때 방문수거일은 같은날이거나 다음날이여야함
        if (mYear2 >= mYear && mMonth2 + 1 == mMonth && mDay2 >= mDay) {
            date2 = String.format("%d-%d-%d", mYear2, mMonth2 + 1, mDay2);
            mTxtDate2.setText(String.format("%d-%d-%d", mYear2, mMonth2 + 1, mDay2));
        }
        //달이 같지 않을경우
        else if (mYear2 >= mYear && mMonth2 + 1 > mMonth) {
            date2 = String.format("%d-%d-%d", mYear2, mMonth2 + 1, mDay2);
            mTxtDate2.setText(String.format("%d-%d-%d", mYear2, mMonth2 + 1, mDay2));
        }
        //지날날일경우
        else if (mYear2 >= mYear && mMonth2 + 1 <= mMonth && mDay2 < mDay) {
            Toast.makeText(getApplicationContext(), R.string.지난날, Toast.LENGTH_LONG).show();
        }
        //다아닐경우
        else {
            Toast.makeText(getApplicationContext(), R.string.방문신청, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    //방문수거시간 스피너요소값 선택되었을때
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //방문수거시간 텍스트 설정
        String text = adapterView.getItemAtPosition(i).toString();
        TextView tv1 = (TextView) findViewById(R.id.time2);
        tv1.setText(text);
        time2 = text;

        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //뒤로가기 눌렀을때
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
        startActivity(intent);
        finish();
    }


}
