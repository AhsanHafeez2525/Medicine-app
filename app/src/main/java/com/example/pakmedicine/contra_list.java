package com.example.pakmedicine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class contra_list extends AppCompatActivity {
 RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra_list);
        recyclerView=findViewById(R.id.rv);
      //  get_contra_info(getIntent().getStringExtra("name"));

        DatabaseHelper db=new DatabaseHelper(this);
        Contradication_Adapter adapter=new Contradication_Adapter(contra_list.this,db.ByContra(getIntent().getStringExtra("name")));
        recyclerView.setLayoutManager(new LinearLayoutManager(contra_list.this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
    public  void get_contra_info(String name) {

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("Name",name);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jor = new JsonObjectRequest(

                Request.Method.POST,
                Constant_Class.Url+"ByContra",
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try
                        {
                            if(response.isNull("ByContraResult")){

                            }
                            else {


                                List<String> formulas=new ArrayList<>();
                                JSONArray object=   response.getJSONArray("ByContraResult");
                                for (int i=0;i<object.length();i++){
                                    formulas.add(object.getString(i));
                                }
                                Contradication_Adapter adapter=new Contradication_Adapter(contra_list.this,formulas);
                                recyclerView.setLayoutManager(new LinearLayoutManager(contra_list.this));
                                recyclerView.setAdapter(adapter);
                                recyclerView.setHasFixedSize(true);
                            }

                        } catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Parsing Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
        {


            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText(getApplicationContext(),"Error Try Again",Toast.LENGTH_SHORT).show();


            }


        }

        );
        queue.add(jor);
    }
}