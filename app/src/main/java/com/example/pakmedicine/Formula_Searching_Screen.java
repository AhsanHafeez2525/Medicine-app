package com.example.pakmedicine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formula_Searching_Screen extends AppCompatActivity {
TextView overview,effects,risk,warning,found_txt,contraindication;
static Context mcontext;
String text;
int F_ID;
Button dosagebtn,brandbtn;
ScrollView info_layout;
    ProgressDialog process;
    String contradication_txt;

    public static List<Integer> fiid=new ArrayList<>();
    Spinner spinner;
    List<String> list=new ArrayList<>();
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_searching_screen);
        mcontext=Formula_Searching_Screen.this;
        process = new ProgressDialog(Formula_Searching_Screen.this);
        process.setMessage("Fetching Record...");
        process.setCancelable(false);
        text=getIntent().getStringExtra("text");
        brandbtn=findViewById(R.id.brand_btn);
        dosagebtn=findViewById(R.id.dosage_btn);
        info_layout=findViewById(R.id.info_layout);
        found_txt=findViewById(R.id.found_txt);
        overview=findViewById(R.id.overview);
        effects=findViewById(R.id.effects);
        risk=findViewById(R.id.risk);
        spinner=findViewById(R.id.spinner);
        warning=findViewById(R.id.warning);
        contraindication=findViewById(R.id.contraindication);
        contraindication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Formula_Searching_Screen.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.conta_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                RecyclerView recyclerView=dialogView.findViewById(R.id.recyclerview);

                List<String> formulas=new ArrayList<>();
                 String items[]=contradication_txt.split(",");
                for (String item: items) {
                    formulas.add(item);
                }
                Contradication_Adapter adapter=new Contradication_Adapter(Formula_Searching_Screen.this,formulas,alertDialog);
                recyclerView.setLayoutManager(new LinearLayoutManager(Formula_Searching_Screen.this));
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }
        });
        fetching_info();
        fetchallmedicine();
    }
   // service
    private void fetching_info2() {
        process.show();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("F_Name",text);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"ByFormula",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try
                        {
                        if(response.isNull("ByFormulaResult")){
                            info_layout.setVisibility(View.GONE);
                            found_txt.setVisibility(View.VISIBLE);
                            dosagebtn.setClickable(false);
                            brandbtn.setClickable(false);
                        }
                        else {
                            JSONObject object=   response.getJSONObject("ByFormulaResult");

                            info_layout.setVisibility(View.VISIBLE);
                            found_txt.setVisibility(View.GONE);
                            dosagebtn.setClickable(true);
                            brandbtn.setClickable(true);

                            overview.setText(object.getString("Overview"));
                            effects.setText(object.getString("Effects"));
                            risk.setText(object.getString("Risk"));
                            warning.setText(object.getString("Warning"));
                            contradication_txt=object.getString("Contraindication");
                            contraindication.setText("This Formula contraindicated in conditions like "+object.getString("Contraindication"));
                            F_ID = object.getInt("F_ID");
                        }
                        process.dismiss();
                        } catch (Exception e)
                        {
                            process.dismiss();
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"Parsing Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
        {


            @Override
            public void onErrorResponse(VolleyError error)
            {
                process.dismiss();
                Toast.makeText(getApplicationContext(),"Error Try Again",Toast.LENGTH_SHORT).show();


            }


        }

        );
        queue.add(jor);
    }
    private void fetching_info() {
        process.show();
        DatabaseHelper db=new DatabaseHelper(this);
        try{

        Cursor cursor=db.SearchByFormula(text);
        if(cursor!=null){
            cursor.moveToFirst();
            info_layout.setVisibility(View.VISIBLE);
            found_txt.setVisibility(View.GONE);
            dosagebtn.setClickable(true);
            brandbtn.setClickable(true);

            overview.setText(cursor.getString(2));
            effects.setText(cursor.getString(3));
            risk.setText(cursor.getString(4));
            warning.setText(cursor.getString(5));
            contradication_txt=cursor.getString(6);
            contraindication.setText("This Formula contraindicated in conditions like "+cursor.getString(6));
            F_ID = cursor.getInt(0);
        }
        else {
            info_layout.setVisibility(View.GONE);
            found_txt.setVisibility(View.VISIBLE);
            dosagebtn.setClickable(false);
            brandbtn.setClickable(false);
        }

        }
        catch (Exception e){

            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        process.dismiss();
        db.close();

    }

    public void open_brand(View view) {
        Intent intent=new Intent(Formula_Searching_Screen.this,Only_Brand_List.class);
        intent.putExtra("b_name",F_ID);
        startActivity(intent);
    }

    public void open_dosage(View view) {
        Intent intent=new Intent(Formula_Searching_Screen.this,Dosage_Screen.class);
        intent.putExtra("F_ID",F_ID);
        startActivity(intent);
    }


    public void Edited(View view) {
        show_login_dialog();
    }

    private void show_login_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Formula_Searching_Screen.this);
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
                    show_edit_dialog();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
    private void show_edit_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Formula_Searching_Screen.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.formula_editer, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText overview,effect,risk,warning,contradiction;
        Button update,clear;
        overview=dialogView.findViewById(R.id.overview);
        effect=dialogView.findViewById(R.id.effects);
        risk=dialogView.findViewById(R.id.risk);
        warning=dialogView.findViewById(R.id.warning);
        contradiction=dialogView.findViewById(R.id.contraindication);
        overview.setText(this.overview.getText());
        effect.setText(this.effects.getText());
        risk.setText(this.risk.getText());
        warning.setText(this.warning.getText());
        contradiction.setText(this.contradication_txt);
        update=dialogView.findViewById(R.id.update_btn);
        clear=dialogView.findViewById(R.id.clear_btn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overview.setText("");
                effect.setText("");
                risk.setText("");
                warning.setText("");
                contradiction.setText("");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DatabaseHelper db=new DatabaseHelper(getApplicationContext());
            int res=db.Update_Formula(F_ID,overview.getText().toString(),effect.getText().toString(),risk.getText().toString(),warning.getText().toString(),contradiction.getText().toString());
            if(res==1) {
                Toast.makeText(getApplicationContext(), "Update Done", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                referesh();
            }
                 else
                Toast.makeText(getApplicationContext(),"Try Again ",Toast.LENGTH_SHORT).show();

                 db.close();
            }
        });
    }

    private void fetchallmedicine()
    {
        DatabaseHelper db=new DatabaseHelper(this);

        list=db.FetchAllMedicine();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flag) {
                    Intent intent = new Intent(Formula_Searching_Screen.this, Dosage_Screen.class);
                    intent.putExtra("F_ID", fiid.get(position));
                    startActivity(intent);
                }
                flag=true;
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

    }
    private void referesh() {
        this.recreate();
    }

}
