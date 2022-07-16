package com.example.main_map;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.text.format.DateFormat;
import android.widget.Toast;

public class infoPointActivity extends AppCompatActivity {
    private TextView Name, Adre, Opis, Rank;
    private ImageView Img;
    private String USER_KEY = "Point_Date";
    private String USER_KEY1 = "Chat";
    private String USER_KEY2 = "User_Date";
    private DatabaseReference myRef;
    public DatabaseReference myRef1;
    public DatabaseReference myRef2;
    private String Dat, d, tx1, a,b,c,k,l;
    private int f;
    private List<String> listData;
    private ArrayAdapter<String> adapter;
    private ListView list;
    private String[] array;
    private TextView text;
    private TextView name, time, al;
    private FirebaseAuth mAuth;
    private ListView listOfMessage;
    private int user = 0; //c
    private FirebaseListAdapter<Message> adapter1;
    private FirebaseListAdapter<Message> adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_point);

        init();
        getIntentMain();
        loadInf();
        mAuth = FirebaseAuth.getInstance();
        list =findViewById(R.id.chatList);
        //array = getResources().getStringArray(R.array.Test);
        listData = new ArrayList<>();
        //adapter = new ArrayAdapter<>(this, R.layout.list_item,array);
       adapter = new ArrayAdapter<>(this, R.layout.list_item,listData);
//
        list.setAdapter(adapter);
        displaMasseg();
       // Message();

        FirebaseUser cUser = mAuth.getCurrentUser(); //c
        if (cUser != null && cUser.isEmailVerified()) //c
        {
            user = 1; //c
        }

    }
    public void init()
    {
        Rank = findViewById(R.id.textView98);
        Name = findViewById(R.id.textView7);
        Adre = findViewById(R.id.textView9);
        Opis = findViewById(R.id.textView6);
        Img = findViewById(R.id.imageView3);
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
        myRef1 = FirebaseDatabase.getInstance().getReference(USER_KEY1);
        myRef2 = FirebaseDatabase.getInstance().getReference(USER_KEY2);
        text = findViewById(R.id.editText);


    }

    public void sendMas()
    {
        HashMap<String,Object> map = new HashMap<>();
        map.put("userName", mAuth.getUid());
        //map.put("pointId", Dat);
       // Date currentDate = new Date();
      // map.put("time", currentDate);
       //map.put("time", DateFormat.format("hh:mm:ss"));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("dd-MM HH:mm");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String dateFormat = simpleDateFormat.format(calendar.getTime());
            map.put("messageTime",dateFormat);
        }


        // map.put("time", timeFormat);
        map.put("textMessage", text.getText().toString());
        myRef.child(Dat).child("Chat").child(String.valueOf(System.currentTimeMillis())).setValue(map);
        Toast.makeText(this, "Сообщение отправлено", Toast.LENGTH_SHORT).show();

    }
    public void  Message()
    {
        listOfMessage =  findViewById(R.id.chatList);



      Query query = FirebaseDatabase.getInstance().getReference(USER_KEY1).child(Dat);
        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLayout(R.layout.list_item_two)
                .build();
        adapter1 = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView( View v, Message model, int position) {
                 TextView massege_user, massege_date, massege_text;
                massege_user = (TextView) v.findViewById(R.id.massege_user);
                massege_date = (TextView) v.findViewById(R.id.massege_date);
                massege_text= (TextView) v.findViewById(R.id.massege_text);

                massege_user.setText(model.getUserName());
                massege_text.setText(model.getTextMessage());
                massege_date.setText(model.getMessageTime());
////                massege_user.setText("ffadfaa");
////                massege_text.setText("qwdqwdq");
////                massege_date.setText("dwqdqwdq");
//
            }
//
        };
        listOfMessage.setAdapter(adapter1);
        //adapter1.notifyDataSetChanged();
    }
    public void displaMasseg()
    {
    myRef.child(Dat).child("Chat").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(listData.size() > 0) listData.clear();
            for (DataSnapshot ds: snapshot.getChildren())
            {


                a = ds.child("userName").getValue().toString();



                ValueEventListener valueEventListener1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                    d = snapshot.child("Name").getValue().toString();
                    k = snapshot.child("Status").getValue().toString();
                    f = Integer.parseInt(k);
                    if (f >= 3 && f < 5)
                    {
                        l = "Профи";
                    }
                    if (f >= 5)
                    {
                        l= "Гуру";
                    }
                    else
                    {
                        l="Пользователь";
                    }

                        b = ds.child("messageTime").getValue().toString();
                        c = ds.child("textMessage").getValue().toString();

                        tx1 =  l + " " +d +"  "+ b+ "\n" + c;
                        listData.add(tx1);
                       // listData.add(d);
                        adapter.notifyDataSetChanged();

                   //text.setText(snapshot.child("Name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                //text.setText(d);
                //k = text.getText().toString();
                myRef2.child(a).addValueEventListener(valueEventListener1);
//d = "Al";

               // tx1 = d +"  "+ b+ "\n" + c;
               // tx1 = d;
             //  listData.add(tx1);
               // adapter.notifyDataSetChanged();
              // listData.add(b);
            }
            //adapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }
    private void getIntentMain()
    {

        Intent intent = getIntent();
        if (intent !=  null) {
            Dat = intent.getStringExtra("Dat");
        }

    }
    private  void loadInf()
    {
        myRef.addValueEventListener(valueEventListener);
        //Name.setText(myRef.child(Dat).child("Name").toString());
        //Adre.setText(myRef.child(Dat).child("Adres").toString());
        //Opis.setText(myRef.child(Dat).child("Opis").toString());
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Name.setText(snapshot.child(Dat).child("Name").getValue().toString());
            Adre.setText(snapshot.child(Dat).child("Adres").getValue().toString());
            Opis.setText(snapshot.child(Dat).child("Opis").getValue().toString());
            Rank.setText(snapshot.child(Dat).child("Rank").getValue().toString());
            Picasso.get().load(snapshot.child(Dat).child("Id_image").getValue().toString()).into(Img);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    public void masseg (View view)
    {
        if ( user == 1 ) { //c
        sendMas();
        text.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else { // c
            Toast.makeText(this, "Для исспользования этой функции авторизируйтесь в приложении", Toast.LENGTH_SHORT).show();
        }
    }

}
