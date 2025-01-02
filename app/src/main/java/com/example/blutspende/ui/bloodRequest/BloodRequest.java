package com.example.blutspende.ui.bloodRequest;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.blutspende.AdapterBloodRequest;
import com.example.blutspende.ModelPostRequest;
import com.example.blutspende.ModelRecyclerView;
import com.example.blutspende.PostRequest;
import com.example.blutspende.R;
import com.example.blutspende.SplashScreen;
import com.example.blutspende.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BloodRequest extends Fragment {
    private FragmentHomeBinding binding;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterBloodRequest adapter;
    private List<ModelPostRequest> list;

    public static BloodRequest newInstance() {
        return new BloodRequest();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blood_request, container, false);

        // Get access to the buttons
       // button = (Button)rootView.findViewById(R.id.bloodRequestButton);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.bloodRequestRecyclerView);

        list=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new AdapterBloodRequest(list,getContext());
        recyclerView.setAdapter(adapter);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        if(SplashScreen.div.equals("") ){
            databaseReference.child("PostRequests").child("default").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    if(snapshot.exists()){
                        for (DataSnapshot x:
                                snapshot.getChildren()) {
                            ModelPostRequest modelPostRequest=x.getValue(ModelPostRequest.class);
                            list.add(modelPostRequest);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        list.add(new ModelPostRequest("No User","999","","","", "", "", "") );
                        adapter.notifyDataSetChanged();
                        // Toast.makeText(getContext(), "No Data!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            databaseReference.child("PostRequests").child(SplashScreen.div).child(SplashScreen.dis).child(SplashScreen.bg).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    if(snapshot.exists()){
                        for (DataSnapshot x:
                                snapshot.getChildren()) {
                            ModelPostRequest modelPostRequest=x.getValue(ModelPostRequest.class);
                            if (!(modelPostRequest.getUserName().equals(SplashScreen.name) && modelPostRequest.getPhoneNumber().equals(SplashScreen.contactNumber))){
                                list.add(modelPostRequest);
                            }

                        }
                        if (list.size() == 0) {
                            list.add(new ModelPostRequest("No Posts Yet","","","","","", "", ""));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        list.add(new ModelPostRequest("No Post","","","","","","","") );
                        adapter.notifyDataSetChanged();
                        // Toast.makeText(getContext(), "No Data!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }





//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent(getContext(),PostRequest.class ));
//            }
//        });

        return rootView;


    }


    public void goToPostRequest(View view) {
        startActivity( new Intent(getContext(), PostRequest.class) );
    }
}