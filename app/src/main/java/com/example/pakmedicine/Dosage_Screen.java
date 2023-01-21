package com.example.pakmedicine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dosage_Screen extends AppCompatActivity {
TextView adult_dose,adult_singledose,adult_freq,adult_route,adult_instr,
    neon_dose,neon_singledose,neon_freq,neon_route,neon_instr,
    paed_dose,paed_singledose,paed_freq,paed_route,paed_instr;
     int F_ID,M_ID;
    ProgressDialog process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage_screen);
        F_ID=getIntent().getIntExtra("F_ID",1);
        process = new ProgressDialog(Dosage_Screen.this);
        process.setMessage("Fetching Record...");
        process.setCancelable(false);
        Int();
    }

    private void Int() {
        adult_dose=findViewById(R.id.adult_dose);
        adult_singledose=findViewById(R.id.adult_singledose);
        adult_freq=findViewById(R.id.adult_frequency);
        adult_route=findViewById(R.id.adult_route);
        adult_instr=findViewById(R.id.adult_instruction);

        neon_dose=findViewById(R.id.neon_dose);
        neon_singledose=findViewById(R.id.neon_singledose);
        neon_freq=findViewById(R.id.neon_freq);
        neon_route=findViewById(R.id.neon_route);
        neon_instr=findViewById(R.id.neon_instruction);

        paed_dose=findViewById(R.id.paed_dose);
        paed_singledose=findViewById(R.id.paed_singledose);
        paed_freq=findViewById(R.id.paed_freq);
        paed_route=findViewById(R.id.paed_route);
        paed_instr=findViewById(R.id.paed_instruction);
        fetching_info();
    }
     // online service
    private void fetching_info2() {
        process.show();
        Map<String, Integer> jsonParams = new HashMap<>();
        jsonParams.put("f_id",F_ID);
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"MedicineDetails",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try
                        {
                            JSONObject object=   response.getJSONObject("MedicineDetailsResult");
                            String adult=object.getString("Adult");
                            String neon=object.getString("Neonatal");
                            String paed=object.getString("Paedriatic");
                            if(adult.contains(",")){
                                String adultarray[]=adult.split(",");
                                adult_dose.setText(adultarray[0]);
                                adult_singledose.setText(adultarray[1]);
                                adult_freq.setText(adultarray[2]);
                                adult_route.setText(adultarray[3]);
                                adult_instr.setText(adultarray[4]);
                            }
                            else {
                                adult_instr.setText("Not recommended in this age");
                            }
                            if(neon.contains(",")){
                                String adultarray[]=neon.split(",");

                                neon_dose.setText(adultarray[0]);
                                neon_singledose.setText(adultarray[1]);
                                neon_freq.setText(adultarray[2]);
                                neon_route.setText(adultarray[3]);
                                neon_instr.setText(adultarray[4]);
                            }
                            else{
                                neon_instr.setText("Not recommended in this age");
                            }

                            if(paed.contains(",")){

                                String adultarray[]=paed.split(",");
                                paed_dose.setText(adultarray[0]);
                                paed_singledose.setText(adultarray[1]);
                                paed_freq.setText(adultarray[2]);
                                paed_route.setText(adultarray[3]);
                                paed_instr.setText(adultarray[4]);
                            }
                            else {
                                paed_instr.setText("Not recommended in this age");
                            }
                         process.dismiss();
                        } catch (Exception e)
                        { process.dismiss();
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
        {


            @Override
            public void onErrorResponse(VolleyError error)
            { process.dismiss();

                Toast.makeText(getApplicationContext(),"Error Try Again",Toast.LENGTH_SHORT).show();


            }


        }

        );
        queue.add(jor);
    }
    public void fetching_info(){
        DatabaseHelper db=new DatabaseHelper(this);
        process.show();
        try {
         Cursor cursor= db.MedicineDetails(F_ID);
           if(cursor!=null){
               cursor.moveToFirst();
               M_ID=cursor.getInt(0);
               String adult=cursor.getString(3);
               String neon=cursor.getString(4);
               String paed=cursor.getString(5);
               if(adult.contains(",")){
                   String adultarray[]=adult.split(",");
                   adult_dose.setText(adultarray[0]);
                   adult_singledose.setText(adultarray[1]);
                   adult_freq.setText(adultarray[2]);
                   adult_route.setText(adultarray[3]);
                   adult_instr.setText(adultarray[4]);
               }
               else {
                   adult_instr.setText("Not recommended in this age");
               }
               if(neon.contains(",")){
                   String adultarray[]=neon.split(",");

                   neon_dose.setText(adultarray[0]);
                   neon_singledose.setText(adultarray[1]);
                   neon_freq.setText(adultarray[2]);
                   neon_route.setText(adultarray[3]);
                   neon_instr.setText(adultarray[4]);
               }
               else{
                   neon_instr.setText("Not recommended in this age");
               }

               if(paed.contains(",")){

                   String adultarray[]=paed.split(",");
                   paed_dose.setText(adultarray[0]);
                   paed_singledose.setText(adultarray[1]);
                   paed_freq.setText(adultarray[2]);
                   paed_route.setText(adultarray[3]);
                   paed_instr.setText(adultarray[4]);
               }
               else {
                   paed_instr.setText("Not recommended in this age");
               }
           }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        process.dismiss();

    }
    private void show_login_dialog(int no) {


    }

    public void show_login_dialog(final View view) {
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
                    show_edit_dialog(view.getTag().toString());

                }
                else
                    Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void show_edit_dialog(final String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dosage_editor, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText dose,singledose,frequency,route,instruction;
        Button update,clear;
        dose=dialogView.findViewById(R.id.dose);
        singledose=dialogView.findViewById(R.id.singledose);
        frequency=dialogView.findViewById(R.id.frequency);
        route=dialogView.findViewById(R.id.route);
        instruction=dialogView.findViewById(R.id.instruction);
        if(type.equalsIgnoreCase("adult")) {
            dose.setText(this.adult_dose.getText());
            singledose.setText(this.adult_singledose.getText());
            frequency.setText(this.adult_freq.getText());
            route.setText(this.adult_route.getText());
            instruction.setText(this.adult_instr.getText());
        }
        else if(type.equalsIgnoreCase("Neonatal")){
            dose.setText(this.neon_dose.getText());
            singledose.setText(this.neon_singledose.getText());
            frequency.setText(this.neon_freq.getText());
            route.setText(this.neon_route.getText());
            instruction.setText(this.neon_instr.getText());
        }
        else {
            dose.setText(this.paed_dose.getText());
            singledose.setText(this.paed_singledose.getText());
            frequency.setText(this.paed_freq.getText());
            route.setText(this.paed_route.getText());
            instruction.setText(this.paed_instr.getText());
        }
        update=dialogView.findViewById(R.id.update_btn);
        clear=dialogView.findViewById(R.id.clear_btn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dose.setText("");
                singledose.setText("");
                frequency.setText("");
                route.setText("");
                instruction.setText("");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db=new DatabaseHelper(getApplicationContext());
                int res=db.Update_Dosage(M_ID,F_ID,type,dose.getText().toString()+","+singledose.getText().toString()+","+frequency.getText().toString()+","+route.getText().toString()+","+instruction.getText().toString());
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
    private void referesh() {
        this.recreate();
    }
}
