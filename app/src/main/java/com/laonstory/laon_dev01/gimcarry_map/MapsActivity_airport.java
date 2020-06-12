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
import android.widget.TextView;


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

public class MapsActivity_airport extends FragmentActivity implements OnMapReadyCallback {

    public LatLng myLocationLatLng;
    public Location location;
    private GoogleMap googleMap;
    static final String TAG = "PlaceAutocomplete";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private GpsInfo gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_airport);


        Button.OnClickListener onClickListener = new Button.OnClickListener() {



            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.backbtn_air:
                        Intent intent1 = new Intent(getApplicationContext(), ManuActivity.class);
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }
                        startActivity(intent1);
                        finish();
                        break;




                    case R.id.testbtn_air:
                        String country = getResources().getString(R.string.전화번호국적);
                        if(country.equals("KR")) {
                            Intent intent = new Intent(getApplicationContext(), daummapapiActivity_air.class);
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
                        else {
                            Intent intent = new Intent(getApplicationContext(), DetailInfoActivity_air.class);
                            intent.putExtra("hotel","Hotel Name:");
                            startActivity(intent);
                            finish();
                        }
                        break;
                }
            }
        };

        Button btntest = (Button) findViewById(R.id.testbtn_air);
        btntest.setOnClickListener(onClickListener);
        Button btn2 = (Button) findViewById(R.id.backbtn_air);
        btn2.setOnClickListener(onClickListener);
//        MapView mapview = (MapView) findViewById(R.id.map_air);
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












    @Override
    public void onMapReady(GoogleMap googleMap) {


        gps = new GpsInfo(MapsActivity_airport.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        String date1 = Double.toString(latitude);



        //우측 상단에 위치 버튼
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocationLatLng, 10));

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlaceAutocomplete.getPlace(this, data);
//                Intent intent = new Intent(getApplicationContext(), DetailInfoActivity_air.class);
//                intent.putExtra("place", place.getAddress());
//                intent.putExtra("place2", place.getName());
//                startActivity(intent);
//                finish();
//                Log.i(TAG, "Place: " + place.getName());
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(this, data);
//                // TODO: Handle the error.
//                Log.i(TAG, status.getStatusMessage());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled operation.
//            }
//        }
//    }




//    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
//        if ( currentMarker != null ) currentMarker.remove();
//
//        if ( location != null) {
//            //현재위치의 위도 경도 가져옴
//            LatLng currentLocation = new LatLng( location.getLatitude(), location.getLongitude());
//
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(currentLocation);
//            markerOptions.title(markerTitle);
//            markerOptions.snippet(markerSnippet);
//            markerOptions.draggable(true);
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//            currentMarker = this.googleMap.addMarker(markerOptions);
//
//            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
//            return;
//        }
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(DEFAULT_LOCATION);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        currentMarker = this.googleMap.addMarker(markerOptions);
//
//        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));
//    }


    public void onBackPressed() {
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
