package com.example.blutspende.ui.myPosts;

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
import com.example.blutspende.AdapterMyPosts;
import com.example.blutspende.ModelPostRequest;
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

public class MyPosts extends Fragment {
    private FragmentHomeBinding binding;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterMyPosts adapter;
    private List<ModelPostRequest> list;

    public static MyPosts newInstance() {
        return new MyPosts();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_posts, container, false);

        // Get access to the buttons
        // button = (Button)rootView.findViewById(R.id.bloodRequestButton);
        recyclerView=(RecyclerView) rootView.findViewById(R.id.myPostsRecyclerView);

        list=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new AdapterMyPosts(list,getContext());
        recyclerView.setAdapter(adapter);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

            databaseReference.child("MyPosts").child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot x: snapshot.getChildren()){
                            ModelPostRequest model=x.getValue(ModelPostRequest.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        list.add(new ModelPostRequest("No Post Yet","","","","","","",""));
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        return rootView;


    }
}