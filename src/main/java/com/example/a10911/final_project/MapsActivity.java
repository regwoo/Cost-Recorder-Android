package com.example.a10911.final_project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latitude ;
    double longtitude ;
    //public ArrayList<address> curaddress = new ArrayList<address>();
    Context context;
    double year0 ;
    double month0 ;
    double  day0  ;
    double addmoney ;
    public String laddre = "UnKnown";
    public String localtime;
    public double llati=0;
    public double lloti=0;
    static double pi = 3.1415926;
    public String addname = "Unknown" ;
    CalendarView cal1;
    PopupWindow popupWindow;
    //public ArrayList<Content> curaddress = new ArrayList<Content>();

    Databasehelper helper = new Databasehelper(this);

    public List<Marker> databaseMarkers = new ArrayList<>();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//initiate the UI
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        googleMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
//get the current location

        getlocation();
        LatLng latLng = new LatLng(llati,lloti);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11f));

//add markers in the database
        for (int i =0 ;i<helper.getall().size();i++)
        {

            if (helper.getall().get(i).latitude != 0){
                latLng = new LatLng(helper.getall().get(i).latitude,helper.getall().get(i).longitude);
                Marker marker =  mMap.addMarker(new MarkerOptions().position(latLng).title(helper.getall().get(i).describe + "  Money: " + String.valueOf(helper.getall().get(i).money)));
                databaseMarkers.add(marker);
            }

        }

//click the map and add a marker,then store it in the database
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
//add a new marker

                latitude  =latLng.latitude ;
                longtitude = latLng.longitude ;
                getaddress(latLng.latitude,latLng.longitude);
                final Marker clickmarker =  mMap.addMarker(new MarkerOptions().position(latLng));
                Intent returnintent=new Intent();
                returnintent.putExtra("latitude",String.valueOf(latitude));
                returnintent.putExtra("longitude",String.valueOf(longtitude));
                setResult(Activity.RESULT_OK,returnintent);
                finish();
                // clickmarker.setTitle(addname + "   Money:  " +String.valueOf(addmoney));
/*
//instantiate popup window
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.popview,null);
                cal1=customView.findViewById(R.id.calendarview);
             //   popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,true);
//display the popup window
           //    popupWindow.showAtLocation(findViewById(R.id.maps), Gravity.CENTER, 0, 0);
                year0 = 0;
// get the date
                cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {
                        year0 = year;
                        month0 =month;
                        day0 = dayOfMonth;
                    }
                });
                Button addb = (Button)customView.findViewById(R.id.addm);
                addb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText editText = (EditText)customView.findViewById(R.id.money);

                        if  (year0 != 0& editText.getText().toString() != null){
                            final Marker clickmarker =  mMap.addMarker(new MarkerOptions().position(latLng));
                            addmoney = Double.parseDouble(editText.getText().toString());
                            clickmarker.setTitle(addname + "   Money:  " +String.valueOf(addmoney));

                            localtime =String.valueOf( year0*10000 + month0*100 + day0) ;
                          //  helper.insertData(addname ,String.valueOf(clickmarker.getPosition().latitude), String.valueOf(clickmarker.getPosition().longitude),"0",localtime,String.valueOf(addmoney),"1");
                          // popupWindow.dismiss();
                        }
                    }
                });


                Button decb = (Button)customView.findViewById(R.id.decm);
                decb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText editText = (EditText)customView.findViewById(R.id.money);
                        if  (year0 != 0& editText.getText().toString() != null){
                            final Marker clickmarker =  mMap.addMarker(new MarkerOptions().position(latLng));

                            addmoney = Double.parseDouble(editText.getText().toString());
                            addmoney = addmoney * (-1);
                            clickmarker.setTitle(addname + "   Money:  " +String.valueOf(addmoney));

                            localtime =String.valueOf( year0*10000 + month0*100 + day0) ;
                            //helper.insertData(addname ,String.valueOf(clickmarker.getPosition().latitude), String.valueOf(clickmarker.getPosition().longitude),"0",localtime,String.valueOf(addmoney),"1");
                          //  popupWindow.dismiss();
                        }
                    }
                });
                */

            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestcode, @NonNull String[] permission, @NonNull int[]grantresult)
    {
        super.onRequestPermissionsResult(requestcode,permission,grantresult);
        switch(requestcode)
        {
            case REQUEST_LOCATION:
                getlocation();
                break;

        }
    }

    public void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }

        else{


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                return;
            }






            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location != null) {

                double lontitude = location.getLongitude();
                lloti = location.getLongitude();
                double latitude = location.getLatitude();
                llati = latitude;

                getaddress(latitude, lontitude);

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lontitude = location.getLongitude();
                        lloti = lontitude;
                        double latitude = location.getLatitude();
                        llati = latitude;

                        getaddress(latitude, lontitude);

                        LatLng latLng = new LatLng(llati,lloti);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11f));
/*
                        if(Looper.myLooper() == Looper.getMainLooper())
                        {
                            Toast.makeText(getApplicationContext(),"not main thread",Toast.LENGTH_SHORT).show();
                        }
                        */
                    }


                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, locationListener);
            } else {

            }
        }
    }

    public void getaddress(double latitude,double longitude)  {
        Geocoder geocoder;
        final List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if(addresses!=null) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                addname = knownName ;
                laddre = "Address:" +"\n"+address + "\nCity: " + city + "\nState: " + state + "\nCountry: " + country + "\nPostalCode: " + postalCode + "\nKnowname: " + knownName ;
            }
            else
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public double getDistance(double lati1,double longi1,double lati2,double longi2)
    {
        double subangle,distance;
        lati1=(lati1/180)*pi;
        longi1=(longi1/180)*pi;
        lati2=(lati2/180)*pi;
        longi2=(longi2/180)*pi;
        double F=pow(cos(lati1)-cos(lati2)*cos(longi2-longi1),2)+pow(cos(lati2)*sin(longi2-longi1),2)+pow(sin(lati1)-sin(lati2),2);
        subangle=acos(0.5*(2-F));
        distance=6371*subangle;
        return distance;

    }
}