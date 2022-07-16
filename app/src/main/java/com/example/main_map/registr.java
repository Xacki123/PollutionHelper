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

public class registr extends AppCompatActivity {
    private ImageView avatar;
    private String[] array;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETemail;
    private EditText ETpassword;
    private EditText ETname;
    private EditText ETphone;
    private EditText Name;
    private EditText Phone;

    private String USER_KEY = "User_Date";
    private DatabaseReference myRef;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr);
        array = getResources().getStringArray(R.array.Avatar);
        //init();
        mAuth = FirebaseAuth.getInstance();





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
        ETemail = (EditText) findViewById(R.id.email);
        ETpassword = (EditText) findViewById(R.id.pass);

    }
   /* private void init()
    {
        Name = findViewById(R.id.Name);
        Phone = findViewById(R.id.Phone);
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    private void Date()
    {
        String id = mAuth.getUid();
        String name = Name.getText().toString();
        String phone = Phone.getText().toString();
        User newUser = new User(id, name, phone);
        myRef.push().setValue(newUser);

    } */
    public void registr (View view){
        if (!TextUtils.isEmpty(ETemail.getText().toString()) && !TextUtils.isEmpty(ETpassword.getText().toString()))
        {
            registration(ETemail.getText().toString(), ETpassword.getText().toString());

           // Date();

        }
        else
        {
            Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show();
        }


    }
public void registration (String email, String password)
{
mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
            Toast.makeText(registr.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
            sendEmailVer();
            Intent intent = new Intent(registr.this,registr2.class);
            startActivity(intent);
            }
        else {
            Toast.makeText(registr.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
        }
    }
});
}




private  void  sendEmailVer()
{
    FirebaseUser user =  mAuth.getCurrentUser();
    assert user != null;
    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful())
            {
                Toast.makeText(registr.this, "Зайдите на почту для подтверждения", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(registr.this, "Ошибка с почтой", Toast.LENGTH_SHORT).show();
            }
        }
    });
}
}

