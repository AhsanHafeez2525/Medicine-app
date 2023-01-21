package com.example.pakmedicine;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class Contradication_Adapter  extends RecyclerView.Adapter<Contradication_Adapter.MyViewHolder> {
    boolean type;
    Context context;
    List<String> formula;
AlertDialog alertDialog;
    public Contradication_Adapter(Context context, List<String> formula, AlertDialog alertDialog) {
        type=false;
        this.context=context;
        this.formula=formula;
        this.alertDialog=alertDialog;
    }

    public Contradication_Adapter(Context context, List<String> formulas) {
        this.context=context;
        type=true;
        this.formula=formulas;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contra_item_layout,parent,false);
        return new MyViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.name.setText(formula.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type){
                    Intent intent = new Intent(context, Formula_Searching_Screen.class);
                    intent.putExtra("text", formula.get(position));
                  context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, contra_list.class);
                    intent.putExtra("name", formula.get(position));
                    context.startActivity(intent);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return formula.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);


        }

    }
}
