package com.example.main_map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class addActivity extends AppCompatActivity {
    private String Sum;
    private double X, Y;
    Switch s1, s2,s3,s4,s5,s6,s7,s8;
    private  String Locate, Key;
    private TextView Adres, Name, Opis;
    private FirebaseAuth mAuth;
    private String USER_KEY = "Point_Date";
    private String USER_KEYS = "User_Date";
    private DatabaseReference myRef;
    private DatabaseReference myRefS;
    private ImageView img;
    private StorageReference mStorageReference;
    private Uri upLoadUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        init();
        getIntentMain();

       // Set();
        getCity();
        mAuth = FirebaseAuth.getInstance();
        s1.setOnCheckedChangeListener(listener);
        s2.setOnCheckedChangeListener(listener);
        s3.setOnCheckedChangeListener(listener);
        s4.setOnCheckedChangeListener(listener);
        s5.setOnCheckedChangeListener(listener);
        s6.setOnCheckedChangeListener(listener);
        s7.setOnCheckedChangeListener(listener);
        s8.setOnCheckedChangeListener(listener);
    }
    public void Save()
    {
        HashMap<String,Object> map = new HashMap<>();
        map.put("Name", Name.getText().toString());
        map.put("Adres", Adres.getText().toString());
        map.put("Opis", Opis.getText().toString());
        map.put("X", X);
        map.put("Y", Y);
        map.put("Rank", Sum);
        map.put("IdUser", mAuth.getUid());
        map.put("Id_image", upLoadUri.toString());
        myRef.setValue(map);
        Key = myRef.getKey();

        //myRefS.child(mAuth.getUid()).child("Point").setValue(Name.getText().toString());
        myRefS.child(mAuth.getUid()).child("Point").child(Key).setValue(Name.getText().toString() + "Key" + Key);
        //myRefS.child(mAuth.getUid()).child("Point").child(Key).setValue(Name.getText().toString());

        //myRefS.child(mAuth.getUid()).child("Point").setValue(Key);


        //myRef.setValue(map);
    }

    private void getCity()
    {
        //String muCity = "";
        Geocoder geocoder = new Geocoder(addActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(X, Y, 1);
            String address = addresses.get(0).getAddressLine(0);
           // muCity = addresses.get(0).getLocality();
            //Name2.setText(muCity);
            Adres.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void  Set()
    {
        //Adres.setText(Locate);
       // String url = "http://maps.google.com/maps/api/geocode/json?latlng=" +
        //        X + "," + Y + "&sensor=true&language=ru";
        //Adres.setText(url);

        //Name.setText("");
    }
    public void init()
    {
        Adres = findViewById(R.id.Adres89);
        Name = findViewById(R.id.Name89);
        Opis = findViewById(R.id.Op89);
        img = findViewById(R.id.imgAdd);
        s1 = findViewById(R.id.switch1);
        s2 = findViewById(R.id.switch2);
        s3 = findViewById(R.id.switch3);
        s4 = findViewById(R.id.switch4);
        s5 = findViewById(R.id.switch5);
        s6 = findViewById(R.id.switch6);
        s7 = findViewById(R.id.switch7);
        s8 = findViewById(R.id.switch8);
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY).push();
        myRefS = FirebaseDatabase.getInstance().getReference(USER_KEYS);
        mStorageReference = FirebaseStorage.getInstance().getReference("ImageDB");
    }
    private void getIntentMain()
    {
        Intent intent = getIntent();
        if (intent !=  null) {
            Locate = intent.getStringExtra("Locate");
            X = intent.getDoubleExtra("X",0);
            Y = intent.getDoubleExtra("Y",0);
        }
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId())
            {
                case R.id.switch1:
                   //Name.setText("omg");
                    if(isChecked){
                    Sum = Sum+"Стекло ";
                    Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Стекло ", "");
                    }
                    break;
                case R.id.switch2:
                    if(isChecked){
                        Sum = Sum+"Макулатура ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Макулатура ", "");
                    }
                    break;
                case R.id.switch3:
                    if(isChecked){
                        Sum = Sum+"Пластик ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Пластик ", "");
                    }
                    break;
                case R.id.switch4:
                    if(isChecked){
                        Sum = Sum+"Одежда ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Одежда ", "");
                    }
                    break;
                case R.id.switch5:
                    if(isChecked){
                        Sum = Sum+"Батарейки ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Батарейки ", "");
                    }
                    break;
                case R.id.switch6:
                    if(isChecked){
                        Sum = Sum+"Бытовая техника ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Бытовая техника ", "");
                    }
                    break;
                case R.id.switch7:
                    if(isChecked){
                        Sum = Sum+"Люминисцентные лампы ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Люминисцентные лампы ", "");
                    }
                    break;
                case R.id.switch8:
                    if(isChecked){
                        Sum = Sum+"Металлолом ";
                        Sum = Sum.replaceAll("null", "");}
                    else
                    {
                        Sum = Sum.replaceAll("Металлолом ", "");
                    }
                    break;


            }
        }
    };
    public void Plas (View view)
    {
        //getImg();
        if (!TextUtils.isEmpty(Adres.getText().toString()) && !TextUtils.isEmpty(Name.getText().toString()) && !TextUtils.isEmpty(Opis.getText().toString()) && !TextUtils.isEmpty(Sum)) {
            upLoadImage();
            //Save();
            Toast.makeText(addActivity.this, "Save", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(addActivity.this, MapsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show();

        }
    }
    public void PlasImg (View view)
    {
        getImg();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null)
        {
            if(resultCode == RESULT_OK)
            {
               // img.setImageResource(R.drawable.logo);
                img.setImageURI(data.getData());
                //upLoadImage();
            }
        }
    }

    private void getImg()
    {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }
    private void upLoadImage()
    {
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageReference.child(System.currentTimeMillis() + "my_image");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
            upLoadUri= task.getResult();
                Save();
            }
        });
    }


   /* public void getImage()
    {
        String[] mimeTypes = { "text/plain"};
        Intent intent = new Intent()
                .putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes) // xz
                .setType("file/*")
                //.setAction(Intent.ACTION_OPEN_DOCUMENT);
                .setAction(Intent.ACTION_GET_CONTENT);
    } */


}