package com.laonstory.laon_dev01.gimcarry_map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by laon-dev01 on 2018-04-09.
 */

public class ManuActivity extends AppCompatActivity {

    String marketVersion, verSion;
    AlertDialog.Builder mDialog;
    private App_info_dialog app_info_dialog;
    private Button app_info_btn;
    private String storeVersion;
    private String deviceVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manuactivity);

        mDialog = new AlertDialog.Builder(this);

//        storeVersion = VersionChecker.getMarketVersion(getPackageName());
//        try{
//            deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Log.e("version : ", storeVersion + ", " + deviceVersion);
//
//        if (storeVersion.compareTo(deviceVersion) > 0) {
//            Log.e("compare : ", ".");
//        } else {
//            Log.e("compare else : ", ".");
//        }

        new UpdateCheck().execute();

        app_info_dialog = new App_info_dialog(this);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        WindowManager.LayoutParams wm = app_info_dialog.getWindow().getAttributes();
        wm.copyFrom(app_info_dialog.getWindow().getAttributes());
        wm.width = (int) (width / 1.5);
        wm.height = (int) (height / 2.5);

        app_info_btn = findViewById(R.id.app_info_btn);
        app_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_info_dialog.show();
            }
        });

        ImageView iv1 = (ImageView) findViewById(R.id.menubackground);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(R.drawable.menuback).into(iv1);

        int permissionCheck = ContextCompat.checkSelfPermission(ManuActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheck2 = ContextCompat.checkSelfPermission(ManuActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck3 = ContextCompat.checkSelfPermission(ManuActivity.this, Manifest.permission.READ_CONTACTS);
        int permissionCheck4 = ContextCompat.checkSelfPermission(ManuActivity.this, Manifest.permission.READ_PHONE_STATE);
        int permissionCheck5 = ContextCompat.checkSelfPermission(ManuActivity.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck == PackageManager.PERMISSION_DENIED || permissionCheck2 == PackageManager.PERMISSION_DENIED || permissionCheck3 == PackageManager.PERMISSION_DENIED || permissionCheck4 == PackageManager.PERMISSION_DENIED || permissionCheck5 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ManuActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE}, 0);
        }


//        //퍼미션 허용
//        PermissionListener permissionlistener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                moveTaskToBack(true);
//                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
//            }
//        };
//
//        //퍼미션 다이얼로그 띄우기
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//                .check();
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
//                .check();
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setPermissions(Manifest.permission.READ_PHONE_STATE)
//                .check();
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setPermissions(Manifest.permission.READ_CONTACTS)
//                .check();
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setPermissions(Manifest.permission.CALL_PHONE)
//                .check();


        //위치정보 켯는지 확인
        chkGpsService();
        //버튼온클릭
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //배송예약버튼
                    case R.id.choice1:
                        String country = getResources().getString(R.string.전화번호국적);
                        //한국일때 다음주소api엑티비티로 넘어감
                        if (country.equals("KR")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (country.equals("ZH")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (country.equals("JA")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (country.equals("US")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), DetailInfoActivity.class);
                            //호텔이름받음
                            intent.putExtra("hotel", "Hotel Name:");
                            startActivity(intent);
                            finish();
                        }
//                    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
//                    startActivity(intent);
//                    finish();
                        break;
                    //공항예약버튼
                    case R.id.choice2:
                        String country2 = getResources().getString(R.string.전화번호국적);
                        //한국일때 다음주소api엑티비티로 넘어감
                        if (country2.equals("KR")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity_air.class);
                            startActivity(intent);
                            finish();
                        } else if (country2.equals("ZH")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity_air.class);
                            startActivity(intent);
                            finish();
                        } else if (country2.equals("JA")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity_air.class);
                            startActivity(intent);
                            finish();
                        } else if (country2.equals("US")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity_air.class);
                            startActivity(intent);
                            finish();
                        }
                        //아닐때 바로 detailinfoactivity로 넘어감
                        else {
                            Intent intent = new Intent(getApplicationContext(), DetailInfoActivity_air.class);
                            //호텔이름받음
                            intent.putExtra("hotel", "Hotel Name:");
                            startActivity(intent);
                            finish();
                        }
//                    Intent intent2 = new Intent(getApplicationContext(),MapsActivity_airport.class);
//                    startActivity(intent2);
//                    finish();

                        break;
                    //예약조회버튼
                    case R.id.choice3:
                        Intent intent3 = new Intent(getApplicationContext(), ReserveinquiryActivity.class);

                        startActivity(intent3);
                        finish();
                        break;

                    //기사전용버튼
                    case R.id.delibtn:
                        //다이얼로그 생성
                        AlertDialog.Builder ad = new AlertDialog.Builder(ManuActivity.this);


                        //다이얼로그 제목과 메시지 설정

                        ad.setTitle("기사전용");       // 제목 설정
                        ad.setMessage("기사배정된 코드를 입력해주세요.");   // 내용 설정
                        // EditText 삽입하기
                        final EditText et = new EditText(ManuActivity.this);
                        et.setWidth(500);
                        et.setPadding(45, 45, 45, 45);
                        ad.setView(et);

                        // 확인 버튼 설정
                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //기사코드 인텐트로 보내기
                                String value = et.getText().toString();
                                Intent intent4 = new Intent(getApplicationContext(), ReserveinquiryActivity_delivery.class);
                                intent4.putExtra("number", value);
                                Log.e("value", value);
                                startActivity(intent4);
                                dialog.dismiss();     //닫기
                                finish();


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
                }
            }
        };

        //리스너 셋팅
        // ㅂㅐ송예약 버튼
        Button btn1 = (Button) findViewById(R.id.choice1);
        btn1.setOnClickListener(onClickListener);
        // 공항예약
        Button btn2 = (Button) findViewById(R.id.choice2);
        btn2.setOnClickListener(onClickListener);
        // 예약조회
        Button btn3 = (Button) findViewById(R.id.choice3);
        btn3.setOnClickListener(onClickListener);
        // 기사용 버튼
        Button btn4 = (Button) findViewById(R.id.delibtn);
        btn4.setOnClickListener(onClickListener);


    }


    //뒤로가기 버튼
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long backPressedTime = 0;
        long intervalTime = tempTime - backPressedTime;

        //종료할건지 다이얼로그 띄움
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ManuActivity.this);
        alert_confirm.setMessage(R.string.프로그램종료).setCancelable(false).setPositiveButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                });

        AlertDialog alert = alert_confirm.create();
        alert.show();
    }


    //퍼미션 요청결과
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "권한이 거부되었습니다. 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }


    //위치정보켯는지 확인
    private boolean chkGpsService() {
        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {
            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle(R.string.위치서비스);
            gsDialog.setMessage(R.string.무선네트워크);
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).create().show();
            return false;

        } else {
            return true;
        }
    }

    public class UpdateCheck extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName()).get();
//                Elements Version = doc.select(".content");
//
                Elements Version = doc.select(".htlgb").eq(6);
                Log.e("eve ", "." + Version.toString() + ", " + doc.toString());

                for (Element mElement : Version) {
                    return mElement.text().trim();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            PackageInfo pi = null;
            try {
                pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            verSion = pi.versionName;
            marketVersion = result;

//            Log.e("version : ", Integer.verSion + ", " + marketVersion);

            if (marketVersion.equals("1.0.0")) {
                mDialog.setMessage("업데이트 후 사용해주세요.")
                        .setCancelable(false)
                        .setPositiveButton("업데이트 바로가기",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        Intent marketLaunch = new Intent(
                                                Intent.ACTION_VIEW);
                                        marketLaunch.setData(Uri
                                                .parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                        startActivity(marketLaunch);
                                        finish();
                                    }
                                });
                AlertDialog alert = mDialog.create();
                alert.setTitle("안 내");
                alert.show();
            } else if (!marketVersion.equals("1.0.0")) {
                if ((Integer.parseInt(verSion) < Integer.parseInt(marketVersion))) { //!verSion.equals(marketVersion)
                    mDialog.setMessage("업데이트 후 사용해주세요.")
                            .setCancelable(false)
                            .setPositiveButton("업데이트 바로가기",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            Intent marketLaunch = new Intent(
                                                    Intent.ACTION_VIEW);
                                            marketLaunch.setData(Uri
                                                    .parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                            startActivity(marketLaunch);
                                            finish();
                                        }
                                    });
                    AlertDialog alert = mDialog.create();
                    alert.setTitle("안 내");
                    alert.show();
                }
            }

            super.onPostExecute(result);
        }
    }

}
