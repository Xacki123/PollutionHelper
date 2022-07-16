package com.example.main_map;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class infoActivity extends AppCompatActivity {
    private ListView list;
    private String key, b;
    private String[] array;
    private ArrayAdapter<String> adapter;
    private TextView Name;
    private TextView UserStatus;
    private FirebaseAuth mAuth;
    private String USER_KEY = "User_Date";
    private String USER_KEYs = "Point_Date";
    private DatabaseReference myRef;
    private DatabaseReference myRefs;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int item = 2;
    private ImageView Avatar;
    private int cost = 0;
    private List<String> listData;
    private List<String> listData2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        init();
        list =findViewById(R.id.ListViewInf);

        array = getResources().getStringArray(R.array.Test);
        listData = new ArrayList<>();
        listData2 = new ArrayList<>();

        //adapter = new ArrayAdapter<>(this, R.layout.list_item,array);
        adapter = new ArrayAdapter<>(this, R.layout.list_item,listData);
        list.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Name.setText(dataSnapshot.child("Name").getValue().toString());
               // Name.setText(dataSnapshot.child("Avatar").getValue().toString());
                item = Integer.parseInt(dataSnapshot.child("Avatar").getValue().toString());


                if (item ==  0)
                {
                    Avatar.setImageResource(R.drawable.avatar011);
                }
               else if (item == 1)
                {
                    Avatar.setImageResource(R.drawable.avatar022);
                }else if (item == 2)
                {
                    Avatar.setImageResource(R.drawable.avatar033);
                }else if (item == 3)
                {
                    Avatar.setImageResource(R.drawable.avatar044);
                }
                else {
                    Avatar.setImageResource(R.drawable.oow);
                   // System.out.print("Значение = " + Avatar);
                }

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
        getDataFromDB();
        setOnClickItem();
       // Date();
       // Status();


    }

public void init()
{
    Avatar = findViewById(R.id.infoAvatar);
    Name = findViewById(R.id.NameInf);
    UserStatus = findViewById(R.id.textView11);
    myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
    myRefs = FirebaseDatabase.getInstance().getReference(USER_KEYs);

}


    public void goInfUser(View view) {
        Intent intent = new Intent(infoActivity.this,info_userActivity.class);
        intent.putExtra("cost", cost);
        startActivity(intent);
    }
    public void Date()
    {
        cost = adapter.getCount();
        myRef.child(mAuth.getUid()).child("Status").setValue(cost);
    }
    private void getDataFromDB()
    {
        myRef.child(mAuth.getUid()).child("Point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listData.size() > 0) listData.clear();
            for (DataSnapshot ds: snapshot.getChildren())
            {
                String name =  ds.getValue().toString();

               // name = name;
                //listData.add(a);
                String a = name.split("Key")[0];
                String d = a+"Key";
                String c = name.replaceAll(d, "");
                listData.add(a);
                listData2.add(c);
                //listData2.add(name.split("Key")[-1]);

            }
            adapter.notifyDataSetChanged();
            Date();
            Status();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


  /*   ValueEventListener vListenrt = new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot)
    {
        if(listData.size() > 0) listData.clear();
    for(DataSnapshot ds : snapshot.getChildren())
    {
        //myRef.child(mAuth.getUid()).child("Point");
        User user = ds.getValue(User.class);
        assert user != null;
        listData.add(user.name);
        //listData.add(myRef.child(mAuth.getUid()).child("Point").toString());
    }
    adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};
myRef.addValueEventListener(vListenrt); //new */
    }


    public void setOnClickItem()
    {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  b =listData2.get(position);
               // String a = myRef.child(mAuth.getUid()).child("Point").k;
               // key = myRef.child(mAuth.getUid()).child("Name").getKey();
                //Name.setText(b);
                Intent intent = new Intent(infoActivity.this,rePoint.class);
                intent.putExtra("id", b);
                startActivity(intent);

            }
        });

    }

    public void Status()
    {
        if (cost >= 3 && cost < 5)
        {
            UserStatus.setText("Продвинутый пользователь");
        }
        else if (cost >= 5)
        {
            UserStatus.setText("Гуру");
        }
        else
        {
            UserStatus.setText("Пользователь");
        }
    }
}

