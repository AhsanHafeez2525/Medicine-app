package com.example.pakmedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        copydatabase();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(Splash.this,MainActivity.class));
               finish();
            }
        },5000);
    }

    private void copydatabase() {
        DatabaseHelper db=new DatabaseHelper(this);
        db.getWritableDatabase();
        db.checkdatabase();
        db.close();
    }

}
