package com.example.main_map;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Locale;

public class testActiv extends AppCompatActivity {
   private TextView Name, Name1, Name2;
    double lat = 57.9989369;
    double lng = 56.2853801;

    private String Locate;
   private ImageView img;
   private StorageReference mStorageReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Name = findViewById(R.id.Name000);
        Name1 = findViewById(R.id.name);
        Name2 = findViewById(R.id.name2);
        img = findViewById(R.id.imgTest);
        getCity();

    }
    private void getCity()
    {
       String muCity = "";
       Geocoder geocoder = new Geocoder(testActiv.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String address = addresses.get(0).getAddressLine(0);
            muCity = addresses.get(0).getLocality();
            Name2.setText(muCity);
            Name1.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    }


