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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterBloodRequest extends RecyclerView.Adapter<AdapterBloodRequest.ViewHolder> {
    private List<ModelPostRequest> list;
    private Context context;

    public AdapterBloodRequest(List<ModelPostRequest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterBloodRequest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext() ).inflate(R.layout.model_post_request,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBloodRequest.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(list.get(position).getUserName() );
        holder.bloodGroup.setText(list.get(position).getBloodGroup() );
        holder.contact.setText(list.get(position).getPhoneNumber() );
        holder.date.setText(list.get(position).getDate() );
        holder.location.setText(list.get(position).getLocation() );
        if (!list.get(position).getBloodGroup().equals("")){
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+list.get(position).getPhoneNumber()));
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,bloodGroup,contact,date,location;
        private ImageView call;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            call=itemView.findViewById(R.id.modelPostRequestCallButton);
            name=itemView.findViewById(R.id.modelPostRequestUserName);
            contact=itemView.findViewById(R.id.modelPostRequestPhone);
            bloodGroup=itemView.findViewById(R.id.modelPostRequestBloodGroup);
            date=itemView.findViewById(R.id.modelPostRequestDate);
            location=itemView.findViewById(R.id.modelPostRequestLocation);
        }
    }
}
