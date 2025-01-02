package com.example.blutspende;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMyPosts extends RecyclerView.Adapter<AdapterMyPosts.ViewHolder> {
    private List<ModelPostRequest> list;
    private Context context;

    public AdapterMyPosts(List<ModelPostRequest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMyPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext() ).inflate(R.layout.model_my_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyPosts.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(list.get(position).getUserName() );
        holder.bloodGroup.setText(list.get(position).getBloodGroup() );
        holder.contact.setText(list.get(position).getPhoneNumber() );
        holder.date.setText(list.get(position).getDate() );
        holder.location.setText(list.get(position).getLocation() );

        if (list.get(position).getDate()==""){
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });}
        else{
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+list.get(position).getPhoneNumber()));
                    context.startActivity(intent);
                }
            });
        }
        if (list.get(position).getDate()==""){
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else{holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("MyPosts").child(SplashScreen.trimmedMail).child(list.get(position).getKey()).removeValue();
                databaseReference.child("PostRequests").child(list.get(position).getInputDiv()).child(list.get(position).getInputDistrict()).child(list.get(position).getBloodGroup()).child(list.get(position).getKey()).removeValue();
                Toast.makeText(context, "Deleted your post !", Toast.LENGTH_SHORT).show();
            }
        });}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,bloodGroup,contact,date,location;
        private ImageView call, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            call=itemView.findViewById(R.id.modelMyPostCallButton);
            name=itemView.findViewById(R.id.modelMyPostUserName);
            contact=itemView.findViewById(R.id.modelMyPostPhone);
            bloodGroup=itemView.findViewById(R.id.modelMyPostBloodGroup);
            date=itemView.findViewById(R.id.modelMyPostDate);
            location=itemView.findViewById(R.id.modelMyPostLocation);
            delete = itemView.findViewById(R.id.modelMyPostDeleteButton);
        }
    }
}
