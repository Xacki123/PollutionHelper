package com.example.main_map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class rePoint  extends AppCompatActivity {
    private TextView Name, Rank, Adres, Opis;
    private ImageView img;
    private String id;
    private String USER_KEY = "Point_Date";
    private String USER_KEYs = "User_Date";
    private DatabaseReference myRef;
    private DatabaseReference myRefs;
    private FirebaseAuth mAuth;
    private Uri upLoadUri;
    private StorageReference mStorageReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repoint);
        init();
        getIntentMain();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Name.setText(dataSnapshot.child(id).child("Name").getValue().toString());
                Rank.setText(dataSnapshot.child(id).child("Rank").getValue().toString());
                Adres.setText(dataSnapshot.child(id).child("Adres").getValue().toString());
                Opis.setText(dataSnapshot.child(id).child("Opis").getValue().toString());
                Picasso.get().load(dataSnapshot.child(id).child("Id_image").getValue().toString()).into(img);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
    }
    private void init()
    {
        Name = findViewById(R.id.Name);
        Rank = findViewById(R.id.Rank);
        Adres = findViewById(R.id.Adres);
        Opis = findViewById(R.id.Opis);
        img = findViewById(R.id.imageView);
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
        myRefs = FirebaseDatabase.getInstance().getReference(USER_KEYs);
        mAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference("ImageDB");
    }
    private void getIntentMain() {

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }
    }
    private void reDate ()
    {
        HashMap hashMapName = new HashMap();
        HashMap hashMapAdres = new HashMap();
        HashMap hashMapOpis = new HashMap();
        HashMap hashMapId = new HashMap();
        HashMap hashMapImg = new HashMap();

        hashMapName.put("Name", Name.getText().toString());
        hashMapAdres.put("Adres", Adres.getText().toString());
        hashMapOpis.put("Opis", Opis.getText().toString());
        hashMapId.put(id, Name.getText().toString() + "Key" + id);
        hashMapImg.put("Id_image", upLoadUri.toString());

        myRef.child(id).updateChildren(hashMapName);
        myRef.child(id).updateChildren(hashMapAdres);
        myRef.child(id).updateChildren(hashMapOpis);
        myRef.child(id).updateChildren(hashMapImg);
        myRefs.child(mAuth.getUid()).child("Point").updateChildren(hashMapId);




        //Toast.makeText(info_userActivity.this, "Данные успешно изменены", Toast.LENGTH_SHORT).show();
    }
    private void getImg()
    {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }
    public void imgRePlass (View view)
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
                reDate();
            }
        });
    }
    public void savePoint (View view)
    {
        if (!TextUtils.isEmpty(Adres.getText().toString()) && !TextUtils.isEmpty(Name.getText().toString()) && !TextUtils.isEmpty(Opis.getText().toString())) {

           // reDate();
            upLoadImage();
            Intent intent = new Intent(rePoint.this, MapsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show();
        }
    }

}
