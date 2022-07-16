package com.example.main_map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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

    public void Login (View view){
        //FirebaseUser user = mAuth.getCurrentUser();
        //if (user.isEmailVerified()){
        if (!TextUtils.isEmpty(ETemail.getText().toString()) && !TextUtils.isEmpty(ETpassword.getText().toString()))
        {
            signing(ETemail.getText().toString(), ETpassword.getText().toString());
        }
        else
            {
                Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show();
        }
    //}
        //else {
          //  Toast.makeText(this, "Почта не подтверждена", Toast.LENGTH_SHORT).show();
        //}
    }
    public void signing(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                Toast.makeText(login_Activity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                FirebaseUser user = mAuth.getCurrentUser();
                if (user.isEmailVerified()){
                Intent intent = new Intent(login_Activity.this,MapsActivity.class);
                startActivity(intent);}
                else {
                    Toast.makeText(login_Activity.this, "Почта не подтверждена", Toast.LENGTH_SHORT).show();
                }
                } else {
                    Toast.makeText(login_Activity.this,"Не верный логин или пароль",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
