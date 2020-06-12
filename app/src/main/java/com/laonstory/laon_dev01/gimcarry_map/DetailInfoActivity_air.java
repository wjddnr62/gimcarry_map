package com.laonstory.laon_dev01.gimcarry_map;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Created by laon-dev01 on 2018-04-10.
 */

public class DetailInfoActivity_air extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int mYear, mMonth, mDay, mHour, mMinute;
    int mYear2, mMonth2, mDay2, mHour2, mMinute2;
    int nYear, nMonth, nDay;
    TextView mTxtDate1;
    TextView mTxtDate2;
    public String time1;
    public String time2;
    public String date1;
    public String date2;
    public String placewrite;
    public String airportkind;
    public String placeall;
    public int count = 0;
    InputMethodManager imm;
    EditText et;
    EditText et1;
    private Button now_day, next_day;
    int day_status = 2;
    private TextView daytv1_air;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailinfoactivity_airport);
        String country = getResources().getString(R.string.전화번호국적);

        TextView tv1 = (TextView) findViewById(R.id.placename_air);
        now_day = findViewById(R.id.now_day);
        next_day = findViewById(R.id.next_day);
        daytv1_air = findViewById(R.id.daytv1_air);

        if (country.equals("US")) {
            Intent intent2 = getIntent();
            String hotel = intent2.getStringExtra("hotel");
            tv1.setText(hotel);
        } else {
            //나머지면 다음주소api에서 받아온 장소 기입
            Intent intent = getIntent();
            final String place1 = intent.getStringExtra("place");
            tv1.setText(place1);
        }

        EditText flightname = (EditText) findViewById(R.id.airname_air);
        flightname.setFilters(new InputFilter[]{filter});

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @SuppressLint({"ResourceType", "NewApi"})
            @Override
            public void onClick(View view) {
                Button btn3 = (Button) findViewById(R.id.btn1_air);
                Button btn4 = (Button) findViewById(R.id.btn2_air);

                TextView tv1 = (TextView) findViewById(R.id.placename_air);

                EditText airname = (EditText) findViewById(R.id.airname_air);

                TextView tv_place = (TextView) findViewById(R.id.placename_air);

                EditText plwred = (EditText) findViewById(R.id.placewrite_air);


                String country = getResources().getString(R.string.전화번호국적);

//                // 주소입력 : 상세주소 입력 받음
//                if(country.equals("US")){
//                    //미국이면 호텔이름을 받는다
//                    Intent intent2 = getIntent();
//                    String hotel = intent2.getStringExtra("hotel");
//                    tv1.setText(hotel);
//                } else{
//                    //나머지면 다음주소api에서 받아온 장소 기입
//                    Intent intent = getIntent();
//                    final String place1 = intent.getStringExtra("place");
//                    tv1.setText(place1);
//                }

                placewrite = plwred.getText().toString();

                //오류원인
                placeall = tv1.getText().toString() + " / " + placewrite;

                if (airname == null || airname.getText().toString().equals("")) {
                    count = 0;
                } else if (plwred.getText().toString().equals("")) {
                    count = 0;
                } else if (airportkind == null || airportkind.equals("")) {
                    count = 0;
                } else if (date1.equals("")) {
                    count = 0;
                } else {
                    count = 1;
                }
                count = 1;

                switch (view.getId()) {


                    case R.id.next1_air:
                        if (count == 1) {
                            if (day_status == 2 || (daytv1_air.getText().toString() == null || daytv1_air.getText().toString().equals(""))) {
                                Toast.makeText(DetailInfoActivity_air.this, "공항착륙일 또는 배송예정일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                date1 = daytv1_air.getText().toString();
                                time1 = "00:00";
                                Intent intent = new Intent(getApplicationContext(), DetailInfoActivity2_airport.class);
                                intent.putExtra("placeall", airportkind);
                                intent.putExtra("date2", date2);
                                intent.putExtra("date1", date1);
                                intent.putExtra("time1", time1);
                                intent.putExtra("time2", time2);
                                intent.putExtra("airname", airname.getText().toString());
                                intent.putExtra("airportkind", placeall);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(new Date());
                                String format_date = format.format(calendar.getTime());
                                int check = date1.compareTo(format_date);
                                Log.e("check", String.valueOf(check));
                                if ((day_status == 0 && (placeall.contains("서울") || placeall.contains("인천") || placeall.contains("경기"))) || placeall.contains("서울") || placeall.contains("인천") || placeall.contains("경기")) {
                                    if (day_status == 0) {
                                        intent.putExtra("now_day", day_status);
                                    }
                                    Log.e("placeall", placeall);
                                    Log.e("airname", airname.getText().toString());
                                    Log.e("airportkind", airportkind);
                                    startActivity(intent);
                                } else if ((day_status == 0 && (!placeall.contains("서울") || !placeall.contains("인천") || !placeall.contains("경기")))) {
                                    Toast.makeText(DetailInfoActivity_air.this, "지방은 당일배송 서비스를 하지 않습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(intent);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.입력되지않은항목, Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.backbtn1_air:
                        Intent intent2 = new Intent(getApplicationContext(), daummapapiActivity_air.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.btn1_air:
                        btn3.setBackgroundColor(Color.parseColor("#F36F24"));
                        btn3.setTextColor(Color.WHITE);
                        btn4.setBackgroundResource(R.drawable.squarewhiteorange);
                        btn4.setTextColor(Color.parseColor("#888888"));
                        airportkind = btn3.getText().toString();
                        if (btn3.getText().toString().equals("Incheon Airport Terminal 1")) {
                            airportkind = "인천공항 1 터미널";
                        } else {
                            airportkind = btn3.getText().toString();
                        }
                        break;
                    case R.id.btn2_air:
                        btn4.setBackgroundColor(Color.parseColor("#F36F24"));
                        btn4.setTextColor(Color.WHITE);
                        btn3.setBackgroundResource(R.drawable.squarewhiteorange);
                        btn3.setTextColor(Color.parseColor("#888888"));
                        if (btn4.getText().toString().equals("Incheon Airport Terminal 2")) {
                            airportkind = "인천공항 2 터미널";
                        } else {
                            airportkind = btn4.getText().toString();
                        }
                        airportkind = btn4.getText().toString();
                        break;
                    case R.id.now_day:
                        if (daytv1_air.getText().toString() == null || daytv1_air.getText().toString().equals("")){
                            Toast.makeText(DetailInfoActivity_air.this, "공항착륙일을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            date1 = daytv1_air.getText().toString();
                            day_status = 0;
                            now_day.setBackgroundResource(R.color.orange);
                            now_day.setTextColor(Color.WHITE);
                            next_day.setBackgroundColor(Color.WHITE);
                            next_day.setTextColor(R.color.darkgray);
                            Calendar calendar = Calendar.getInstance();
                            try {
                                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            date2 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                            Log.e("now_day", date1);
                        }
                        break;
                    case R.id.next_day:
                        if (daytv1_air.getText().toString() == null || daytv1_air.getText().toString().equals("")){
                            Toast.makeText(DetailInfoActivity_air.this, "공항착륙일을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            date1 = daytv1_air.getText().toString();
                            day_status = 1;
                            now_day.setBackgroundColor(Color.WHITE);
                            now_day.setTextColor(R.color.darkgray);
                            next_day.setBackgroundResource(R.color.orange);
                            next_day.setTextColor(Color.WHITE);
                            Calendar calendar1 = Calendar.getInstance();
                            try {
                                calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            calendar1.add(Calendar.DATE, 1);
                            date2 = new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime());
                            Log.e("next_day", date1);
                        }
                }
            }
        };

        Button btn1 = (Button) findViewById(R.id.next1_air);
        Button btn2 = (Button) findViewById(R.id.backbtn1_air);
        Button btn3 = (Button) findViewById(R.id.btn1_air);
        Button btn4 = (Button) findViewById(R.id.btn2_air);

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        now_day.setOnClickListener(onClickListener);
        next_day.setOnClickListener(onClickListener);

        Calendar cal = new GregorianCalendar();

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);


        Spinner spinner = findViewById(R.id.spinner1_air);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void linearOnClick(View v) {
        et = (EditText) findViewById(R.id.placewrite_air);
        et1 = (EditText) findViewById(R.id.airname_air);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
    }

    public void mOnClick(View v) {
        et = (EditText) findViewById(R.id.placewrite_air);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

        switch (v.getId()) {

            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌

//            case R.id.datesel1_air:
//
//                //여기서 리스너도 등록함
//
//                new DatePickerDialog(this, mDateSetListener1, mYear,
//
//                        mMonth, mDay).show();
//
//                break;

            case R.id.daytv1_air:
                new DatePickerDialog(this, mDateSetListener1, mYear,

                        mMonth, mDay).show();

                break;
//            case R.id.time2_air:
//
//                break;


            //시간 대화상자 버튼이 눌리면 대화상자를 보여줌
//            case R.id.timesel_air:
//                TimePickerDialog dialog = new TimePickerDialog(this, listener, mHour, mMinute, false);
//                mTxtDate1 = (TextView)findViewById(R.id.daytv1_air);
//                String tv = mTxtDate1.getText().toString();
//                //텍스트뷰의 값을 업데이트함
//                if(tv.equals("")){
//                    Toast.makeText(getApplicationContext(),R.string.탑승일먼저입력,Toast.LENGTH_LONG).show();
//                }
//                else{
//                    dialog.show();
//                }
//
//                dialog.show();
//
//
//                break;
//
//            case R.id.time1_air:
//                TimePickerDialog dialog2 = new TimePickerDialog(this, listener, mHour, mMinute, false);
//                mTxtDate1 = (TextView)findViewById(R.id.daytv1_air);
//
//                    dialog2.show();
//
//
//                break;


        }

    }

    //날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            //사용자가 입력한 값을 가져온뒤
            int bordingYear = year;
            int bordingMonth = monthOfYear;
            int bordingDay = dayOfMonth;

            mTxtDate1 = (TextView) findViewById(R.id.daytv1_air);
//            mTxtDate2 = (TextView)findViewById(R.id.daytv2_air);

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

//            Log.d("synack",dateTemp);

            date1 = dateTemp;
            mTxtDate1.setText(dateTemp);
//            mTxtDate2.setText(dateTemp);


//            //텍스트뷰의 값을 업데이트함
//            mTxtDate1 = (TextView)findViewById(R.id.daytv1_air);
//            mTxtDate2 = (TextView)findViewById(R.id.daytv2_air);
//            String tv = mTxtDate2.getText().toString();
//
//            date1 = String.format("%d-%d-%d", bordingYear, bordingMonth + 1, bordingDay);
//            mTxtDate1.setText(String.format("%d-%d-%d", bordingYear, bordingMonth + 1, bordingDay));
//            mTxtDate2.setText(String.format("%d-%d-%d", bordingYear, bordingMonth + 1, bordingDay));

            //텍스트뷰의 값을 업데이트함
//            UpdateNow1();
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


//            mTxtDate2 = (TextView)findViewById(R.id.daytv2);
//            date2 = String.format("%d-%d-%d", visitYear, visitMonth + 1, visitDay);
//            mTxtDate2.setText(String.format("%d-%d-%d", visitYear, visitMonth + 1, visitDay));

            //텍스트뷰의 값을 업데이트함
//            UpdateNow2();
        }
    };


//    void UpdateNow1(){
//
//        mTxtDate1 = (TextView)findViewById(R.id.daytv1_air);
//        mTxtDate2 = (TextView)findViewById(R.id.daytv2_air);
//
//        Calendar calendar = new GregorianCalendar();
//
//        nYear = calendar.get(Calendar.YEAR);
//        nMonth = calendar.get(Calendar.MONTH) + 1;
//        nDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        if(mYear >= nYear && mMonth+1 == nMonth && mDay > nDay){
//            date1=String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
//            mTxtDate1.setText(String.format("%d-%d-%d", mYear, mMonth + 1, mDay));
//            mTxtDate2.setText(String.format("%d-%d-%d", mYear, mMonth + 1, mDay));
//        }
//        else if(mYear >= nYear && mMonth+1 > nMonth){
//            date1=String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
//            mTxtDate1.setText(String.format("%d-%d-%d", mYear, mMonth + 1, mDay));
//            mTxtDate2.setText(String.format("%d-%d-%d", mYear, mMonth + 1, mDay));
//        }
//        else {
//            Toast.makeText(getApplicationContext(),R.string.오늘이후로예약,Toast.LENGTH_LONG).show();
//        }
//    }

//    void UpdateNow2(){
//        mTxtDate2 = (TextView)findViewById(R.id.daytv2_air);
//        Calendar calendar = new GregorianCalendar();
//        nYear = calendar.get(Calendar.YEAR);
//        nMonth = calendar.get(Calendar.MONTH) + 1;
//        nDay = calendar.get(Calendar.DAY_OF_MONTH);
//        Toast.makeText(getApplicationContext(),mMonth+"_"+mMonth2,Toast.LENGTH_LONG).show();
////        Toast.makeText(getApplicationContext(),String.valueOf(mMonth)+"  _   "+String.valueOf(nMonth),Toast.LENGTH_LONG).show();
//        if(mYear2 >= mYear && mMonth2+1 >= mMonth && mDay2 > mDay){
//            date2=String.format("%d-%d-%d", mYear2,
//                    mMonth2 + 1, mDay2);
//            mTxtDate2.setText(String.format("%d-%d-%d", mYear2,
//                    mMonth2 + 1, mDay2));
//        }
//        else {
//            Toast.makeText(getApplicationContext(),R.string.방문신청,Toast.LENGTH_LONG).show();
//        }
//    }

    // 영문만 허용 (숫자 포함)
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        time2 = text;

        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Back 버튼 입력시
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), daummapapiActivity_air.class);
        startActivity(intent);
        finish();
    }

    //시간 설정
    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

        @Override

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            TextView tv2 = (TextView) findViewById(R.id.time1_air);

            String country = getResources().getString(R.string.전화번호국적);

            if (minute <= 9) {
                tv2.setText(+hourOfDay + ":" + "0" + minute);
            } else {
                tv2.setText(+hourOfDay + ":" + minute);
            }
            time1 = tv2.getText().toString();

            // 이 항목은 의미가 없어서 00:00으로 변경함
            time1 = "00:00";

//            if(mYear2 == mYear && mMonth2 == mMonth && mDay2 == mDay){
//                if(country.equals("KR")) {
//                    if (hourOfDay - 12 < 0) {
//                        Toast.makeText(getApplicationContext(), R.string.방문수거일과, Toast.LENGTH_LONG).show();
//                    } else {
//                        if (minute <= 9) {
//                            tv2.setText(+hourOfDay + ":" + "0" + minute);
//                        } else {
//                            tv2.setText(+hourOfDay + ":" + minute);
//                        }
//                        time1 = tv2.getText().toString();
//                    }
//                }
//                else {
//                    if (hourOfDay - 12 < 0) {
//                        Toast.makeText(getApplicationContext(), R.string.방문수거일과, Toast.LENGTH_LONG).show();
//                    } else {
//                        if (minute <= 9) {
//                            tv2.setText(+hourOfDay+ ":" + "0" + minute);
//                        } else {
//                            tv2.setText(+hourOfDay+ ":" + minute);
//                        }
//                        time1 = tv2.getText().toString();
//                    }
//                }
//
//            }
//            else {
//                if(country.equals("KR")) {
//                    if (minute <= 9) {
//                        tv2.setText(+hourOfDay + ":" + "0" + minute);
//                    } else {
//                        tv2.setText(+hourOfDay + ":" + minute);
//                    }
//                    time1 = tv2.getText().toString();
//                }
//                else {
//                    if (minute <= 9) {
//                        tv2.setText(+hourOfDay  + ":" + "0" + minute);
//                    } else {
//                        tv2.setText(+hourOfDay + ":" + minute );
//                    }
//                    time1 = tv2.getText().toString();
//                }
//            }


        }

    };


}
