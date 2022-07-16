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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registr2 extends AppCompatActivity {
    private ImageView avatar;
    private String[] array;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String item;
    private EditText Name;
    private EditText Phone;
    private EditText email;
    private EditText password;
    private FirebaseUser user;

    private String USER_KEY = "User_Date";
    private DatabaseReference myRef;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr2);
        array = getResources().getStringArray(R.array.Avatar);
        init();


        final int listsize = getResources().getStringArray(R.array.Avatar).length - 3;
        ArrayAdapter<String> AvatarAdapter = new ArrayAdapter<String>(this,
                R.layout.spiner_item, array){
            @Override
            public int getCount() {
                return(listsize); // Truncate the list
            }
        };




        AvatarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spAvatar = (Spinner) findViewById(R.id.spinner1);
        spAvatar.setAdapter(AvatarAdapter);
        spAvatar.setPrompt("Аватар");
        spAvatar.setSelection(4);



        avatar = findViewById(R.id.AvatarIm);


        spAvatar.setOnItemSelectedListener(onItemSelectedListener());



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
    private void init()
    {
        Name = findViewById(R.id.Name1);
        Phone = findViewById(R.id.Phone1);
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        email = findViewById(R.id.email);
        password =  findViewById(R.id.pass);
    }
    public void registration (String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String name = Name.getText().toString();
                    String phone = Phone.getText().toString();
                    String AvItem = item;
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("Name", name);
                    map.put("Phone", phone);
                    map.put("Avatar", AvItem);
                    myRef.child(mAuth.getUid()).setValue(map);




                    Toast.makeText(registr2.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    sendEmailVer();
                    Intent intent = new Intent(registr2.this,login_Activity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(registr2.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   /* private void Date()
    {
       // String id = mAuth.getUid();
        String name = Name.getText().toString();
        String phone = Phone.getText().toString();
        String AvItem = item.toString();
        //String item =
       // User newUser = new User(id, name, phone);
        myRef.child(mAuth.getUid()).child("Name").setValue(name);
        myRef.child(mAuth.getUid()).child("Phone").setValue(phone);
        myRef.child(mAuth.getUid()).child("Avatar").setValue(AvItem);

        //myRef.child(mAuth.getUid()).push().setValue(newUser);
       // myRef.child(mAuth.getUid()).child("Name").push().setValue(name);
       // myRef.child(mAuth.getUid()).child("Phone").push().setValue(phone);
       // myRef.push().setValue(newUser);
        Toast.makeText(registr2.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(registr2.this,login_Activity.class);
        startActivity(intent);

    */

    //}
    private  void  sendEmailVer()
    {
        FirebaseUser user =  mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {

                    Toast.makeText(registr2.this, "Зайдите на почту для подтверждения", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(registr2.this, "Ошибка с почтой", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void reg (View view){

        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && !TextUtils.isEmpty(Name.getText().toString()) && !TextUtils.isEmpty(Phone.getText().toString()) && Phone.length() >= 11)
        {

            registration(email.getText().toString(), password.getText().toString());
            //Date();

        }
        else
        {
            Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show();
        }


    }





    AdapterView.OnItemSelectedListener onItemSelectedListener()
    {
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
