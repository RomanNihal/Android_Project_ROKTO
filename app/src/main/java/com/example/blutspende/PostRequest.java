package com.example.blutspende;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostRequest extends AppCompatActivity {
    private String phone,address,division="",district="",bloodGroup="";
    private EditText contact,add;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_request);
        contact=findViewById(R.id.postRequestContactNumber);
        add=findViewById(R.id.postRequestAddress);
        bundle=getIntent().getExtras();
        division = bundle.getString("div");
        district = bundle.getString("dis");
        bloodGroup = bundle.getString("bg");
    }

    public void PostRequest(View view) {
        phone=contact.getText().toString();
        address=add.getText().toString();
        if(division.isEmpty() || district.isEmpty() || bloodGroup.isEmpty() || phone.isEmpty()|| address.isEmpty())
        {
            Toast.makeText(this, "Please enter every information", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        String key = databaseReference.push().getKey();
        databaseReference.child("PostRequests").child(division).child(district).child(bloodGroup)
                .child(key).setValue(new ModelPostRequest(SplashScreen.name,phone,bloodGroup,address,formatter.format(date)+"",district, division, key) );

        databaseReference.child("MyPosts").child(SplashScreen.trimmedMail).child(key).setValue(new ModelPostRequest(SplashScreen.name,phone,bloodGroup,address,formatter.format(date)+"",district, division, key) );

        startActivity(new Intent(PostRequest.this,SplashScreen.class) );
    }
}