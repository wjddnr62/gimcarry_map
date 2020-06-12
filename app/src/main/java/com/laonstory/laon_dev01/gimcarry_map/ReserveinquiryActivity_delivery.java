package com.laonstory.laon_dev01.gimcarry_map;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;

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


public class ReserveinquiryActivity_delivery extends AppCompatActivity {


    private View header;
    private static String TAG = "phptest_MainActivity";
    public String phonenumber;
    public String vno;
    public TextView nodata;
    public boolean flag = false;
    public String[] data1 = new String[100];
    public String search_result;
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
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    TelephonyManager telephonyManager;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public int count = 1;
    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveinquiryactivity_deli);
        Intent intent = getIntent();
        //운전코드 가져오기
        final String drivenum = intent.getStringExtra("number");
        nodata = (TextView)findViewById(R.id.nodata2);





        //온클릭 리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                //운송정보
                Button btn3 = (Button)findViewById(R.id.delibtn1);
                //운송중
                Button btn4 = (Button)findViewById(R.id.delibtn2);
                //운송완료
                Button btn5 = (Button)findViewById(R.id.delibtn3);
                switch (view.getId()) {
                    //뒤로가기버튼
                    case R.id.backbtn_reserve:
                        //메뉴로이동
                            Intent intent = new Intent(getApplicationContext(), ManuActivity.class);
                            startActivity(intent);
                            finish();
                        break;
                        //검색버튼(이건 수정해야함)
                    case R.id.search_btn:
                        AlertDialog.Builder ad = new AlertDialog.Builder(ReserveinquiryActivity_delivery.this);

                        ad.setTitle("기사전용");       // 제목 설정
                        ad.setMessage("검색할 주소를 입력해주세요");   // 내용 설정
                        // EditText 삽입하기
                        final EditText etsearch = new EditText(ReserveinquiryActivity_delivery.this);
                        etsearch.setWidth(500);
                        etsearch.setPadding(45,45,45,45);
                        ad.setView(etsearch);

                        // 확인 버튼 설정
                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                search_result = etsearch.getText().toString();
                                mlistView.setAdapter(null);
                                mArrayList.clear();
                                showResultserach();
                                // Text 값 받아서 로그 남기기
                                dialog.dismiss();     //닫기
                                // Event
                            }
                        });


                        // 취소 버튼 설정
                        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                                // Event
                            }
                        });

                        // 창 띄우기
                        ad.show();

                        break;

                        //운송정보
                    case R.id.delibtn1:
                        //리스트 초기화
                        mArrayList.clear();
                        //버튼 디자인
                        btn3.setBackgroundResource(R.drawable.bluegradation);
                        btn3.setTextColor(Color.WHITE);
                        btn4.setBackgroundResource(R.drawable.graygradation);
                        btn4.setTextColor(Color.parseColor("#5B5B5B"));
                        btn5.setBackgroundResource(R.drawable.graygradation);
                        btn5.setTextColor(Color.parseColor("#5B5B5B"));
                        count=1;
                        //모든정보 받아옴
                        GeDtata task = new GeDtata();
                        task.execute("http://gimcarry.com/select.php",drivenum, "");
                        break;
                        //운송중
                    case R.id.delibtn2:
                        //리스트 초기화
                        mArrayList.clear();
                        //버튼 디자인
                        btn4.setBackgroundResource(R.drawable.bluegradation);
                        btn4.setTextColor(Color.WHITE);
                        btn3.setBackgroundResource(R.drawable.graygradation);
                        btn3.setTextColor(Color.parseColor("#5B5B5B"));
                        btn5.setBackgroundResource(R.drawable.graygradation);
                        btn5.setTextColor(Color.parseColor("#5B5B5B"));
                        count=2;
                        //운송중인 정보만 받아옴
                        GeDtata task2 = new GeDtata();
                        task2.execute("http://gimcarry.com/select.php",drivenum, "");
                        break;

                        //운송완료
                    case R.id.delibtn3:
                        //리스트 초기화
                        mArrayList.clear();
                        //버튼 디자인
                        btn5.setBackgroundResource(R.drawable.bluegradation);
                        btn5.setTextColor(Color.WHITE);
                        btn4.setBackgroundResource(R.drawable.graygradation);
                        btn4.setTextColor(Color.parseColor("#5B5B5B"));
                        btn3.setBackgroundResource(R.drawable.graygradation);
                        btn3.setTextColor(Color.parseColor("#5B5B5B"));
                        count=3;
                        //운송완료정보만 받아옴
                        GeDtata task3 = new GeDtata();
                        task3.execute("http://gimcarry.com/select.php",drivenum, "");
                        break;
                }
            }
        };

        //리스너설정
        Button btn1 = (Button)findViewById(R.id.backbtn_reserve);
        btn1.setOnClickListener(onClickListener);
        Button btn2 = (Button)findViewById(R.id.search_btn);
        btn2.setOnClickListener(onClickListener);
        Button btn3 = (Button)findViewById(R.id.delibtn1);
        btn3.setOnClickListener(onClickListener);
        Button btn4 = (Button)findViewById(R.id.delibtn2);
        btn4.setOnClickListener(onClickListener);
        Button btn5 = (Button)findViewById(R.id.delibtn3);
        btn5.setOnClickListener(onClickListener);
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();
        //운송정보 받아옴
        GeDtata task = new GeDtata();
            task.execute("http://gimcarry.com/select.php",drivenum, "");
    }


    //운송에 관한 정보,중,완료
    private class GeDtata extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReserveinquiryActivity_delivery.this,
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
            String driver = (String)params[1];
            String postParameters = "driver=" + driver ;
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









    // 리스트에 데이터 뿌리는곳
    private void showResult(){
        header = getLayoutInflater().inflate(R.layout.item_list_reserve_delivery,null,false);
//        Button btn5 = (Button)header.findViewById(R.id.btnfinish);
//
//        btn5.setOnClickListener(new View.OnClickListener(){
//            TextView tv1 = (TextView)header.findViewById(R.id.addr1_list);
//            String st1 = tv1.getText().toString();
//            @Override
//            public void onClick(View v){
//                flag=true;
//                Toast.makeText(getApplicationContext(),st1, Toast.LENGTH_LONG).show();
//            }
//        });
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            if(jsonArray.length()==0){
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
                String no = item.getString(TAG_NO);

                HashMap<String,String> hashMap = new HashMap<>();

                data1[i]=number;
                //도착공항 미국과 한국 통일시켜서 보이기 위함
                if(addr1.startsWith("인천공항")){
                    if (addr2.startsWith("Hotel")){
                        String[] add = addr1.split(" ");
                        String addtv = add[1]+add[2];
                        hashMap.put(TAG_ADDR1, addtv);
                        String[] add2 = addr2.split(" ");
                        hashMap.put(TAG_ADDR2, add2[2]);
                    }
                    else {
                        String[] add = addr1.split(" ");
                        String addtv = add[1]+add[2];
                        hashMap.put(TAG_ADDR1, addtv);
                        String[] add2 = addr2.split(" ");
                        hashMap.put(TAG_ADDR2, add2[3]);
                    }

                }
                else {
                    if (addr1.startsWith("Hotel")){
                        String[] add = addr1.split(" ");
                        hashMap.put(TAG_ADDR1, add[2]);

                        String[] add2 = addr2.split(" ");
                        String add2tv = add2[1]+add2[2];
                        hashMap.put(TAG_ADDR2, add2tv);
                    }
                    else {
                        String[] add = addr1.split(" ");
                        hashMap.put(TAG_ADDR1, add[3]);

                        String[] add2 = addr2.split(" ");
                        String add2tv = add2[1]+add2[2];
                        hashMap.put(TAG_ADDR2, add2tv);
                    }

                }

                //상태나타내기위함
                if(number.equals("")){
                    number = "배차중";
                }
                int bagtotal = Integer.parseInt(bag1.replace("개",""))+Integer.parseInt(bag2.replace("개",""));

                hashMap.put(TAG_BAG1, String.valueOf(bagtotal));
                hashMap.put(TAG_FEE, fee);

                if(count==1){
                    mArrayList.add(hashMap);
                }
                else if(count==2){
                    if(number.equals("배차중")){
                        mArrayList.add(hashMap);
                    }
                }
                else if(count==3){
                    if(!number.equals("배차중")){
                        mArrayList.add(hashMap);
                    }

                }


            }

            //리스트 생성
            ListAdapter adapter = new SimpleAdapter(
                    ReserveinquiryActivity_delivery.this, mArrayList, R.layout.item_list_reserve_delivery,
                    new String[]{TAG_ADDR1,TAG_ADDR2,TAG_BAG1,TAG_FEE},
                    new int[]{R.id.addr1_listdeli,R.id.addr2_listdeli,R.id.bagtotal_list_deli,R.id.fee_listdeli}
            );

            mlistView.setAdapter(adapter);
            mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    try {
                        JSONObject jsonObject = new JSONObject(mJsonString);
                        JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                        JSONObject item = jsonArray.getJSONObject(i);
                        vno = item.getString(TAG_NO);
                        Intent intent = new Intent(getApplicationContext(),MoreinfoActivity.class);
                                               intent.putExtra("num",vno);
                        startActivity(intent);
                        finish();



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

    //검색부분(수정)
    private void showResultserach(){

        header = getLayoutInflater().inflate(R.layout.item_list_reserve_delivery,null,false);
//        Button btn5 = (Button)header.findViewById(R.id.btnfinish);
//
//        btn5.setOnClickListener(new View.OnClickListener(){
//            TextView tv1 = (TextView)header.findViewById(R.id.addr1_list);
//            String st1 = tv1.getText().toString();
//            @Override
//            public void onClick(View v){
//                flag=true;
//                Toast.makeText(getApplicationContext(),st1, Toast.LENGTH_LONG).show();
//            }
//        });
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
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
                String no = item.getString(TAG_NO);
                Toast.makeText(this, btime, Toast.LENGTH_SHORT).show();
                HashMap<String,String> hashMap = new HashMap<>();

                data1[i]=number;



                if(number.equals("")){
                    number = "배송준비중";
                }

            if(addr1.contains(search_result)) {
                hashMap.put(TAG_VTIME, vtime);
                hashMap.put(TAG_NUMBER, number);
                hashMap.put(TAG_BDATE, bdate);
                hashMap.put(TAG_ADDR1, addr1);
                hashMap.put(TAG_VDATE, vdate);
                hashMap.put(TAG_ADDR2, addr2);
                hashMap.put(TAG_FLIGHT, flight);
                hashMap.put(TAG_BAG1, bag1);
                hashMap.put(TAG_BAG2, bag2);
                hashMap.put(TAG_TEL, tel);
                hashMap.put(TAG_FEE, fee);
                hashMap.put(TAG_BTIME, btime);
                hashMap.put(TAG_NO,no);
                mArrayList.add(hashMap);
            }

            }

            ListAdapter adapter = new SimpleAdapter(
                    ReserveinquiryActivity_delivery.this, mArrayList, R.layout.item_list_reserve_delivery,
                    new String[]{TAG_ADDR1,TAG_ADDR2,TAG_BAG1,TAG_FEE},
                    new int[]{R.id.addr1_listdeli,R.id.addr2_listdeli,R.id.bag1_list,R.id.fee_list}
            );
            mlistView.setAdapter(adapter);



            mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        JSONObject jsonObject = new JSONObject(mJsonString);
                        JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                        JSONObject item = jsonArray.getJSONObject(i);
                        vno = item.getString(TAG_NO);


                        AlertDialog.Builder ad = new AlertDialog.Builder(ReserveinquiryActivity_delivery.this);

                        ad.setTitle("기사전용");       // 제목 설정
                        ad.setMessage("배달 일련번호를 입력해주세요");   // 내용 설정
// EditText 삽입하기
                        final EditText et = new EditText(ReserveinquiryActivity_delivery.this);
                        et.setWidth(500);
                        et.setPadding(45,45,45,45);
                        ad.setView(et);

// 확인 버튼 설정
                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = et.getText().toString();
                                String token = FirebaseInstanceId.getInstance().getToken();
                                GetData1 task = new GetData1();
                                task.execute("http://gimcarry.com/drv_serial_update.php",vno,value,token);
                                Toast.makeText(getApplicationContext(),vno+"    "+value,Toast.LENGTH_LONG).show();
                                Intent intent4 = new Intent(getApplicationContext(),ManuActivity.class);
                                startActivity(intent4);
                                finish();
                                // Text 값 받아서 로그 남기기
                                dialog.dismiss();     //닫기
                                // Event
                            }
                        });


// 취소 버튼 설정
                        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                                // Event
                            }
                        });

// 창 띄우기
                        ad.show();


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

    //기사 업데이트시 필요한거
    private class GetData1 extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReserveinquiryActivity_delivery.this,
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
            Log.e("test", "test");
            Log.e("params1", params[1]);
            Log.e("params2", params[2]);
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







    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ManuActivity.class);
        startActivity(intent);
        finish();
    }
}



