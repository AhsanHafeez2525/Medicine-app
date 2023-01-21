package com.example.pakmedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Only_Brand_List extends AppCompatActivity {
    String b_name,c_name;
    RecyclerView recyclerView;
    Brand_details_adapter adapter;
    List<String> data=new ArrayList<>();
    int f_id;
    ProgressDialog process;
    boolean flg=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only__brand__list);
        recyclerView=findViewById(R.id.recyclerview);

        c_name = getIntent().getStringExtra("c_name");
        f_id=getIntent().getIntExtra("b_name",0);
        process = new ProgressDialog(Only_Brand_List.this);
        process.setMessage("Fetching Record...");
        process.setCancelable(false);
        if(c_name!=null)
            fetching_info_by_com();
        else
        fetching_info();
    }
 // service
    private void fetching_info_by_com2() {
        data.clear();
        process.show();
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("C_Name",c_name);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"ByCompany",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String wcfresponse = null;
                        try
                        {
                            JSONArray object=   response.getJSONArray("ByCompanyResult");
                            for (int i=0;i<object.length();i++){
                                data.add(object.getString(i));
                            }
                            adapter=new Brand_details_adapter(Only_Brand_List.this,data,"alternate_brand");
                            recyclerView.setLayoutManager(new LinearLayoutManager(Only_Brand_List.this, LinearLayoutManager.VERTICAL,false));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                            process.dismiss();

                        }
                        catch (Exception e)
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
    private void fetching_info_by_com(){
        process.show();
        DatabaseHelper db=new DatabaseHelper(this);
        try {
          Cursor cursor= db.ByCompany(c_name);
          if(cursor!=null){
              while (cursor.moveToNext()){
                      data.add(cursor.getString(0));
              }
              adapter=new Brand_details_adapter(Only_Brand_List.this,data,"alternate_brand");
              recyclerView.setLayoutManager(new LinearLayoutManager(Only_Brand_List.this, LinearLayoutManager.VERTICAL,false));
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
    private void fetching_info2() {
        data.clear();
        process.show();
        Map<String, Integer> jsonParams = new HashMap<>();
        jsonParams.put("fid",f_id);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"AlternateBrandByID",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String wcfresponse = null;
                        try
                        {
                            JSONArray object=   response.getJSONArray("AlternateBrandByIDResult");
                            for (int i=0;i<object.length();i++){
                                data.add(object.getString(i));
                            }
                            adapter=new Brand_details_adapter(Only_Brand_List.this,data,"alternate_brand");
                            recyclerView.setLayoutManager(new LinearLayoutManager(Only_Brand_List.this, LinearLayoutManager.VERTICAL,false));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                            process.dismiss();

                        }
                        catch (Exception e)
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
    private void fetching_info(){
        process.show();
        DatabaseHelper db=new DatabaseHelper(this);
        try{
           Cursor cursor= db.AlternateBrandByID(f_id);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    data.add(cursor.getString(0));
                }
                adapter=new Brand_details_adapter(Only_Brand_List.this,data,"alternate_brand");
                recyclerView.setLayoutManager(new LinearLayoutManager(Only_Brand_List.this, LinearLayoutManager.VERTICAL,false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                process.dismiss();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Parsing Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
        db.close();
        process.dismiss();
    }
}
