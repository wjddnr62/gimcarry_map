//package com.example.laon_dev01.gimcarry_map;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.telephony.PhoneNumberFormattingTextWatcher;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
///**
// * Created by laon-dev01 on 2018-04-10.
// */
//
//
//
//public class DetailInfoActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//    public String resultprice;
//    public String phonenumber;
//    public String phonenumbertv;
//    public String plusdata;
//    public String bag1num;
//    public String bag2num;
//    String TAG = "PhoneActivityTAG";
//    String wantPermission = Manifest.permission.READ_PHONE_STATE;
//    private static final int PERMISSION_REQUEST_CODE = 1;
//    public int count = 0;
//    //manager
//    TelephonyManager telephonyManager;
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.detailinfoactivity2);
//        Intent intent = getIntent();
//
//
//        final String placeall = intent.getStringExtra("placeall");
//        final String date1 = intent.getStringExtra("date1");
//        final String date2 = intent.getStringExtra("date2");
//        final String time2 = intent.getStringExtra("time2");
//        final String time1 = intent.getStringExtra("time1");
//        final String airname = intent.getStringExtra("airname");
//        final String airportkind = intent.getStringExtra("airportkind");
//
//
//
//
//
//        EditText phonenum = findViewById(R.id.phonenumber);
//        EditText plus = findViewById(R.id.plusanything);
////        String myPhoneNumber = getPhoneNumber();
//
//        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        if (!checkPermission(wantPermission)) {
//            requestPermission(wantPermission);
//        } else {
//            Log.d(TAG, "Phone number: " + getPhoneNumber() );
//            phonenum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
//            phonenumber=getPhoneNumber();
//            phonenum.setText(phonenumber);
//            plus.requestFocus();
//            plus.setCursorVisible(true);
//        }
//
//    Button.OnClickListener onClickListener = new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                EditText plusques = findViewById(R.id.plusanything);
//                //EditText phonenumed = findViewById(R.id.phonenumber);
//                plusdata = plusques.getText().toString();
//                //phonenumbertv = phonenumed.getText().toString();
//                if (phonenumber.equals("")) {
//                    count = 0;
//                } else {
//                    count = 1;
//                }
//                switch (view.getId()) {
//                    case R.id.reservebtn:
//                        if (count == 1) {
//                            InsertData task = new InsertData();
//                            task.execute(date1, time1, date2, time2, placeall, airportkind, airname, bag1num, bag2num, plusdata, "123-24", "이미지1", "이미지2", "김운전","010-1231-1231");
//                            Intent intent = new Intent(getApplicationContext(), ManuActivity.class);
//                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            } else {
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            }
//                            startActivity(intent);
//                             finish();
//                            Toast.makeText(getApplicationContext(), "예약 완료 되었습니다" + plusdata, Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "입력되지 않은 항목이 있습니다.", Toast.LENGTH_LONG).show();
//                        }
//                        break;
//                    case R.id.backbtn2:
//                        finish();
//                        break;
//                }
//            }
//        };
//
//        Button btn1 = (Button) findViewById(R.id.reservebtn);
//        btn1.setOnClickListener(onClickListener);
//
//        Button btn2 = (Button) findViewById(R.id.backbtn2);
//        btn2.setOnClickListener(onClickListener);
//
//
//        Spinner spinner1 = findViewById(R.id.spinnermount1);
//        Spinner spinner2 = findViewById(R.id.spinnermount2);
//        phonenum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers2, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.numbers2, android.R.layout.simple_spinner_item);
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner1.setAdapter(adapter);
//        spinner1.setOnItemSelectedListener(this);
//        spinner2.setAdapter(adapter2);
//        spinner2.setOnItemSelectedListener(this);
//
//
//    }
//
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Spinner spinner1 = findViewById(R.id.spinnermount1);
//        Spinner spinner2 = findViewById(R.id.spinnermount2);
//        TextView tv1 = (TextView) findViewById(R.id.mount1);
//        TextView tv2 = (TextView) findViewById(R.id.mount2);
//        TextView price = (TextView) findViewById(R.id.price);
//        Integer sum = 0;
//        Integer sum2 = 0;
//        Integer result;
//
//        if (spinner2.getSelectedItem().equals("1")) {
//            tv2.setText("1");
//            bag2num = "1개";
//            sum = 20000;
//        } else if (spinner2.getSelectedItem().equals("2")) {
//            tv2.setText("2");
//            bag2num = "2개";
//            sum = 40000;
//        } else if (spinner2.getSelectedItem().equals("3")) {
//            tv2.setText("3");
//            sum = 60000;
//            bag2num = "3개";
//        } else if (spinner2.getSelectedItem().equals("4")) {
//            tv2.setText("4");
//            sum = 80000;
//            bag2num = "4개";
//        } else if (spinner2.getSelectedItem().equals("5")) {
//            tv2.setText("5");
//            sum = 100000;
//            bag2num = "5개";
//        }
//
//        if (spinner1.getSelectedItem().equals("1")) {
//            tv1.setText("1");
//            bag1num = "1개";
//            sum2 = 30000;
//        } else if (spinner1.getSelectedItem().equals("2")) {
//            tv1.setText("2");
//            sum2 = 60000;
//            bag1num = "2개";
//        } else if (spinner1.getSelectedItem().equals("3")) {
//            tv1.setText("3");
//            sum2 = 90000;
//            bag1num = "3개";
//        } else if (spinner1.getSelectedItem().equals("4")) {
//            tv1.setText("4");
//            sum2 = 120000;
//            bag1num = "4개";
//        } else if (spinner1.getSelectedItem().equals("5")) {
//            tv1.setText("5");
//            sum2 = 150000;
//            bag1num = "5개";
//        }
//        result = sum + sum2;
//        resultprice = result.toString();
//        price.setText(result.toString());
//
//    }
//
//    public void onItemSelected2(AdapterView<?> adapterView2, View view, int i, long l) {
//        String text = adapterView2.getItemAtPosition(i).toString();
//        TextView tv1 = (TextView) findViewById(R.id.mount1);
//        tv1.setText(text);
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
//
//    public void onBackPressed() {
//
//        finish();
//    }
//
//    class InsertData extends AsyncTask<String, Void, String> {
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = ProgressDialog.show(DetailInfoActivity2.this,
//                    "Please Wait", null, true, true);
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            progressDialog.dismiss();
//
//        }
//
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            String date1 = (String) params[0];
//            String time1 = (String) params[1];
//            String date2 = (String) params[2];
//            String time2 = (String) params[3];
//            String startplace = (String) params[4];
//            String airportkind = (String) params[5];
//            String airname = (String) params[6];
//            String bag1 = (String) params[7];
//            String bag2 = (String) params[8];
//            String plusplus = (String) params[9];
//            String number = (String) params[10];
//            String img1 = (String) params[11];
//            String img2 = (String) params[12];
//            String driver = (String) params[13];
//            String tel = (String) params[14];
//            String serverURL = "http://108.160.128.68/insert.php";
//            String postParameters = "date1=" + date1 + "&time1=" + time1 + "&date2=" + date2 + "&time2=" + time2 + "&startplace=" + startplace + "&airportkind=" + airportkind + "&airname=" + airname + "&bag1=" + bag1 + "&bag2=" + bag2 + "&plusplus=" + plusplus + "&number=" + number + "&img1=" + img1 + "&img2=" + img2 + "&driver=" + driver+"&tel="+tel;
//            try {
//
//                URL url = new URL(serverURL);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                httpURLConnection.setReadTimeout(5000);
//                httpURLConnection.setConnectTimeout(5000);
//                httpURLConnection.setRequestMethod("POST");
//                //httpURLConnection.setRequestProperty("content-type", "application/json");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.connect();
//
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters.getBytes("UTF-8"));
//                outputStream.flush();
//                outputStream.close();
//
//
//                int responseStatusCode = httpURLConnection.getResponseCode();
//
//
//                InputStream inputStream;
//                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpURLConnection.getInputStream();
//                } else {
//                    inputStream = httpURLConnection.getErrorStream();
//                }
//
//
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line);
//                }
//                bufferedReader.close();
//                return sb.toString();
//            } catch (Exception e) {
//                return new String("Error: " + e.getMessage());
//            }
//
//        }
//    }
//
//    private String getPhoneNumber() {
//        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(DetailInfoActivity2.this, wantPermission) != PackageManager.PERMISSION_GRANTED) {
//            return "";
//        }
//        return phoneMgr.getLine1Number();
//    }
//
//
//    private void requestPermission(String permission){
//        if (ActivityCompat.shouldShowRequestPermissionRationale(DetailInfoActivity2.this, permission)){
//            Toast.makeText(DetailInfoActivity2.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
//        }
//        ActivityCompat.requestPermissions(DetailInfoActivity2.this, new String[]{permission},PERMISSION_REQUEST_CODE);
//    }
//
//
//    private boolean checkPermission(String permission){
//        if (Build.VERSION.SDK_INT >= 23) {
//            int result = ContextCompat.checkSelfPermission(DetailInfoActivity2.this, permission);
//            if (result == PackageManager.PERMISSION_GRANTED){
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return true;
//        }
//    }
//
//}
//
package com.laonstory.laon_dev01.gimcarry_map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by laon-dev01 on 2018-04-10.
 */

public class DetailInfoActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //토탈가격
    public String resultprice;
    //전화번호
    public String phonenumber = "01000000000";
    //요청사항
    public String plusdata;
    //일반가방
    public String bag1num;
    //규격외 가방
    public String bag2num;
    //비밀번호
    public String password;
    //입력매니저
    InputMethodManager imm;
    public int count = 0;
    //전화퍼미션

    String wantPermission = Manifest.permission.READ_PHONE_STATE;

    //전화매니저
    TelephonyManager telephonyManager;

    //퍼미션 코드
    private static final int PERMISSION_REQUEST_CODE = 1;

    private WebView webView;
    String return_result = "";
    private Handler handler;

    private String APP_SCHEME = "gimcarry_pay";

    private boolean isChecked = true;
    private Thread thread;

    private String placeall;
    private String date1;
    private String date2;
    private String time2;
    private String time1;
    private String airname;
    private String airportkind;

    private int result_pay = 0;

    private int day_check = 1;
    private int day_check2 = 1;

    private TextView tv1;
    private TextView tv2;

    private int sum1 = 0;  // 규격내
    private int sum2 = 0;  // 규격외

    private String metro_area = "";
    private String province_area = "";

    private String token = "";

    private Reservation_popup reservation_popup;

    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailinfoactivity2);

        FirebaseApp.initializeApp(this);
        token = FirebaseInstanceId.getInstance().getToken();

        reservation_popup = new Reservation_popup(this);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        WindowManager.LayoutParams wm = reservation_popup.getWindow().getAttributes();
        wm.copyFrom(reservation_popup.getWindow().getAttributes());
        wm.width = width;
        wm.height = (int) (height / 4.5);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new InicisWebViewClient(this, DetailInfoActivity2.this));


        //인텐트 받아오기
        Intent intent = getIntent();

        day_check = intent.getIntExtra("now_day", 1);
        day_check2 = intent.getIntExtra("now_day2", 1);
        //주소데이터 받아오기
        placeall = intent.getStringExtra("placeall");
        //탑승일 받아오기
        date1 = intent.getStringExtra("date1");
        //방문수거일 받아오기
        date2 = intent.getStringExtra("date2");
        // 방문수거시간 받아오기
        time2 = intent.getStringExtra("time2");
        // 탑승시간 받아오기
        time1 = intent.getStringExtra("time1");
        // 항공편 받아오기
        airname = intent.getStringExtra("airname");
        // 도착공항 받아오기
        airportkind = intent.getStringExtra("airportkind");

        // 전화번호입력
        EditText phonenum = findViewById(R.id.phonenumber);
        final EditText plus = findViewById(R.id.plusanything);

        String myPhoneNumber = getPhoneNumber();

        // 비밀번호입력
        EditText passnum = findViewById(R.id.password);

        // 퍼미션 체크
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        // 퍼미션 허용안햇을떄
        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {

            // 전화번호 받아오기
            String country = getResources().getString(R.string.전화번호국적);

            phonenumber = getPhoneNumber();

//            if(phonenumber == null){
//                phonenumber = "000";
//            }else{
//                phonenumber = "999";
//            }

            if (phonenumber.startsWith("+82")) {
                phonenumber = phonenumber.replace("+82", "0");
//                phonenum.setText(phonenumber);
            } else if (phonenumber.startsWith("82")) {
                phonenumber = phonenumber.replace("82", "0");
//                phonenum.setText(phonenumber);
            } else if (phonenumber.startsWith("010")) {
//                phonenumber = phonenumber;
            } else {
//                phonenumber = "01000000003";
            }

            //전화번호 에딧텍스트에 받아온번호 기입
            phonenum.setText(phonenumber);

            //비밀번호칸으로 포커스 이동
            passnum.requestFocus();
            passnum.setCursorVisible(true);
        }

        //전체 화면 받아오기
        LinearLayout rl = (LinearLayout) findViewById(R.id.linear2);
        //화면클릭시
        rl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //키보드 내리기
                EditText et1 = (EditText) findViewById(R.id.plusanything);
// TODO Auto-generated method stub
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
            }
        });

        //버튼온클릭
        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                //요청사항
                EditText plusques = findViewById(R.id.plusanything);

                //전화번호
                EditText phonenum = findViewById(R.id.phonenumber);

                //비밀번호
                EditText pass = findViewById(R.id.password);

                //에딧텍스트값 받아오기
                password = pass.getText().toString();
                phonenumber = phonenum.getText().toString();
                plusdata = plusques.getText().toString();

                //프래그 설정
                boolean numflag = false;

                //모든사항입력했는지 비교
                //일반가방하고 규격외가방 비교
                if (!bag1num.equals("0개") || !bag2num.equals("0개")) {
                    numflag = true;
                }
                //전화번호랑 비밀번호 비교
                if (phonenumber.equals("") || password.equals("") || numflag == false) {
                    count = 0;
                } else {
                    count = 1;
                }


                //클릭 리스너
                switch (view.getId()) {
                    //예약하기기버튼
                    case R.id.reservebtn:
                        //모든정보를 입력했을때
                        if (count == 1) {
                            reservation_popup.show();
                            //결제 연동
                            reservation_popup.setReservation_listener(new Reservation_listener() {
                                @Override
                                public void onReservationClick(int status) {
                                    if (status == 0) {
                                        String parameter = "";
                                        try {
                                            if (placeall.contains("서울") || placeall.contains("경기") || placeall.contains("인천")) {
                                                parameter = "amount=" + URLEncoder.encode(metro_area, "UTF-8") + "&buyer_tel=" + URLEncoder.encode(phonenumber, "UTF-8");
                                            } else {
                                                parameter = "amount=" + URLEncoder.encode(province_area, "UTF-8") + "&buyer_tel=" + URLEncoder.encode(phonenumber, "UTF-8");
                                            }


                                            Intent intent = getIntent();
                                            Uri intentData = intent.getData();

                                            if (intentData == null) {
                                                webView.addJavascriptInterface(new pay_check(), "pay_check");
                                                webView.postUrl("http://gimcarry.com/pay_modul/pay_modul.php", parameter.getBytes());
                                                webView.setVisibility(View.VISIBLE);

                                                Log.e("go_url", ".");

                                                Log.e("return_result", return_result + ".");

                                            } else {
                                                String url = intentData.toString();
                                                if (url.startsWith(APP_SCHEME)) {
                                                    // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
                                                    String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
                                                    Log.e("url", redirectURL);
                                                    webView.loadUrl(redirectURL);
                                                }
                                            }

                                            Log.e("return_result", return_result);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    } else if (status == 1) {
//                                        Toast.makeText(DetailInfoActivity2.this, "준비 중 입니다.", Toast.LENGTH_SHORT).show();
                                        String parameter = "";
                                        try {
                                            if (placeall.contains("서울") || placeall.contains("경기") || placeall.contains("인천")){
                                                parameter = "amount=" + URLEncoder.encode(metro_area, "UTF-8") + "&buyer_tel=" + URLEncoder.encode(phonenumber, "UTF-8");
                                            } else {
                                                parameter = "amount=" + URLEncoder.encode(province_area, "UTF-8") + "&buyer_tel=" + URLEncoder.encode(phonenumber, "UTF-8");
                                            }



                                            Intent intent = getIntent();
                                            Uri intentData = intent.getData();

                                            if (intentData == null) {
                                                webView.addJavascriptInterface(new pay_check(), "pay_check");
                                                webView.postUrl("http://gimcarry.com/pay_modul/pay_modul_phone.php", parameter.getBytes());
                                                webView.setVisibility(View.VISIBLE);

                                                Log.e("go_url", ".");

                                                Log.e("return_result", return_result + ".");

                                            } else {
                                                String url = intentData.toString();
                                                if (url.startsWith(APP_SCHEME)) {
                                                    // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
                                                    String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
                                                    Log.e("url", redirectURL);
                                                    webView.loadUrl(redirectURL);
                                                }
                                            }

                                            Log.e("return_result", return_result);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    } else if (status == 2) {
                                        plusdata += "\n현장결제";
                                        pay_com();
//                                        Toast.makeText(DetailInfoActivity2.this, "토스트!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onNotClick() {

                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), R.string.입력되지않은항목, Toast.LENGTH_LONG).show();
                        }
                        break;
                    //뒤로가기버튼
                    case R.id.backbtn2:
                        finish();
                        break;
                }
            }
        };

        //리스너셋팅
        Button btn1 = (Button) findViewById(R.id.reservebtn);
        btn1.setOnClickListener(onClickListener);

        Button btn2 = (Button) findViewById(R.id.backbtn2);
        btn2.setOnClickListener(onClickListener);


        //스피너셋팅
        Spinner spinner1 = findViewById(R.id.spinnermount1);
        Spinner spinner2 = findViewById(R.id.spinnermount2);


        //스피너에 아이템넣고 셋팅하기
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.numbers2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }

    public void pay_com() {
        //db 데이터등록
        InsertData task = new InsertData();
        if (placeall.contains("서울") || placeall.contains("인천") || placeall.contains("경기")) {
            task.execute(date1, time1, date2, time2, placeall, airportkind, airname, bag1num, bag2num, plusdata, "no1_00000", "이미지1", "이미지2", "NONE", phonenumber, metro_area, password, token);
        } else {
            task.execute(date1, time1, date2, time2, placeall, airportkind, airname, bag1num, bag2num, plusdata, "no1_00000", "이미지1", "이미지2", "NONE", phonenumber, province_area, password, token);
        }

        Intent intent = new Intent(getApplicationContext(), ManuActivity.class);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(), R.string.예약완료되었습니다, Toast.LENGTH_SHORT).show();
    }

    class pay_check {

        @android.webkit.JavascriptInterface
        public void pay_check(final String pay_check) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    return_result = pay_check;
                    Log.e("pay_check", pay_check + ".");
                    Toast.makeText(DetailInfoActivity2.this, pay_check, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        String url = intent.toString();

        if (url.startsWith(APP_SCHEME)) {
            // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
            String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
            Log.e("url", redirectURL + ".");
            webView.loadUrl(redirectURL);
        }
    }


    @Override
    //스피너 선택 이벤트
    // ---------------------------------------------------------------------------------------------
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner1 = findViewById(R.id.spinnermount1);
        Spinner spinner2 = findViewById(R.id.spinnermount2);

        tv1 = (TextView) findViewById(R.id.mount1);
        tv2 = (TextView) findViewById(R.id.mount2);

        TextView price = (TextView) findViewById(R.id.price);


        Integer result;

//        if (placeall.contains("서울") || placeall.contains("인천") || placeall.contains("경기")){
        if (day_check == 0 && day_check2 == 0) {
            if (spinner1.getSelectedItem().equals("0")) {
                tv1.setText(String.valueOf(0));
                bag1num = 0 + "개";
                sum1 = 0;
            } else if (spinner1.getSelectedItem().equals("1")) {
                tv1.setText(String.valueOf(1));
                bag1num = 1 + "개";
                sum1 = 20000;
            } else if (spinner1.getSelectedItem().equals("2")) {
                tv1.setText(String.valueOf(2));
                bag1num = 2 + "개";
                sum1 = 20000;
                sum1 += 10000;
            } else if (spinner1.getSelectedItem().equals("3")) {
                tv1.setText(String.valueOf(3));
                bag1num = 3 + "개";
                sum1 = 20000;
                sum1 += 10000 * 2;
            } else if (spinner1.getSelectedItem().equals("4")) {
                tv1.setText(String.valueOf(4));
                bag1num = 4 + "개";
                sum1 = 20000;
                sum1 += 10000 * 3;
            } else if (spinner1.getSelectedItem().equals("5")) {
                tv1.setText(String.valueOf(5));
                bag1num = 5 + "개";
                sum1 = 20000;
                sum1 += 10000 * 4;
            } else if (spinner1.getSelectedItem().equals("6")) {
                tv1.setText(String.valueOf(6));
                bag1num = 6 + "개";
                sum1 = 20000;
                sum1 += 10000 * 5;
            } else if (spinner1.getSelectedItem().equals("7")) {
                tv1.setText(String.valueOf(7));
                bag1num = 7 + "개";
                sum1 = 20000;
                sum1 += 10000 * 6;
            } else if (spinner1.getSelectedItem().equals("8")) {
                tv1.setText(String.valueOf(8));
                bag1num = 8 + "개";
                sum1 = 20000;
                sum1 += 10000 * 7;
            } else if (spinner1.getSelectedItem().equals("9")) {
                tv1.setText(String.valueOf(9));
                bag1num = 9 + "개";
                sum1 = 20000;
                sum1 += 10000 * 8;
            } else if (spinner1.getSelectedItem().equals("10")) {
                tv1.setText(String.valueOf(10));
                bag1num = 10 + "개";
                sum1 = 20000;
                sum1 += 10000 * 9;
            }

            if (spinner2.getSelectedItem().equals("0")) {
                tv2.setText(String.valueOf(0));
                bag2num = 0 + "개";
                sum2 = 0;
            } else if (spinner2.getSelectedItem().equals("1")) {
                tv2.setText(String.valueOf(1));
                bag2num = 1 + "개";
                sum2 = 30000;
            } else if (spinner2.getSelectedItem().equals("2")) {
                tv2.setText(String.valueOf(2));
                bag2num = 2 + "개";
                sum2 = 30000;
                sum2 *= 2;
            } else if (spinner2.getSelectedItem().equals("3")) {
                tv2.setText(String.valueOf(3));
                bag2num = 3 + "개";
                sum2 = 30000;
                sum2 *= 3;
            } else if (spinner2.getSelectedItem().equals("4")) {
                tv2.setText(String.valueOf(4));
                bag2num = 4 + "개";
                sum2 = 30000;
                sum2 *= 4;
            } else if (spinner2.getSelectedItem().equals("5")) {
                tv2.setText(String.valueOf(5));
                bag2num = 5 + "개";
                sum2 = 30000;
                sum2 *= 5;
            } else if (spinner2.getSelectedItem().equals("6")) {
                tv2.setText(String.valueOf(6));
                bag2num = 6 + "개";
                sum2 = 30000;
                sum2 *= 6;
            } else if (spinner2.getSelectedItem().equals("7")) {
                tv2.setText(String.valueOf(7));
                bag2num = 7 + "개";
                sum2 = 30000;
                sum2 *= 7;
            } else if (spinner2.getSelectedItem().equals("8")) {
                tv2.setText(String.valueOf(8));
                bag2num = 8 + "개";
                sum2 = 30000;
                sum2 *= 8;
            } else if (spinner2.getSelectedItem().equals("9")) {
                tv2.setText(String.valueOf(9));
                bag2num = 9 + "개";
                sum2 = 30000;
                sum2 *= 9;
            } else if (spinner2.getSelectedItem().equals("10")) {
                tv2.setText(String.valueOf(10));
                bag2num = 10 + "개";
                sum2 = 30000;
                sum2 *= 10;
            }
        } else {
            if (spinner1.getSelectedItem().equals("0")) {
                tv1.setText(String.valueOf(0));
                bag1num = 0 + "개";
                sum1 = 0;
            } else if (spinner1.getSelectedItem().equals("1")) {
                tv1.setText(String.valueOf(1));
                bag1num = 1 + "개";
                sum1 = 15000;
            } else if (spinner1.getSelectedItem().equals("2")) {
                tv1.setText(String.valueOf(2));
                bag1num = 2 + "개";
                sum1 = 15000;
                sum1 += 10000;
            } else if (spinner1.getSelectedItem().equals("3")) {
                tv1.setText(String.valueOf(3));
                bag1num = 3 + "개";
                sum1 = 15000;
                sum1 += 10000 * 2;
            } else if (spinner1.getSelectedItem().equals("4")) {
                tv1.setText(String.valueOf(4));
                bag1num = 4 + "개";
                sum1 = 15000;
                sum1 += 10000 * 3;
            } else if (spinner1.getSelectedItem().equals("5")) {
                tv1.setText(String.valueOf(5));
                bag1num = 5 + "개";
                sum1 = 15000;
                sum1 += 10000 * 4;
            } else if (spinner1.getSelectedItem().equals("6")) {
                tv1.setText(String.valueOf(6));
                bag1num = 6 + "개";
                sum1 = 15000;
                sum1 += 10000 * 5;
            } else if (spinner1.getSelectedItem().equals("7")) {
                tv1.setText(String.valueOf(7));
                bag1num = 7 + "개";
                sum1 = 15000;
                sum1 += 10000 * 6;
            } else if (spinner1.getSelectedItem().equals("8")) {
                tv1.setText(String.valueOf(8));
                bag1num = 8 + "개";
                sum1 = 15000;
                sum1 += 10000 * 7;
            } else if (spinner1.getSelectedItem().equals("9")) {
                tv1.setText(String.valueOf(9));
                bag1num = 9 + "개";
                sum1 = 15000;
                sum1 += 10000 * 8;
            } else if (spinner1.getSelectedItem().equals("10")) {
                tv1.setText(String.valueOf(10));
                bag1num = 10 + "개";
                sum1 = 15000;
                sum1 += 10000 * 9;
            }

            if (spinner2.getSelectedItem().equals("0")) {
                tv2.setText(String.valueOf(0));
                bag2num = 0 + "개";
                sum2 = 0;
            } else if (spinner2.getSelectedItem().equals("1")) {
                tv2.setText(String.valueOf(1));
                bag2num = 1 + "개";
                sum2 = 25000;
            } else if (spinner2.getSelectedItem().equals("2")) {
                tv2.setText(String.valueOf(2));
                bag2num = 2 + "개";
                sum2 = 25000;
                sum2 *= 2;
            } else if (spinner2.getSelectedItem().equals("3")) {
                tv2.setText(String.valueOf(3));
                bag2num = 3 + "개";
                sum2 = 25000;
                sum2 *= 3;
            } else if (spinner2.getSelectedItem().equals("4")) {
                tv2.setText(String.valueOf(4));
                bag2num = 4 + "개";
                sum2 = 25000;
                sum2 *= 4;
            } else if (spinner2.getSelectedItem().equals("5")) {
                tv2.setText(String.valueOf(5));
                bag2num = 5 + "개";
                sum2 = 25000;
                sum2 *= 5;
            } else if (spinner2.getSelectedItem().equals("6")) {
                tv2.setText(String.valueOf(6));
                bag2num = 6 + "개";
                sum2 = 25000;
                sum2 *= 6;
            } else if (spinner2.getSelectedItem().equals("7")) {
                tv2.setText(String.valueOf(7));
                bag2num = 7 + "개";
                sum2 = 25000;
                sum2 *= 7;
            } else if (spinner2.getSelectedItem().equals("8")) {
                tv2.setText(String.valueOf(8));
                bag2num = 8 + "개";
                sum2 = 25000;
                sum2 *= 8;
            } else if (spinner2.getSelectedItem().equals("9")) {
                tv2.setText(String.valueOf(9));
                bag2num = 9 + "개";
                sum2 = 25000;
                sum2 *= 9;
            } else if (spinner2.getSelectedItem().equals("10")) {
                tv2.setText(String.valueOf(10));
                bag2num = 10 + "개";
                sum2 = 25000;
                sum2 *= 10;
            }
        }
//        }
//        else {
//            if( spinner1.getSelectedItem().equals("0")){
//                tv1.setText(String.valueOf(0));
//                bag1num = 0 + "개";
//                sum1 = 0;
//            } else if (spinner1.getSelectedItem().equals("1")){
//                tv1.setText(String.valueOf(1));
//                bag1num = 1 + "개";
//                sum1 = 30000;
//            } else if (spinner1.getSelectedItem().equals("2")){
//                tv1.setText(String.valueOf(2));
//                bag1num = 2 + "개";
//                sum1 = 30000;
//                sum1 += 10000;
//            } else if (spinner1.getSelectedItem().equals("3")){
//                tv1.setText(String.valueOf(3));
//                bag1num = 3 + "개";
//                sum1 = 30000;
//                sum1 += 10000 * 2;
//            } else if (spinner1.getSelectedItem().equals("4")){
//                tv1.setText(String.valueOf(4));
//                bag1num = 4 + "개";
//                sum1 = 30000;
//                sum1 += 10000 * 3;
//            } else if (spinner1.getSelectedItem().equals("5")){
//                tv1.setText(String.valueOf(5));
//                bag1num = 5 + "개";
//                sum1 = 30000;
//                sum1 += 10000 * 4;
//            }
//
//            if (spinner2.getSelectedItem().equals("0")){
//                tv2.setText(String.valueOf(0));
//                bag2num = 0 + "개";
//                sum2 = 0;
//            } else if (spinner2.getSelectedItem().equals("1")){
//                tv2.setText(String.valueOf(1));
//                bag2num = 1 + "개";
//                sum2 = 50000;
//            } else if (spinner2.getSelectedItem().equals("2")){
//                tv2.setText(String.valueOf(2));
//                bag2num = 2 + "개";
//                sum2 = 50000;
//                sum2 *= 2;
//            } else if (spinner2.getSelectedItem().equals("3")){
//                tv2.setText(String.valueOf(3));
//                bag2num = 3 + "개";
//                sum2 = 50000;
//                sum2 *= 3;
//            }else if (spinner2.getSelectedItem().equals("4")){
//                tv2.setText(String.valueOf(4));
//                bag2num = 4 + "개";
//                sum2 = 50000;
//                sum2 *= 4;
//            }else if (spinner2.getSelectedItem().equals("2")){
//                tv2.setText(String.valueOf(2));
//                bag2num = 2 + "개";
//                sum2 = 50000;
//                sum2 *= 5;
//            }
//        }

//        // 규격 내 가방 계산
//        if (spinner1.getSelectedItem().equals("0")) {
//            tv1.setText("0");
//            sum1 = 0;
//            bag1num = "0개";
//        } else if (spinner1.getSelectedItem().equals("1")) {
//            tv1.setText("1");
//            sum1 = 15000;
//            bag1num = "1개";
//        } else if (spinner1.getSelectedItem().equals("2")) {
//            tv1.setText("2");
//            sum1 = 25000;
//            bag1num = "2개";
//        } else if (spinner1.getSelectedItem().equals("3")) {
//            tv1.setText("3");
//            sum1 = 35000;
//            bag1num = "3개";
//        } else if (spinner1.getSelectedItem().equals("4")) {
//            tv1.setText("4");
//            sum1 = 45000;
//            bag1num = "4개";
//        } else if (spinner1.getSelectedItem().equals("5")) {
//            tv1.setText("5");
//            sum1 = 55000;
//            bag1num = "5개";
//        }
//
//        // 규격 외 가방 계산
//        if (spinner2.getSelectedItem().equals("0")) {
//            tv2.setText("0");
//            sum2 = 0;
//            bag2num = "0개";
//        } else if (spinner2.getSelectedItem().equals("1")) {
//            tv2.setText("1");
//            sum2 = 25000;
//            bag2num = "1개";
//        } else if (spinner2.getSelectedItem().equals("2")) {
//            tv2.setText("2");
//            sum2 = 35000;
//            bag2num = "2개";
//        } else if (spinner2.getSelectedItem().equals("3")) {
//            tv2.setText("3");
//            sum2 = 45000;
//            bag2num = "3개";
//        } else if (spinner2.getSelectedItem().equals("4")) {
//            tv2.setText("4");
//            sum2 = 55000;
//            bag2num = "4개";
//        } else if (spinner2.getSelectedItem().equals("5")) {
//            tv2.setText("5~");
//            sum2 = 65000;
//            bag2num = "5개";
//        }


        // 규격내 + 규격외 가방 합산 금액
        result = sum1 + sum2;

        // 천 단위 콤마
        String r_sudo = String.format("%,d", result.intValue());
        String r_jibang = String.format("%,d", result.intValue() * 2);

        price.setText("수도권 : " + r_sudo + " / 지방 : " + r_jibang);
        metro_area = r_sudo;
        province_area = r_jibang;

        Integer sum1_1 = (sum1 + sum2) / 10000;
        Integer sum2_1 = 0;

        if (sum2 == 0) {
            sum2_1 = sum1_1 + sum1_1;
        } else {
            sum2_1 = (sum2 + sum2) / 10000 * 2;
        }


        resultprice = sum1_1.toString() + "~" + sum2_1.toString();

    }

    // ---------------------------------------------------------------------------------------------
    public void onItemSelected2(AdapterView<?> adapterView2, View view, int i, long l) {
        String text = adapterView2.getItemAtPosition(i).toString();
        TextView tv1 = (TextView) findViewById(R.id.mount1);
        tv1.setText(text);
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // ---------------------------------------------------------------------------------------------
    //뒤로가기
    public void onBackPressed() {
        if (webView.getVisibility() == View.VISIBLE) {
            webView.setVisibility(View.GONE);
        } else {
            finish();
        }
    }


    // ---------------------------------------------------------------------------------------------
    //데이터 인서트
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //다이얼로그 보여주기
            progressDialog = ProgressDialog.show(DetailInfoActivity2.this,
                    "Please Wait", null, true, true);
        }

        //다이얼로그 종료

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

        }

        protected void paymentIn(String... params) {

            String amount = (String) params[0];
            String buyer_tel = (String) params[1];

            String serverURLpay = "http://www.gimcarry.com/pay_modul/pay_modul.php";

            String PostParameter = amount + "&amount" + buyer_tel + "&buyer_tel";


        }


        @Override
        //post할 값
        protected String doInBackground(String... params) {
            String date1 = (String) params[0];
            String time1 = (String) params[1];
            String date2 = (String) params[2];
            String time2 = (String) params[3];
            String startplace = (String) params[4];
            String airportkind = (String) params[5];
            String airname = (String) params[6];
            String bag1 = (String) params[7];
            String bag2 = (String) params[8];
            String plusplus = (String) params[9];
            String number = (String) params[10];
            String img1 = (String) params[11];
            String img2 = (String) params[12];
            String driver = (String) params[13];
            String tel = (String) params[14];
            String fee = (String) params[15];
            String pass = (String) params[16];
            String token = (String) params[17];
            //php url 설정
            String serverURL = "http://gimcarry.com/insert.php";
            //post할 값 변수에 넣기
            String postParameters = "date1=" + date1 + "&time1=" + time1 + "&date2=" + date2 + "&time2=" + time2 + "&startplace=" + startplace + "&airportkind=" + airportkind + "&airname=" + airname + "&bag1=" + bag1 + "&bag2=" + bag2 + "&plusplus=" + plusplus + "&number=" + number + "&img1=" + img1 + "&img2=" + img2 + "&driver=" + driver + "&tel=" + tel + "&fee=" + fee + "&pass=" + pass + "&token=" + token;

            try {

                //url 연결
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //전송방식 설정
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                int responseStatusCode = httpURLConnection.getResponseCode();
                //전송
                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;


                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }

        }
    }

    // 전화번호받아오기
    private String getPhoneNumber() {

//        String phoneNumber = "";

        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(DetailInfoActivity2.this, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
//
//        try{
//            phoneNumber = phoneMgr.getLine1Number();
//        }catch(Exception e){
//            phoneNumber = "01000000002";
//        }

        String phoneNumber = "";

        if (phoneMgr.getLine1Number() == null) {
            phoneNumber = "01000000000";

        } else {
            phoneNumber = phoneMgr.getLine1Number();
            ;
        }

        return phoneNumber;
//        return phoneMgr.getLine1Number();

    }


    // 퍼미션 요청
    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DetailInfoActivity2.this, permission)) {
            Toast.makeText(DetailInfoActivity2.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(DetailInfoActivity2.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    //퍼미션 체크

    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(DetailInfoActivity2.this, permission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }


}





