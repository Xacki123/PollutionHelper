package com.example.main_map;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.main_map.databinding.ActivityMapsBinding;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final int SITYSCALE = 15;
    private String Locate;
    private double X, Y;
    private String USER_KEY = "Point_Date";
    private DatabaseReference myRef;
    private String Dat;
    private TextView Dat1;
    private int f = 0;
    private int user = 0; //c
    private String filtr;
    private FirebaseAuth mAuth; // c
    final String[] catNamesArray = {"Стекло", "Макулатура", "Одежда", "Пластик", "Батарейки", "Бытовая техника", "Люминисцентные лампы", "Металлолом"};
    final boolean[] checkedItemsArray = {false, false, false, false, false, false, false, false};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance(); //c
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        loadMapPoint();
        Dat1 = findViewById(R.id.Ed22);
        FirebaseUser cUser = mAuth.getCurrentUser(); //c
        if (cUser != null && cUser.isEmailVerified()) //c
        {
            user = 1; //c
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;




    mMap.getUiSettings().setZoomControlsEnabled(true);
if (ActivityCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
{mMap.setMyLocationEnabled(true);}

LatLng Slava = new LatLng(59.8603497,30.3896796);
mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Slava,SITYSCALE));
//mMap.addMarker(new MarkerOptions().position(Slava).title(getString(R.string.test)).snippet(getString(R.string.test_click)));
mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

        Dat = (String) marker.getTag();
        if (Dat!= null)
        {
        Toast.makeText(MapsActivity.this, "Марке " + Dat, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MapsActivity.this,infoPointActivity.class);
        intent.putExtra("Dat", Dat);
        startActivity(intent);
        } else {
            Intent intent = new Intent(MapsActivity.this, addActivity.class);
            intent.putExtra("Locate", Locate);
            intent.putExtra("X", X);
            intent.putExtra("Y", Y);
            startActivity(intent);
        }
    }
});
mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
     /*   Dat = (String) marker.getTag();
        if (Dat == null) {
            // Intent intent = new Intent(MapsActivity.this,testActiv.class);
            Intent intent = new Intent(MapsActivity.this, addActivity.class);
            intent.putExtra("Locate", Locate);
            intent.putExtra("X", X);
            intent.putExtra("Y", Y);
            startActivity(intent);
        } */
    }
});

mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions(); // Создание маркера
        markerOptions.position(latLng); // Установка маркера
        markerOptions.title("Новый маркер").snippet(latLng.latitude+ " : " + latLng.longitude);
        Locate = latLng.latitude+ " : " + latLng.longitude; // xz
        X = latLng.latitude;
        Y = latLng.longitude;
        //mMap.clear();
        mMap.addMarker(markerOptions);
    }
});
        // Add a marker in Sydney and move the camera
       /** LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }
    public void loadMapPoint()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    String X1 = ds.child("X").getValue().toString();
                    String Y1 = ds.child("Y").getValue().toString();
                    String name = ds.child("Name").getValue().toString();
                    String rank = ds.child("Rank").getValue().toString();
                    String id = ds.getKey();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(X1), Double.parseDouble(Y1))).title(name).snippet(rank)).setTag(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void FiltrPoint()
    {
        mMap.clear();
        if (filtr != null){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    String X1 = ds.child("X").getValue().toString();
                    String Y1 = ds.child("Y").getValue().toString();
                    String name = ds.child("Name").getValue().toString();
                    String rank = ds.child("Rank").getValue().toString();
                    String id = ds.getKey();
                    if (rank.contains(filtr)) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(X1), Double.parseDouble(Y1))).title(name).snippet(rank)).setTag(id);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}
        else {
        loadMapPoint();
        }

    }
    public  void  poisk()
    {
        String adress = Dat1.getText().toString();
        List<Address> addressList = null;
        MarkerOptions userMarker = new MarkerOptions();
        if (!TextUtils.isEmpty(adress))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(adress, 4);
                if (addressList != null)
                {for (int i = 0; i<addressList.size(); i++)
                {
                    Address userAddress = addressList.get(i);
                    LatLng latlang = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                    userMarker.position(latlang);
                    userMarker.title(adress).snippet(latlang.toString());
                    Locate = userAddress.getLatitude()+ " : " + userAddress.getLongitude(); // xz
                    X = userAddress.getLatitude();
                    Y = userAddress.getLongitude();
                    userMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.addMarker(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(14));}
                } else {
                    Toast.makeText(this, "Адрес не найден", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  else {
            Toast.makeText(this, "Заполните строку поиска", Toast.LENGTH_SHORT).show();
            Dat1.setVisibility(View.GONE);
            f = 0;

        }

    }
    public void goRoom(View view) {

       // Intent intent = new Intent(MapsActivity.this,testActiv.class);
        if ( user == 1 ) { //c
            Intent intent = new Intent(MapsActivity.this, infoActivity.class);
            startActivity(intent);
        } else { // c
            Toast.makeText(this, "Для исспользования этой функции авторизируйтесь в приложении", Toast.LENGTH_SHORT).show();
        }

}
    public void goHome(View view) {
        Intent intent = new Intent(MapsActivity.this,MainActivity.class);
        startActivity(intent);
    FirebaseAuth.getInstance().signOut();
    }
    public void goAdd(View view) {
        //Intent intent = new Intent(MapsActivity.this,addActivity.class);
//        Intent intent = new Intent(MapsActivity.this,testActiv.class);
//        startActivity(intent);}
        filtr = "";
        AlertDialog.Builder a_bilder = new AlertDialog.Builder(MapsActivity.this);
        a_bilder.setCancelable(false)
//                .setMultiChoiceItems(catNamesArray, checkedItemsArray, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                        checkedItemsArray[which] = isChecked;
//                    }
//                })
                .setSingleChoiceItems(catNamesArray, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                filtr = catNamesArray[which];
                            }
                        })
                .setPositiveButton("Фильтровать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        for (int i = 0; i < catNamesArray.length; i++) {
//                            if (checkedItemsArray[i]){
//                                filtr = catNamesArray[i];
//                            }
//
//                        }

                            FiltrPoint();
                            dialog.cancel();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_bilder.create();
        alert.setTitle("Фильтр");
        alert.show();

    }
    public void cearchMap(View view)
    {
      if (f == 0)
      {
          Dat1.setVisibility(View.VISIBLE);
          f = 1;
      }
      else
          {
        poisk();

      }

        }
}