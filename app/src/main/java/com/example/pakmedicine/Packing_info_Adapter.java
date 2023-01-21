package com.example.pakmedicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Packing_info_Adapter extends RecyclerView.Adapter<Packing_info_Adapter.MyViewHolder>  {

    private Context context;
    List<packinginfo_model_class> Data;

    public Packing_info_Adapter(Context con,List<packinginfo_model_class> data) {

        context=con;
        Data=data;

    }
    @NonNull
    @Override
    public Packing_info_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_for_packinginfo,  parent, false);
        return new Packing_info_Adapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final Packing_info_Adapter.MyViewHolder holder, final int position) {
        holder.txt1.setText(Data.get(position).getGram());
        holder.txt3.setText(Data.get(position).getPrice());

    }



    @Override
    public int getItemCount() {



        return Data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt1,txt3;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt1=itemView.findViewById(R.id.txt1);
            txt3=itemView.findViewById(R.id.txt3);

        }
    }


}
