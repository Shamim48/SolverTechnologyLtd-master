package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.Solver.Solver.ModelClass.GroupMessage;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ShareLocationAct extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMarkerClickListener{

    public final static int uid=432;
    private static final String TAG = "GroupChatAtv";
    NotificationCompat.Builder notification;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 111;
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = true;
    private FusedLocationProviderClient client;
    private EditText addressEt,msgEt;
    private ImageButton sendMsgBtn;
    double latitude;
    double longitude;
    String addressLine;
    FirebaseAuth mAuth;
    private DatabaseReference UsersRef, GroupNameRef, GroupMessageKeyRef, clientRef, jobRef, scheduleREf, allScheduleREf;

    private String currentGroupName, currentUserID, currentUserName, currentDate,currentSceDate, currentTime;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);
        addressEt = findViewById(R.id.addressId);
        msgEt=findViewById(R.id.msgEtLsId);
        sendMsgBtn=findViewById(R.id.sendMsgBtnId);
        client = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent()!=null){
            currentGroupName=getIntent().getStringExtra("groupName");
        }

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("User");
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Group").child(currentGroupName);

        dateTime();
        getUserInfo();


        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveMessageInfoToDatabase();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        //mMap.setOnMarkerDragListener(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnMarkerClickListener(this)  ;
        mMap.setTrafficEnabled(true);
        if(mLocationPermissionGranted){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        //updateLocationUI();
        getCurrentLocation();
    }

    private void getUserInfo() {
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCurrentLocation() {
        if(mLocationPermissionGranted){
            client.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location == null){
                                return;
                            }

                             latitude = location.getLatitude();
                             longitude =location.getLongitude();
                            LatLng currentLatLnt = new LatLng(latitude, longitude);
                            getLocationAddress();
                            /*Marker myPositionMarker =
                                    mMap.addMarker(new MarkerOptions()
                                    .position(currentLatLnt)
                                    .title("I am here")
                                    .draggable(true));*/
                          //  address.setText(String.valueOf(latitude)+","+String.valueOf(longitude));
                            mMap.addMarker(new MarkerOptions().position(currentLatLnt).title(addressLine));
                           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLnt, 18.5f));
                            mMap.addMarker(new MarkerOptions().position(currentLatLnt).title(addressLine));

                            //   mMap.setBuildingsEnabled(true);

                           // getNearbyPlaces(new LatLng(latitude, longitude));
                        }
                    });
        }
    }



    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getCurrentLocation();
                }
            }
        }
        //updateLocationUI();
    }

    private void saveMessageInfoToDatabase() {

         message = msgEt.getText().toString();

        String address = addressEt.getText().toString();
        String messagekEY = GroupNameRef.push().getKey();
        String currentUserId = mAuth.getCurrentUser().getUid();

        if (latitude==0.0|| longitude==0.0) {
            Toast.makeText(this, "Location not found.. Please try again..", Toast.LENGTH_SHORT).show();
        } else {


   // public GroupMessage(String name, String sender, String message, String time, String date, String msgType, String msgKey, String groupName, double latitude, double longitude, String address) {

            GroupMessage groupMessage = new GroupMessage(currentUserName, currentUserId, message, currentTime, currentDate, "Location",messagekEY,currentGroupName,latitude,longitude,address);
            GroupNameRef.child(messagekEY).setValue(groupMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        msgEt.setText(null);
                        // tagClientAt.setText(null);
                        sendNotification(currentGroupName,message);

                        Intent intent=new Intent(getApplicationContext(),GroupChatAtv.class);
                        intent.putExtra("GroupName",currentGroupName);
                        startActivity(intent);
                        finish();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            //subEt.setText(null);


        }
    }
    public void dateTime(){
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForSce = Calendar.getInstance();
        SimpleDateFormat currentDateFormatSce = new SimpleDateFormat("dd-MM-yyy");
        currentSceDate = currentDateFormatSce.format(calForSce.getTime());



        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());
    }

    public void sendNotification(String title,String message){
        notification.setSmallIcon(R.drawable.solverlogo);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setWhen(System.currentTimeMillis());
        // notification.setAutoCancel(true);
        String action;
        Intent intent=new Intent(this,GroupChatAtv.class);

        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ShareLocationAct.this);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(uid,notification.build());

    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
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


             addressLine=subLocality+","+city+","+country;

            addressEt.setText(addressLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, "stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = mMap.getCameraPosition().target;
      //  address.setText(latLng.latitude+" "+latLng.longitude);
      //  getNearbyPlaces(latLng);
    }

    @Override
    public void onCameraMove() {
        LatLng latLng = mMap.getCameraPosition().target;
        //address.setText(latLng.latitude+" "+latLng.longitude);
        mMap.clear();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
