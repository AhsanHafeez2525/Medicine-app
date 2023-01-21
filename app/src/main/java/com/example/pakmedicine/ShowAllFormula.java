package com.example.pakmedicine;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowAllFormula extends AppCompatActivity {
    RecyclerView recyclerView;
    List<FormulaModel> list=new ArrayList<>();
    FormulaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showallformula);
        recyclerView=findViewById(R.id.rv);
        DatabaseHelper db=new DatabaseHelper(this);
        Cursor cursor=  db.AllFormula();
        while (cursor.moveToNext()){

            list.add(new FormulaModel(

                    cursor.getInt(0),
                    cursor.getString(1),
                   cursor.getString(2),
          cursor.getString(3),
            cursor.getString(4),
           cursor.getString(5),
          cursor.getString(6),
                    cursor.getString(7)

            ));

        }
        adapter = new FormulaAdapter(ShowAllFormula.this, list, "available_forms");
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllFormula.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
