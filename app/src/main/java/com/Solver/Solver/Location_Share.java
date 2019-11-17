package com.Solver.Solver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class Location_Share extends AppCompatActivity {

    private boolean isLocationPermissionGranted=false;
    FusedLocationProviderClient client;
    private double latitude, longitude;
  private TextView latTv;
  private TextView lonTv;
  private TextView addressTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__share);
        Context context;
        latTv=findViewById(R.id.latitudeTvId);
        lonTv=findViewById(R.id.longitudeTvId);
        addressTv=findViewById(R.id.addressTvId);
        client = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();


    }
    public void checkLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},007);
        }else {
isLocationPermissionGranted=true;
            getDeviceCurrentLocation();
        }
    }

    private void getDeviceCurrentLocation() {
        if(isLocationPermissionGranted){
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location == null){
                        return;
                    }

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                   // getCurrentWeatherData();
                    getLocationAddress();

                  latTv.setText(String.valueOf(latitude));
                   lonTv.setText(String.valueOf(longitude));

                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:%s,%s",latitude,longitude)));
                    if(intent.resolveActivity(getApplicationContext().getPackageManager())!=null){
                        startActivity(intent);
                    }
                }
            });
        }

    }

    private void getLocationAddress() {
        final Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addressList.get(0).getAddressLine(0);
            String city = addressList.get(0).getLocality();
            String subLocality = addressList.get(0).getSubLocality();
            String country = addressList.get(0).getCountryName();
            String postalCode = addressList.get(0).getPostalCode();
            String knownName = addressList.get(0).getFeatureName();

            addressTv.setText(subLocality+","+address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
