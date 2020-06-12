package com.laonstory.laon_dev01.gimcarry_map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by laon-dev01 on 2018-05-15.
 */

public class MoreinfoActivity extends AppCompatActivity {
    private static final String TAG_JSON = "reservation";
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
    private static final String TAG_DRIVER = "re_driver";
    private static final String TAG_DATETIME = "re_date";
    //qr code scanner object
    private IntentIntegrator qrScan;
    public String teltv = "";
    public String driver = "";
    public String num = "";
    String mJsonString;
    private TextView mTextViewResult;
    private TextView start_id, care_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreinfoactivity);

        start_id = findViewById(R.id.start_id);
        care_date = findViewById(R.id.care_date);

        Intent intent = getIntent();
        //번호 인텐트로 받아옴
        num = intent.getStringExtra("num");
        //번호를 php로보내서 번호상세정보 받아옴
        GetData task = new GetData();
        task.execute("http://gimcarry.com/selectinfo.php", num);

        //큐알코드스캔 선언
        qrScan = new IntentIntegrator(this);


        //온클릭리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    //닫기버튼
                    case R.id.closebtninfo:
                        //전체정보엑티비티로 넘어감
                        Intent intent = new Intent(getApplicationContext(), ReserveinquiryActivity_delivery.class);
                        //이전정보 다시보내기
                        intent.putExtra("number", driver);
                        startActivity(intent);
                        finish();
                        break;
                        //큐알코드버튼
                    case R.id.qrbtn:
                        //스캔하기
                        qrScan.setPrompt("스캔중..");
                        //qrScan.setOrientationLocked(false);
                        qrScan.initiateScan();
                        break;
                        //전화버튼
                    case R.id.telbtn:

                        //전화번호로 바로 전화걸기
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        intent2.setData(Uri.parse("tel:"+teltv));

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermi
                            // ssions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent2);



                        break;

                }
            }
        };

        //리스너 설정

        Button btn1 = (Button)findViewById(R.id.closebtninfo);
        btn1.setOnClickListener(onClickListener);
        Button btn2 = (Button)findViewById(R.id.qrbtn);
        btn2.setOnClickListener(onClickListener);
        Button tel = (Button)findViewById(R.id.telbtn);
        tel.setOnClickListener(onClickListener);
    }

    //데이터 post
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MoreinfoActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }





                mJsonString = result;
                //결과 가져오기
                showResult();

        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String no = (String)params[1];
            String postParameters = "no=" + no ;
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

                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){
        try {
            //json으로 정보담기
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            //json배열만큼 돌려서 값뽑아내기
            for(int i=0;i<jsonArray.length();i++){
                //컬럼을 item에 담아주기
                JSONObject item = jsonArray.getJSONObject(i);
                //정보받아와서 저장
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
                String notice = item.getString(TAG_NOTICE);
                String driver2 = item.getString(TAG_DRIVER);
                driver=driver2;
                teltv=tel;
                //배송상태 나타내기
                if(number.startsWith("no1_")){
                    number = "배차중"; // 앱에서 예약을 했을시
                }
                else if(number.contains("no2")){
                    number = "픽업중";  // 웹에서 배차완료 시
                }
                else if(number.startsWith("no3_")){
                    number = "픽업완료 및 배송중"; // QR코드 등록 시
                }
                else if(number.startsWith("no4_")){
                    number = "배송완료"; // 공항에서 웹으로 수령 시
                }
                else if(number.startsWith("no5_")){
                    number = "수령완료"; // 고객이 최종 캐리어 수령 시
                } else if (number.contains("no6")){
                    number = "예약취소";
                }

                //텍스트에 정보 업데이트
                TextView tv1 = (TextView)findViewById(R.id.no_info);
                tv1.setText(no);
                TextView tv2 = (TextView)findViewById(R.id.bdateinfo);
                tv2.setText(bdate);
                TextView tv3 = (TextView)findViewById(R.id.btimeinfo);
                tv3.setText(btime);
                TextView tv4 = (TextView)findViewById(R.id.vdate_info);
                tv4.setText(vdate);
                TextView tv5 = (TextView)findViewById(R.id.vtime_info);
                tv5.setText(vtime);
                TextView tv6 = (TextView)findViewById(R.id.addr1_info);
                tv6.setText(addr1);
                if (addr1.contains("공항")){
                    care_date.setText("도착일시");
                }
                TextView tv7 = (TextView)findViewById(R.id.addr2_info);
                tv7.setText(addr2);
                TextView tv8 = (TextView)findViewById(R.id.flight_info);
                tv8.setText(flight);
                TextView tv9 = (TextView)findViewById(R.id.bag1_info);
                tv9.setText(bag1);
                TextView tv10 = (TextView)findViewById(R.id.bag2_info);
                tv10.setText(bag2);
                TextView tv11 = (TextView)findViewById(R.id.notice_info);
                tv11.setText(notice);
                tv11.setMovementMethod(new ScrollingMovementMethod());
                TextView tv12 = (TextView)findViewById(R.id.tel_info);
                tv12.setText(tel);
                TextView tv13 = (TextView)findViewById(R.id.fee_info);
                tv13.setText(fee);
                TextView tv15 = (TextView)findViewById(R.id.numinfo);
                tv15.setText(number);
                int bagtotal = Integer.parseInt(bag1.replace("개",""))+Integer.parseInt(bag2.replace("개",""));
                TextView tv14 = (TextView)findViewById(R.id.totalbag_info);
                tv14.setText(String.valueOf(bagtotal)+"개");
            }
        } catch (JSONException e) {

        }

    }

    //뒤로가기 눌렀을때
    public void onBackPressed() {
        //이전으로 되돌아가기
        Intent intent = new Intent(getApplicationContext(), ReserveinquiryActivity_delivery.class);
        //이전정보 되돌리기
        intent.putExtra("number",driver);
        startActivity(intent);
        finish();
    }
    //큐알코드 결과값
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(MoreinfoActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(MoreinfoActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                TextView tv1 = (TextView)findViewById(R.id.no_info);
                String token = FirebaseInstanceId.getInstance().getToken();
                //qr코드 정보를 php로 보내 배송상태 업데이트
                GetData1 task = new GetData1();
                task.execute("http://gimcarry.com/drv_serial_update.php",tv1.getText().toString(),"no3_"+result.getContents());
                Intent intent = new Intent(getApplicationContext(),MoreinfoActivity.class);
                intent.putExtra("num",num);
                startActivity(intent);
                finish();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //큐알코드 php로 보내서 업데이트
    private class GetData1 extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MoreinfoActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }





                mJsonString = result;
                showResult();


        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String no = (String)params[1];
            String number = (String)params[2];
            String postParameters = "no="+no+"&number=" +number;

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


                errorString = e.toString();
                Toast.makeText(getApplicationContext(),errorString,Toast.LENGTH_LONG).show();

                return null;
            }

        }
    }

}
