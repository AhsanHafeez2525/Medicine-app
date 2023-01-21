package com.example.pakmedicine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.MyViewHolder>  {

    private Context context;
    List<FormulaModel> Data;
    String layout;
    public FormulaAdapter(Context con,List<FormulaModel> data,String txt) {

        context=con;
        Data=data;
        layout=txt;
    }
    @NonNull
    @Override
    public FormulaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.formulalistitem,  parent, false);
        return new FormulaAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final FormulaAdapter.MyViewHolder holder, final int position) {
        holder.name.append(Data.get(position).name);
        holder.overview.append(Data.get(position).overview);
        holder.effect.append(Data.get(position).effects);
        holder.risk.append(Data.get(position).risk);
        holder.warning.append(Data.get(position).warning);
        holder.contra.append(Data.get(position).contradiction);
        holder.dosage.append(Data.get(position).dosage);


    }



    @Override
    public int getItemCount() {



        return Data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,overview,effect,risk,warning,contra,dosage;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            overview=itemView.findViewById(R.id.overview);
            effect=itemView.findViewById(R.id.effect);
            risk=itemView.findViewById(R.id.risk);
            warning=itemView.findViewById(R.id.warning);
            contra=itemView.findViewById(R.id.contra);
            dosage=itemView.findViewById(R.id.dosage);


        }
    }


}