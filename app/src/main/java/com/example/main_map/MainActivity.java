package com.example.main_map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button B1, B2, B3, B4, B5;
    private TextView T1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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

}

    public void goLogin(View view) {
        Intent intent = new Intent(MainActivity.this,login_Activity.class);
startActivity(intent);}

    public void goRegistr(View view) { Intent intent = new Intent(MainActivity.this,registr2.class);
        startActivity(intent);
    }
    public void goExit(View view) {
        FirebaseAuth.getInstance().signOut();
        B1.setVisibility(View.VISIBLE);
        B2.setVisibility(View.VISIBLE);
        B3.setVisibility(View.VISIBLE);
        B4.setVisibility(View.GONE);
        B5.setVisibility(View.GONE);
        T1.setVisibility(View.GONE);
    }
    public void goStart(View view) { Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }

    public void goB(View view) {
       // signing(getString(R.string.email), getString(R.string.password));
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }
    public void signing(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(intent);}
                else {
                    Toast.makeText(MainActivity.this, "Не верный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }
        public void init()
        {
            B1 = findViewById(R.id.bLogin);
            B2 = findViewById(R.id.bReg);
            B3 = findViewById(R.id.bNAft);
            B4 = findViewById(R.id.bStart);
            B5 = findViewById(R.id.bExit);
            T1 = findViewById(R.id.tStart);
        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null && cUser.isEmailVerified())
        {
        B1.setVisibility(View.GONE);
        B2.setVisibility(View.GONE);
        B3.setVisibility(View.GONE);
        B4.setVisibility(View.VISIBLE);
        B5.setVisibility(View.VISIBLE);
        T1.setVisibility(View.VISIBLE);
        String userName = "Вы вошли как : " + cUser.getEmail();
        T1.setText(userName);
        }
        else {
            B1.setVisibility(View.VISIBLE);
            B2.setVisibility(View.VISIBLE);
            B3.setVisibility(View.VISIBLE);
            B4.setVisibility(View.GONE);
            B5.setVisibility(View.GONE);
            T1.setVisibility(View.GONE);
        }
        }
    }
