package com.example.pakmedicine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText search_text;
RadioGroup radioGroup;
String tag="Brand";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_text=findViewById(R.id.search_text);
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.brand)
                    tag="Brand";
                else if(checkedId==R.id.company)
                tag="Company";
                else
                    tag="Formula";

            }
        });

    }

    public void search(View view) {
        if(search_text.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Plz Enter The Field First",Toast.LENGTH_SHORT).show();
        }
        else {


            if (tag.equalsIgnoreCase("formula")) {
                Intent intent = new Intent(MainActivity.this, Formula_Searching_Screen.class);
                intent.putExtra("text", search_text.getText().toString());
                startActivity(intent);
            }
            else if(tag.equalsIgnoreCase("company"))
            {
                Intent intent=new Intent(MainActivity.this,Only_Brand_List.class);
                intent.putExtra("c_name",search_text.getText().toString());
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, Brand_Screen.class);
                intent.putExtra("text", search_text.getText().toString());
                startActivity(intent);
            }
        }
    }



    private void Show_Add_Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_medicine, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText formula,overview,effect,risk,warning,contradiction;
        Button update;
        formula=dialogView.findViewById(R.id.formula);
        overview=dialogView.findViewById(R.id.overview);
        effect=dialogView.findViewById(R.id.effects);
        risk=dialogView.findViewById(R.id.risk);
        warning=dialogView.findViewById(R.id.warning);
        contradiction=dialogView.findViewById(R.id.contraindication);
        update=dialogView.findViewById(R.id.update_btn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db=new DatabaseHelper(getApplicationContext());
             long res= db.insertformula(formula.getText().toString(),overview.getText().toString(),effect.getText().toString(),risk.getText().toString(),warning.getText().toString(),contradiction.getText().toString());

             if(res==-1)
                 Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();
             else
             {
                 Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                 alertDialog.dismiss();
             }
             db.close();
            }
        });
    }

    public void Add_Medicines(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.login_dialog, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText name,pass;
        Button login=dialogView.findViewById(R.id.login_btn);
        name=dialogView.findViewById(R.id.u_name);
        pass=dialogView.findViewById(R.id.pass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("admin")&&pass.getText().toString().equals("admin")){
                    alertDialog.dismiss();
                    Show_Add_Dialog();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void ShowFormulaAll(View view) {
        startActivity(new Intent(this,ShowAllFormula.class));
    }
}
