package com.laonstory.laon_dev01.gimcarry_map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public LatLng myLocationLatLng;
    public Location location;
    private GoogleMap googleMap;
    static final String TAG = "PlaceAutocomplete";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private GpsInfo gps;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Button.OnClickListener onClickListener = new Button.OnClickListener() {



            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    // 맵에서 마지막에 백버튼하면 액티비티 클리어
                    case R.id.backbtn:
                        Intent intent1 = new Intent(getApplicationContext(), ManuActivity.class);
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }
                        startActivity(intent1);
                        finish();
                        break;




                        //주소검색버튼
                    case R.id.testbtn:
                        String country = getResources().getString(R.string.전화번호국적);
                        //한국일때 다음주소api엑티비티로 넘어감
                        if(country.equals("KR")){
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (country.equals("ZH")){
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (country.equals("JA")){
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (country.equals("US")){
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        //아닐때 바로 detailinfoactivity로 넘어감
                        else {
                            Intent intent = new Intent(getApplicationContext(), DetailInfoActivity.class);
                            //호텔이름받음
                            intent.putExtra("hotel","Hotel Name:");
                            startActivity(intent);
                            finish();
                        }

//                        try {
//                            Intent intent =
//                                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                                            .build(MapsActivity.this);
//                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//                        } catch (GooglePlayServicesRepairableException e) {
//                            // TODO: Handle the error.
//                        } catch (GooglePlayServicesNotAvailableException e) {
//                            // TODO: Handle the error.
//                        }
                        break;
                }
            }
        };

        //온클릭리스너
        Button btntest = (Button) findViewById(R.id.testbtn);
        btntest.setOnClickListener(onClickListener);
        Button btn2 = (Button) findViewById(R.id.backbtn);
        btn2.setOnClickListener(onClickListener);
        //맵뷰에 지도띄우기
//        MapView mapview = (MapView) findViewById(R.id.map);
//        mapview.onCreate(savedInstanceState);
//        mapview.onResume();
//        mapview.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services /is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */












    //지도
    @Override
    public void onMapReady(GoogleMap googleMap) {


        //현재 위치받아옴
        gps = new GpsInfo(MapsActivity.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        String date1 = Double.toString(latitude);



        //우측 상단에 위치 버튼
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        //지도 위치 셋팅
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocationLatLng, 10));

    }




    //뒤로가기 버튼눌렀을때
    public void onBackPressed() {
        //메인메뉴로 감
        Intent intent = new Intent(getApplicationContext(), ManuActivity.class);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
        finish();
    }

}
