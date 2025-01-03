package com.example.blutspende;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.example.blutspende.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListOfDonors extends AppCompatActivity {
    private Bundle bundle;
    private ProgressBar progressBar;
    private String division, district, bloodGroup;
    private List<ModelRecyclerView> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Connector connector;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatePicker datePicker;
    private int presentDay, presentMonth, presentYear;
    private Button button;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_donors);
        button = findViewById(R.id.bloodRequestButton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        datePicker = findViewById(R.id.listOfDonorsDatePicker);
        progressBar = findViewById(R.id.progressBarListOfDonors);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_ATOP);
        presentDay = datePicker.getDayOfMonth();
        presentMonth = datePicker.getMonth() + 1;
        presentYear = datePicker.getYear();

        Calendar cal = Calendar.getInstance();


        cal.set(Calendar.DAY_OF_MONTH, presentDay);
        cal.set(Calendar.MONTH,presentMonth);
        cal.set(Calendar.YEAR, presentYear);
        Date secondDate = cal.getTime();


        bundle = getIntent().getExtras();
        division = bundle.getString("division");
        district = bundle.getString("district");
        bloodGroup = bundle.getString("bloodGroup");

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        connector = new Connector(list, this);
        recyclerView.setAdapter(connector);

        databaseReference.child("donors").child(division).child(district).child(bloodGroup).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ModelNormalUser modelDonors = snapshot1.getValue(ModelNormalUser.class);
                        System.out.println(modelDonors.toString());

                        if (modelDonors.getDay() != null) {
                            try {
                                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(modelDonors.getDay()));
                            } catch(NumberFormatException e) {
                                // Deal with the situation like
                                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt("1"));
                            }
                        }
                        if (modelDonors.getDay() != null) {
                            try {
                                cal.set(Calendar.MONTH, Integer.parseInt(modelDonors.getMonth()));
                            } catch(NumberFormatException e) {
                                // Deal with the situation like
                                cal.set(Calendar.MONTH, Integer.parseInt("1"));
                            }
                        }
                        if (modelDonors.getDay() != null) {
                            try {
                                cal.set(Calendar.YEAR, Integer.parseInt(modelDonors.getYear()));
                            } catch(NumberFormatException e) {
                                // Deal with the situation like
                                cal.set(Calendar.YEAR, Integer.parseInt("2000"));
                            }
                        }




                        Date firstDate = cal.getTime();
                        long diff = secondDate.getTime() - firstDate.getTime();

                        if ((diff / 1000 / 60 / 60 / 24) > 56 || (diff / 1000 / 60 / 60 / 24) < 0) {
                            ModelRecyclerView model = new ModelRecyclerView(modelDonors.getUsername(), modelDonors.getPhone(), modelDonors.getPictureLink(), modelDonors.getBloodGroup());
                            if (!(model.getUserName().equals(SplashScreen.name) && model.getPhoneNumber().equals(SplashScreen.contactNumber))){
                                list.add(model);
                            }

                        }
                    }
                    if (list.size() == 0) {
                        list.add(new ModelRecyclerView("No Donor !!", "999", "https://www.pngitem.com/pimgs/m/22-220721_circled-user-male-type-user-colorful-icon-png.png?fbclid=IwAR3VIKtfsa7rYCRsLWYKwJ6WS0Ga54kQtLxQAowOiK55zPgD8h7QeMXwr-0", ""));
                    }
                    connector.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    list.add(new ModelRecyclerView("No Donor !!", "999", "https://www.pngitem.com/pimgs/m/22-220721_circled-user-male-type-user-colorful-icon-png.png?fbclid=IwAR3VIKtfsa7rYCRsLWYKwJ6WS0Ga54kQtLxQAowOiK55zPgD8h7QeMXwr-0", ""));
                    connector.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListOfDonors.this,PostRequest.class);
                intent.putExtra("div",division);
                intent.putExtra("dis",district);
                intent.putExtra("bg",bloodGroup);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}