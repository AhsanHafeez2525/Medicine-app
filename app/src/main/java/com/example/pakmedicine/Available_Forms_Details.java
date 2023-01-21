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

public class Available_Forms_Details extends AppCompatActivity {
RecyclerView recyclerView;
String b_name,type;
List<packinginfo_model_class> data=new ArrayList<>();
Packing_info_Adapter adapter;
    ProgressDialog process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_forms_details);
        recyclerView=findViewById(R.id.recyclerview);
        b_name=getIntent().getStringExtra("b_name");
        type=getIntent().getStringExtra("type");
        process = new ProgressDialog(Available_Forms_Details.this);
        process.setMessage("Fetching Record...");
        process.setCancelable(false);
        fetching_info();
    }

    // service
    private void fetching_info2() {
        process.show();
        data.clear();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("b_name",b_name);
        jsonParams.put("type",type);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"PackingInfo",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String wcfresponse = null;
                        try
                        {
                            JSONArray jsonArray=   response.getJSONArray("PackingInfoResult");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                              String packing[]=jsonObject.getString("Packing_Info").split(",");
                                String price[]=jsonObject.getString("Price").split(",");
                                for (int k=0;k<packing.length;k++) {
                                    data.add(new packinginfo_model_class(packing[k],price[k]));
                                }
                            }
                            adapter=new Packing_info_Adapter(Available_Forms_Details.this,data);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Available_Forms_Details.this, LinearLayoutManager.VERTICAL,false));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);

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

    private void fetching_info(){
       DatabaseHelper db=new DatabaseHelper(this);
       process.show();
       try {
        Cursor cursor= db.PackingInfo(b_name,type);
        if(cursor!=null) {
            while (cursor.moveToNext()) {

                String packing[] = cursor.getString(4).split(",");
                String price[] = cursor.getString(5).split(",");
                for (int k = 0; k < packing.length; k++) {
                    data.add(new packinginfo_model_class(packing[k], price[k]));
                }
            }
            adapter = new Packing_info_Adapter(Available_Forms_Details.this, data);
            recyclerView.setLayoutManager(new LinearLayoutManager(Available_Forms_Details.this, LinearLayoutManager.VERTICAL, false));
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
}
