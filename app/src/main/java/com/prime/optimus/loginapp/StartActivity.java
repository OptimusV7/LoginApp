package com.prime.optimus.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    public Button btnLog, btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLog = (Button)findViewById(R.id.buttonLogin);
        btnReg = (Button)findViewById(R.id.buttonRegister);

    }

    public void login(View view){
        Intent logIntent = new Intent( this, LoginActivity.class);
        startActivity(logIntent);
    }

    public void register(View view){
        Intent logIntent = new Intent( this, RegisterActivity.class);
        startActivity(logIntent);
    }

}
