package com.dell.noteit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    Button btnReg,btnLog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnReg = findViewById(R.id.start_reg_btn);
        btnLog = findViewById(R.id.start_log_btn);

        mAuth = FirebaseAuth.getInstance();

        updateUI();

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void updateUI()
    {
        if(mAuth.getCurrentUser() != null)
        {
            Intent startIntent = new Intent(StartActivity.this,MainActivity.class);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
            startActivity(startIntent);
            finish();
            Log.i("StartActivity","fAuth != null");
        }else
        {

            Log.i("StartActivity","fAuth != null");
        }
    }

    private void login()
    {
        Intent logIntent = new Intent(StartActivity.this,LoginActivity.class);
        logIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(logIntent);
    }

    private void register()
    {
        Intent regIntent = new Intent(StartActivity.this,RegisterActivity.class);
        regIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(regIntent);
    }
}
