package com.laonstory.laon_dev01.gimcarry_map;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ReserveinquiryActivity extends AppCompatActivity {



    private static String TAG = "phptest_MainActivity";

    public TextView nodata;

    public String phonenumber;
    public String vno;
    public String vnum;
    public String vnotice;
    public String datetime;

    public boolean flag = false;

    public String[] data1 = new String[100];

    public LayoutInflater mInflater;

    private static final String TAG_JSON="reservation";
    private static final String TAG_VTIME = "re_vtime";
    private static final String TAG_NUMBER = "re_number";
    private static final String TAG_BDATE = "re_bdate";
    private static final String TAG_ADDR1 = "re_addr1";
    private static final String TAG_VDATE = "re_vdate";
    private static final String TAG_ADDR2 = "re_addr2";
    private static final String TAG_FLIGHT = "re_flight";
    private static final String TAG_BAG1 = "re_bag1";
    private static final String TAG_BAG2 = "re_bag2";
    private static final String TAG_TEL = "re_tel";
    private static final String TAG_FEE = "re_fee";
    private static final String TAG_NO = "re_no";
    private static final String TAG_BTIME = "re_btime";
    private static final String TAG_NOTICE = "re_notice";
    private static final String TAG_DATETIME = "re_date";

    String wantPermission = Manifest.permission.READ_PHONE_STATE;

    TelephonyManager telephonyManager;

    private static final int PERMISSION_REQUEST_CODE = 1;

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveinquiryactivity);




        //데이터가 없을때
        nodata = (TextView)findViewById(R.id.nodata);

        //온클릭리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //뒤로가기 버튼
                    case R.id.backbtn_reserve:
                            Intent intent = new Intent(getApplicationContext(), ManuActivity.class);
                            startActivity(intent);
                            finish();
                        break;

                }
            }
        };


        //리스너 셋팅
        Button btn1 = (Button)findViewById(R.id.backbtn_reserve);
        btn1.setOnClickListener(onClickListener);


        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();



        //전화번호 받아오기
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
            phonenumber = getPhoneNumber();
            String ph = phonenumber;


//            if(phonenumber.startsWith("+82")){
//                phonenumber = phonenumber.replace("+82", "0");
//                String formattingNumber = PhoneNumberUtils.formatNumber(phonenumber);
//                phonenumber = formattingNumber;
//                  Toast.makeText(getApplicationContext(),phonenumber,Toast.LENGTH_LONG).show();
//
//            }
//
//            else if(phonenumber.startsWith("82")){
//                phonenumber = phonenumber.replace("82", "0");
//                String formattingNumber = PhoneNumberUtils.formatNumber(phonenumber);
//
//                phonenumber = formattingNumber;
//            }
//
//            else if(phonenumber.startsWith("010")){
//                String formattingNumber = PhoneNumberUtils.formatNumber(phonenumber);
//                phonenumber = formattingNumber;
//                Toast.makeText(getApplicationContext(),"010입니다",Toast.LENGTH_LONG).show();
//                GetData task = new GetData();
//                task.execute("http://108.160.128.68/select.php",phonenumber);
//            }
//
//            else{
//
//            }

            Log.d("synack", "source : _" + phonenumber + "_");

            String phonenumber_tmp = "none";

            if (phonenumber.equals(null)) {
                phonenumber = "010";
            }

            //전화번호 스타일이 다르기때문에 통일
            if (phonenumber.startsWith("010-")) {
                String phonenumberTemp = "";
                String[] phoneNumber;

                phonenumberTemp = String.valueOf(phonenumber);

                if (phonenumberTemp.contains("-")) {
                    phoneNumber = phonenumberTemp.split("-");
                    phonenumber = phoneNumber[0] + phoneNumber[1] + phoneNumber[2];
                    phonenumber_tmp = phonenumberTemp;
                }
                Log.d("synack", "010-_phonenumber : " + phonenumber);
                Log.d("synack", "010-_formattingNumber : " + phonenumberTemp);
                Log.d("synack", "010-_phonenumber_tmp : " + phonenumber_tmp);
            } else if (phonenumber.startsWith("+82")) {
                phonenumber = phonenumber.replace("+82", "0");
                String formattingNumber = PhoneNumberUtils.formatNumber(phonenumber);
                phonenumber_tmp = formattingNumber;

                Log.d("synack", "+82_phonenumber : " + phonenumber);
                Log.d("synack", "+82_formattingNumber : " + formattingNumber);
                Log.d("synack", "+82_phonenumber_tmp : " + phonenumber_tmp);
                // SAMSUNG SM-G610S
            } else if (phonenumber.startsWith("82")) {
                phonenumber = phonenumber.replace("82", "0");
                String formattingNumber = PhoneNumberUtils.formatNumber(phonenumber);
                phonenumber_tmp = formattingNumber;

                Log.d("synack", "82_phonenumber : " + phonenumber);
                Log.d("synack", "82_formattingNumber : " + formattingNumber);
                Log.d("synack", "82_phonenumber_tmp : " + phonenumber_tmp);
            }
//            else if(phonenumber.startsWith("010-")){
//            }
            else {
                Log.d("synack", "010_phonenumber : " + phonenumber);
                Log.d("synack", "010_formattingNumber : " + "phonenumberTemp");
                Log.d("synack", "010_phonenumber_tmp : " + phonenumber_tmp);
                // SAMSUNG SM-G160N
            }


//            phonenumber = phonenumber_tmp;


            // -가 들어간 폰에서 "-'를 제거하는 코드
            //--------------------------------------------------------------------------
//            String phonenumberTemp = "";
//            String[] phoneNumber;
//            phonenumberTemp = String.valueOf(phonenumber);
//            if(phonenumberTemp.contains("-")) {
//                phoneNumber = phonenumberTemp.split("-");
//                phonenumber = phoneNumber[0] + phoneNumber[1] + phoneNumber[2];
//            }
            //--------------------------------------------------------------------------

//            Log.d("synack", "phongenumber : " + String.valueOf(phonenumber));
            //전화번호정보보내고 그에관한정보받아오기
            GetData task = new GetData();
            task.execute("http://gimcarry.com/select.php", phonenumber);

        }
    }


    //전화번호 정보 받아오기
    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReserveinquiryActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                //결과 보여주기
                showResult();

            }
        }


        @Override
        protected String doInBackground(String... params) {

            //보낼 데이터설정
            String serverURL = params[0];
            String tel = (String)params[1];
            String postParameters = "tel=" + tel ;
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                //httpURLConnection.setRequestProperty("content-type", "application/json");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    // 수령완료 업데이트를 하기위한 클래스
    private class GetData1 extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReserveinquiryActivity.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();

            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String no = (String)params[1];
            String number = (String)params[2];
            String token = (String)params[3];
            String postParameters = "no="+no+"&number=" + number+"&token="+token;
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                //httpURLConnection.setRequestProperty("content-type", "application/json");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                Toast.makeText(getApplicationContext(),errorString,Toast.LENGTH_LONG).show();
                return null;
            }

        }
    }




    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            //값이없을경우
            if(jsonArray.length()==0){
                //데이터없음 표시
                nodata.setVisibility(View.VISIBLE);
                mlistView.setVisibility(View.GONE);
            }
            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String vtime = item.getString(TAG_VTIME);
                String number = item.getString(TAG_NUMBER);
                String bdate = item.getString(TAG_BDATE);
                String addr1 = item.getString(TAG_ADDR1);
                String vdate = item.getString(TAG_VDATE);
                String addr2 = item.getString(TAG_ADDR2);
                String flight = item.getString(TAG_FLIGHT);
                String bag1 = item.getString(TAG_BAG1);
                String bag2 = item.getString(TAG_BAG2);
                String tel = item.getString(TAG_TEL);
                String fee = item.getString(TAG_FEE);
                String btime = item.getString(TAG_BTIME);
                HashMap<String,String> hashMap = new HashMap<>();
                data1[i]=number;
                String country = getResources().getString(R.string.전화번호국적);
                //미국일때 개를 ea로 변경
                if(country.equals("US")){
                    bag1=bag1.replace("개","EA");
                    bag2=bag2.replace("개","EA");
                    hashMap.put(TAG_BAG1, bag1);
                    hashMap.put(TAG_BAG2, bag2);
                    //시분 이라는 단어 없에기
                    btime = btime.replace("시","");
                    btime = btime.replace(" ",":");
                    btime = btime.replace("분","");
                    hashMap.put(TAG_BTIME, btime);
                    //영어스타일로 도착공항 변경
                    if(addr1.startsWith("인천공항")){
                        addr1=addr1.replace("인천공항","Incheon Airport");
                        addr1=addr1.replace("터미널","Terminal");
                    }
                    else{
                        addr2=addr2.replace("인천공항","Incheon Airport");
                        addr2=addr2.replace("터미널","Terminal");
                    }
                }
                else {
                    //해시맵에 정보넣기
                    hashMap.put(TAG_BTIME, btime);
                    hashMap.put(TAG_BAG1, bag1);
                    hashMap.put(TAG_BAG2, bag2);
                }
                //배차 상태 업데이트
                if(number.startsWith("no1_")){
                    number = "배차중";
                }
                else if(number.contains("no2")){
                    number = "픽업중";
                }
                else if(number.startsWith("no3_")){
                    number = "픽업완료 및 배송중";
                }
                else if(number.startsWith("no4_")){
                    number = "배송완료";
                }
                else if(number.startsWith("no5_")){
                    number = "수령완료";
                } else if (number.contains("no6")){
                    number = "예약취소";
                }

                //해쉬맵에 정보넣기
                hashMap.put(TAG_VTIME, vtime);

                hashMap.put(TAG_NUMBER, number);
                hashMap.put(TAG_BDATE, bdate);
                hashMap.put(TAG_ADDR1, addr1);
                hashMap.put(TAG_VDATE, vdate);
                hashMap.put(TAG_ADDR2, addr2);
                hashMap.put(TAG_FLIGHT, flight);
                hashMap.put(TAG_TEL, tel);
                hashMap.put(TAG_FEE, fee);

                //리스트에 담기
                mArrayList.add(hashMap);

            }
            //리스트아답타생성과 연결
            ListAdapter adapter = new SimpleAdapter(
                    ReserveinquiryActivity.this, mArrayList, R.layout.item_list_reserve,
                    new String[]{TAG_VTIME,TAG_NUMBER,TAG_BDATE,TAG_ADDR1,TAG_VDATE,TAG_ADDR2,TAG_FLIGHT,TAG_BAG1,TAG_BAG2,TAG_TEL,TAG_FEE,TAG_BTIME},
                    new int[]{R.id.vtime_list, R.id.number_list, R.id.bdate_list,R.id.addr1_list,R.id.vdate_list,R.id.addr2_list,R.id.flight_list,R.id.bag1_list,R.id.bag2_list,R.id.tel_list,R.id.fee_list,R.id.btime_list}
                    );

            //아답타 설정
            mlistView.setAdapter(adapter);
            //리스트 아이템 클릭 이벤트
            mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        JSONObject jsonObject = new JSONObject(mJsonString);
                        JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                        JSONObject item = jsonArray.getJSONObject(i);
                        vno = item.getString(TAG_NO);
                        vnum = item.getString(TAG_NUMBER);
                        vnotice = item.getString(TAG_NOTICE);
                        datetime = item.getString(TAG_DATETIME);
                        String ss = getString(R.string.예약시간);
                        String ss2 = getString(R.string.기타);

                        // 기타 내용이 없을 시
                        if(vnotice == ""){
                            vnotice = "none";
                        }
                        //상태가 no4로 되었을때만 가방 수령다이얼로그가 뜸
                        if(vnum.startsWith("no1")){
                            Toast.makeText(getApplicationContext(),ss+datetime+"\n"+ss2+vnotice,Toast.LENGTH_LONG).show();
                        }
                        else if(vnum.startsWith("no2")){
                            Toast.makeText(getApplicationContext(),ss+datetime+"\n"+ss2+vnotice,Toast.LENGTH_LONG).show();
                        }
                        else if(vnum.startsWith("no3")){
                            Toast.makeText(getApplicationContext(),ss+datetime+"\n"+ss2+vnotice,Toast.LENGTH_LONG).show();
                        }
                        else if(vnum.startsWith("no5")){
                            Toast.makeText(getApplicationContext(),ss+datetime+"\n"+ss2+vnotice,Toast.LENGTH_LONG).show();
                        }
                        else {
                            //다이얼로그 생성
                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ReserveinquiryActivity.this);
                            alert_confirm.setMessage(R.string.가방을수령).setCancelable(false).setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //ok버튼을 누르면 수령완료로 상태 업데이트
                                            GetData1 task = new GetData1();
                                            String[] str = vnum.split("_");
                                            String token = FirebaseInstanceId.getInstance().getToken();
                                            task.execute("http://gimcarry.com/user_serial_update.php",vno,"no5_"+str[1], token);
                                            //메인으로 나감
                                            Intent intent4 = new Intent(getApplicationContext(),ManuActivity.class);
                                            startActivity(intent4);
                                            finish();
                                        }
                                    }).setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    });

                            AlertDialog alert = alert_confirm.create();
                            alert.show();

                        }
                    }
                    catch (JSONException e){

                    }



                    Object vo = (Object)adapterView.getAdapter().getItem(i);  //리스트뷰의 포지션 내용을 가져옴.

                    String itemid = vo.toString();


                }
            });


        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

    //전화번호 받아오기
    private String getPhoneNumber() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ReserveinquiryActivity.this, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "010";
        }

        String phoneNumber = "";

        if(phoneMgr.getLine1Number() == null){
            phoneNumber = "01000000000";

        }
        else{
            phoneNumber = phoneMgr.getLine1Number();;
        }

        return phoneNumber;
    }


    //퍼미션 요청.
    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(ReserveinquiryActivity.this, permission)){
            Toast.makeText(ReserveinquiryActivity.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(ReserveinquiryActivity.this, new String[]{permission},PERMISSION_REQUEST_CODE);
    }


    //퍼미션 체크.
    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(ReserveinquiryActivity.this, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }





    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ManuActivity.class);
        startActivity(intent);
        finish();
    }

}



