package com.example.pakmedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brand_Screen extends AppCompatActivity {
String brand_name;
TextView txt1,txt2,txt3,fount_txt;
RecyclerView recyclerView;
Brand_details_adapter adapter;
List<String> data=new ArrayList<>();

    ProgressDialog process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_screen);
        brand_name=getIntent().getStringExtra("text");
        fount_txt=findViewById(R.id.found_txt);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        recyclerView=findViewById(R.id.recyclerview);
        process = new ProgressDialog(Brand_Screen.this);
        process.setMessage("Fetching Record...");
        process.setCancelable(false);
        data.clear();
        fetching_available_forms();

    }

    public void open_alternate_brands(View view) {
        txt1.setTextColor(getResources().getColor(R.color.white));
        txt2.setTextColor(getResources().getColor(R.color.white));
        txt3.setTextColor(getResources().getColor(R.color.yellow));
        data.clear();
        fetching_alternate_brands();
    }



    public void open_include_drugs(View view) {
        txt1.setTextColor(getResources().getColor(R.color.white));
        txt2.setTextColor(getResources().getColor(R.color.yellow));
        txt3.setTextColor(getResources().getColor(R.color.white));
        data.clear();

        fetching_include_drugs();
    }


    public void open_available_forms(View view) {
        txt1.setTextColor(getResources().getColor(R.color.yellow));
        txt2.setTextColor(getResources().getColor(R.color.white));
        txt3.setTextColor(getResources().getColor(R.color.white));
        data.clear();
        fetching_available_forms();
    }

    // service
    private void fetching_available_forms2() {
        process.show();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("b_name",brand_name);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"AvailableForms",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String wcfresponse = null;
                        try
                        {
                            if(response.isNull("AvailableFormsResult"))
                            {
                                fount_txt.setVisibility(View.VISIBLE);
                            }
                            else {
                                fount_txt.setVisibility(View.GONE);
                                JSONArray object = response.getJSONArray("AvailableFormsResult");
                                for (int i = 0; i < object.length(); i++) {
                                    data.add(object.getString(i));
                                }
                                adapter = new Brand_details_adapter(Brand_Screen.this, data, "available_forms");
                                recyclerView.setLayoutManager(new LinearLayoutManager(Brand_Screen.this, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                            }
                            process.dismiss();
                        } catch (Exception e)
                        {
                            process.dismiss();
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
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

    private void fetching_available_forms(){
        process.show();
        DatabaseHelper db=new DatabaseHelper(this);
        try {
            Cursor cursor=db.AvailableForms(brand_name);
            if(cursor==null)
            {
                fount_txt.setVisibility(View.VISIBLE);
            }
            else {
                fount_txt.setVisibility(View.GONE);
               while (cursor.moveToNext()){
                    data.add(cursor.getString(0));
                }
                adapter = new Brand_details_adapter(Brand_Screen.this, data, "available_forms");
                recyclerView.setLayoutManager(new LinearLayoutManager(Brand_Screen.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        process.dismiss();

    }
    // service
    private void fetching_alternate_brands2() {
          process.show();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("b_name",brand_name);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"AlternateBrand",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String wcfresponse = null;
                        try
                        {
                            if(response.isNull("AlternateBrandResult"))
                            {
                                fount_txt.setVisibility(View.VISIBLE);
                            }
                            else {
                                fount_txt.setVisibility(View.GONE);
                                JSONArray object = response.getJSONArray("AlternateBrandResult");
                                for (int i = 0; i < object.length(); i++) {
                                    data.add(object.getString(i));
                                }
                                adapter = new Brand_details_adapter(Brand_Screen.this, data, "alternate_brand");
                                recyclerView.setLayoutManager(new LinearLayoutManager(Brand_Screen.this, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                            }
                            process.dismiss();
                        } catch (Exception e)
                        {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

                        process.dismiss();
                        }
                    }
                }, new Response.ErrorListener()
        {


            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText(getApplicationContext(),"Error Try Again",Toast.LENGTH_SHORT).show();
                process.dismiss();

            }


        }

        );
        queue.add(jor);
    }

    private void fetching_alternate_brands(){
        DatabaseHelper db=new DatabaseHelper(this);
        process.show();
        try {
           Cursor cursor=db.AlternateBrand(brand_name);
            if(cursor==null)
            {
                fount_txt.setVisibility(View.VISIBLE);
            }
            else {
                fount_txt.setVisibility(View.GONE);

                while (cursor.moveToNext()){
                    data.add(cursor.getString(0));
                }
                adapter = new Brand_details_adapter(Brand_Screen.this, data, "alternate_brand");
                recyclerView.setLayoutManager(new LinearLayoutManager(Brand_Screen.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        process.dismiss();
        db.close();
    }
    // service
    private void fetching_include_drugs2() {
        process.show();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("b_name",brand_name);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"IncludeDrugs",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String wcfresponse = null;
                        try {
                            if (response.isNull("IncludeDrugsResult")) {
                                fount_txt.setVisibility(View.VISIBLE);
                            }
                            else {
                                fount_txt.setVisibility(View.GONE);
                            JSONArray object = response.getJSONArray("IncludeDrugsResult");
                            for (int i = 0; i < object.length(); i++) {
                                data.add(object.getString(i));
                            }
                            adapter = new Brand_details_adapter(Brand_Screen.this, data, "include_drugs");
                            recyclerView.setLayoutManager(new LinearLayoutManager(Brand_Screen.this, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                            process.dismiss();
                        } catch (Exception e)
                        {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        process.dismiss();
                        }
                    }
                }, new Response.ErrorListener()
        {


            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText(getApplicationContext(),"Error Try Again",Toast.LENGTH_SHORT).show();
                process.dismiss();
            }


        }

        );
        queue.add(jor);
    }

    private void fetching_include_drugs() {
        process.show();
        DatabaseHelper db=new DatabaseHelper(this);
        try {
            Cursor cursor = db.IncludeDrugs(brand_name);
            if (cursor == null) {
                fount_txt.setVisibility(View.VISIBLE);
            } else {
                fount_txt.setVisibility(View.GONE);
                while (cursor.moveToNext()) {
                    data.add(cursor.getString(0));
                }

                adapter = new Brand_details_adapter(Brand_Screen.this, data, "include_drugs");
                recyclerView.setLayoutManager(new LinearLayoutManager(Brand_Screen.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        }
        catch ( Exception e){
            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
        process.dismiss();
        db.close();
    }
}
