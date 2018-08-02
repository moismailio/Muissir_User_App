package com.o058team.hajjuserapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.o058team.hajjuserapp.R;
import com.o058team.hajjuserapp.models.ServiceRequest;
import com.o058team.hajjuserapp.models.ServiceResponseItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.o058team.hajjuserapp.App.database;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvServices)
    RecyclerView rvServices;

    @BindView(R.id.spinnerLang)
    Spinner spinnerLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        validateLocationPermission();
        setupLangSpinner();
    }

    public void setupLangSpinner(){
        LangSpinnerAdapter adapter= new LangSpinnerAdapter(this);
        spinnerLang.setAdapter(adapter);
    }

    public void validateLocationPermission(){
        if (    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
        else {
            setupAdapter();
        }
    }

    public void setupAdapter(){
        ArrayList<ServiceResponseItem> serviceResponseItems = new ArrayList<>();
        serviceResponseItems.add(new ServiceResponseItem(R.drawable.doctor,"service one"));
        serviceResponseItems.add(new ServiceResponseItem(R.drawable.guide,"service two"));
        serviceResponseItems.add(new ServiceResponseItem(R.drawable.mostaqbil,"service three"));
        serviceResponseItems.add(new ServiceResponseItem(R.drawable.tawaf,"service four"));
        serviceResponseItems.add(new ServiceResponseItem(R.drawable.translator,"service five"));
        serviceResponseItems.add(new ServiceResponseItem(R.drawable.other,"service six"));

        setupAdapter(serviceResponseItems);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        setupAdapter();
    }

    public void monitorLastPostedData(final String key){
        DatabaseReference myRef = database.getReference("help_requests");
        myRef.child(key).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equalsIgnoreCase("processing")){
                    loaderDialog.dismiss();
                    navigateToMap(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    MaterialDialog confrimationDialog;
    public void setupAdapter(ArrayList<ServiceResponseItem> items){
        ServicesListAdapter adapter = new ServicesListAdapter(items, this, new ServicesListAdapter.OnServiceClickListener() {
            @Override
            public void onServiceItemClicked(ServiceResponseItem item) {
                confrimationDialog = new ServiceSelectionConfirmation(MainActivity.this, new ServiceSelectionConfirmation.onConfirmationClicked() {
                    @Override
                    public void onButtonClicked() {
                        confrimationDialog.dismiss();
                        showLoadingDialog();
                    }
                }).build();
                confrimationDialog.show();
            }

            @Override
            public void playSound() {

            }
        });
        rvServices.setLayoutManager(new GridLayoutManager(this,2));
        rvServices.setAdapter(adapter);
    }
    MaterialDialog loaderDialog;

    public void showLoadingDialog(){
        loaderDialog= new MaterialDialog.Builder(this).title("Searching for Volunteer").content("Waiting for Nearest Volunteer to Accept").progress(true,100).build();
        loaderDialog.show();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loaderDialog.dismiss();
//                navigateToMap();
//            }
//        },3000);

        DatabaseReference myRef = database.getReference("help_requests");

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setServiceType("medication");
        serviceRequest.setActive(true);
        serviceRequest.setEstimatedDistance("5");
        serviceRequest.setEstimatedTime("3");
        serviceRequest.setStatus("waiting");

        String keyData =Long.toString(System.currentTimeMillis());
        myRef.child(keyData).setValue(serviceRequest);
        monitorLastPostedData(keyData);
    }

    public void navigateToMap(String key){
        Intent intent = new Intent(this,ServiceVolunteerLocationActivity.class);
        intent.putExtra("selected_key",key);
        startActivity(intent);
    }

    MaterialDialog localizationDialog;
    @OnClick(R.id.ivSetting)
    public void onSettingsClicked(){
        localizationDialog =  new MaterialDialog.Builder(this).customView(R.layout.localization_list,false).title("Select Language").positiveText("Save").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                localizationDialog.dismiss();
            }
        }).build();
        localizationDialog.show();
    }
}
