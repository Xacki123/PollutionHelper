package com.example.main_map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class info_userActivity extends AppCompatActivity {
    private ImageView avatar;
    private String[] array;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText Name;
    private EditText Phone;
    private EditText Mail;
    private int Avatar;
    private int cost;
    private int a;
    private String item;

    private String USER_KEY = "User_Date";
    private DatabaseReference myRef;

   // private EditText Name;
   // private EditText Phone;
   // private FirebaseAuth mAuth;
   // private String USER_KEY = "User_Date";
   // private DatabaseReference myRef;
   // private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_user);
        array = getResources().getStringArray(R.array.Avatar);
        init();
        getIntentMain();
        AvatarStatus();


        mAuth = FirebaseAuth.getInstance();

        final int listsize = getResources().getStringArray(R.array.Avatar).length - a;
        ArrayAdapter<String> AvatarAdapter = new ArrayAdapter<String>(this,
                R.layout.spiner_item, array) {
            @Override
            public int getCount() {
                return (listsize); // Truncate the list
            }
        };


        AvatarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spAvatar = (Spinner) findViewById(R.id.spinner2);
        spAvatar.setAdapter(AvatarAdapter);
        spAvatar.setPrompt("Аватар");
        spAvatar.setSelection(Avatar);
       // spAvatar.setSelection(4);


        avatar = findViewById(R.id.AvatarIm);


        spAvatar.setOnItemSelectedListener(onItemSelectedListener());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Name.setText(dataSnapshot.child("Name").getValue().toString());
                //Name.setText(dataSnapshot.child("Name").getKey());
                Phone.setText(dataSnapshot.child("Phone").getValue().toString());
                Mail.setText(user.getEmail());
               // cost = dataSnapshot.child("Status").getValue(Integer.class);
                Avatar = Integer.parseInt(dataSnapshot.child("Avatar").getValue().toString());
                spAvatar.setSelection(Avatar);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.child(user.getUid()).addValueEventListener(valueEventListener);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {

                } else{

                }

            }
        };

    }


        private void init ()
        {
            Name = findViewById(R.id.Name2);
            Phone = findViewById(R.id.Phone2);
            myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
            Mail = findViewById(R.id.Mail2);
        }
        private void getIntentMain()
        {

            Intent intent = getIntent();
            if (intent !=  null) {
                cost = intent.getIntExtra("cost",   0);
            }

        }
        private void AvatarStatus() // не работает
        {
            if (cost >= 3 && cost<5)
            { a = 2; }
            else if (cost >= 5)
            { a = 1; }
            else { a = 3;}


        }
        private void reDate ()
        {
            HashMap hashMapPhone = new HashMap();
            HashMap hashMapName = new HashMap();
            //HashMap hashMapAvatar = new HashMap();
            //hashMapAvatar.put("Avatar", item);
            myRef.child(mAuth.getUid()).child("Avatar").setValue(item);
            hashMapName.put("Name", Name.getText().toString());
            hashMapPhone.put("Phone", Phone.getText().toString());

            myRef.child(mAuth.getUid()).updateChildren(hashMapName);
            myRef.child(mAuth.getUid()).updateChildren(hashMapPhone);
          // myRef.child("User_Date").child(mAuth.getUid()).child("Phone").updateChildren(null);
            //myRef.child(mAuth.getUid()).updateChildren(null);
           // myRef.child("User_Date").child(mAuth.getUid()).child("Name").setValue(Name);
           // myRef.child("User_Date").child(mAuth.getUid()).child("Phone").setValue(Phone);
            Toast.makeText(info_userActivity.this, "Данные успешно изменены", Toast.LENGTH_SHORT).show();
        }
    public void saveUser (View view) {
        if (!TextUtils.isEmpty(Name.getText().toString()) && !TextUtils.isEmpty(Phone.getText().toString()) && Phone.length() >= 11)
        {


            reDate();
            Intent intent = new Intent(info_userActivity.this,MapsActivity.class);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show();
        }
    }
    AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        avatar.setImageResource(R.drawable.avatar011);
                        item = "0";
                        break;
                    case 1:
                        avatar.setImageResource(R.drawable.avatar022);
                        item = "1";
                        break;
                    case 2:
                        avatar.setImageResource(R.drawable.avatar033);
                        item = "2";
                        break;
                    case 3:
                        avatar.setImageResource(R.drawable.avatar044);
                        item = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
    }


