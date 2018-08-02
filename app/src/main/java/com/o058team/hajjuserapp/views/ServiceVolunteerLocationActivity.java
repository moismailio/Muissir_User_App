package com.o058team.hajjuserapp.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.o058team.hajjuserapp.R;

import static com.o058team.hajjuserapp.App.database;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class ServiceVolunteerLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap googleMap;

    Button btnRate;
    String key;

    Button btnCancel;
    MaterialDialog cancelServiceDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_volunteer);
        btnRate = findViewById(R.id.btnRate);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelServiceDialog = new MaterialDialog.Builder(ServiceVolunteerLocationActivity.this).
                        title("Remove Request").titleColor(ContextCompat.getColor(ServiceVolunteerLocationActivity.this,R.color.redColor)).
                        content("Are you Sure you want to Remove service").positiveText("Remove").positiveColor(ContextCompat.getColor(ServiceVolunteerLocationActivity.this,R.color.redColor)).
                        negativeText("Dismiss").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        cancelServiceDialog.dismiss();
                    }
                }).build();

                cancelServiceDialog.show();
            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRateClicked();
            }
        });
        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentData));
        mapFragment.getMapAsync(this);
        key = getIntent().getStringExtra("selected_key");

        DatabaseReference myRef = database.getReference("help_requests");
        myRef.child(key).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equalsIgnoreCase("done")){
                    showCompleteMessage();
                    btnRate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    MaterialDialog infoDialog;
    public void showMessageDialog(){
        infoDialog = new MaterialDialog.Builder(this).title("Volunteer on his way").content("Yasser on His to you on 100 M").positiveText("ok").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                infoDialog.dismiss();
            }
        }).build();
        infoDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap gm) {
        if (gm != null) {
            googleMap = gm;
            configureBlueDot();
            zoomToMyCurrentLocation();
        }
    }

    public void configureBlueDot() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
    }



    Location currentLocation;

    public void zoomToMyCurrentLocation() {
        if (googleMap != null) {
            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if (location != null) {
                        googleMap.moveCamera(CameraUpdateFactory.
                                newLatLngZoom(new LatLng(location.getLatitude(),
                                        location.getLongitude()), 10.0f));
                        googleMap.setOnMyLocationChangeListener(null);
                        currentLocation = location;
                        addmarker();
                        showMessageDialog();
                    }
                }
            });

        }
    }

    Polyline mapPolyline;

    public void addmarker(){
        MarkerOptions markerOptions = new MarkerOptions();
//        LatLng latLng = new LatLng(21.293803011673397,40.38966289970858);

        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title("the volunteer is on his way");
        googleMap.addMarker(markerOptions);
    }


    MaterialDialog ratingDialog;
    public void onRateClicked(){
        ratingDialog = new ServiceRatingDialog(this, new ServiceRatingDialog.onRateClicked() {
            @Override
            public void onButtonClicked() {
                ratingDialog.dismiss();
            }
        }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                ratingDialog.dismiss();
            }
        }).build();
        ratingDialog.show();
    }

    public void showCompleteMessage(){
        playNotificationSound();
        MaterialDialog completeDialog = new MaterialDialog.Builder(this).title("Service Completed").
                content("Thanks for using service, you could rate us").build();
        completeDialog.show();
    }

    public void playNotificationSound(){
        try {
            Uri ring_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ring_uri);
            r.play();
        } catch (Exception e) {
            // Error playing sound
        }
    }

}
