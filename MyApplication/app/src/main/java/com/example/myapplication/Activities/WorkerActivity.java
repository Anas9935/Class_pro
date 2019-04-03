package com.example.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;

public class WorkerActivity extends AppCompatActivity {
int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        Intent intent=getIntent();
        uid=intent.getIntExtra("uid",0);
        if(uid==0){
            Toast.makeText(this, "Authentication error", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
