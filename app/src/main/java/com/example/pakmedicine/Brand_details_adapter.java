package com.example.pakmedicine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Brand_details_adapter extends RecyclerView.Adapter<Brand_details_adapter.MyViewHolder>  {

    private Context context;
    List<String> Data;
    String layout;
    public Brand_details_adapter(Context con,List<String> data,String txt) {

        context=con;
        Data=data;
        layout=txt;
    }
    @NonNull
    @Override
    public Brand_details_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item,  parent, false);
        return new Brand_details_adapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final Brand_details_adapter.MyViewHolder holder, final int position) {
        holder.name.setText(Data.get(position));
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
 if(layout.equalsIgnoreCase("available_forms")){
     Intent intent=new Intent(context,Available_Forms_Details.class);
    intent.putExtra ("b_name",((Brand_Screen)context).brand_name);
     intent.putExtra ("type",Data.get(position));
     context.startActivity(intent);
 }
 else if(layout.equalsIgnoreCase("alternate_brand")){
     Intent intent=new Intent(context,Brand_Screen.class);
     intent.putExtra("text",Data.get(position));
     context.startActivity(intent);
 }
 else {
     Intent intent=new Intent(context,Formula_Searching_Screen.class);
     intent.putExtra("text",Data.get(position));
     context.startActivity(intent);
 }
         }
     });
    }



    @Override
    public int getItemCount() {



        return Data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);

        }
    }


}
