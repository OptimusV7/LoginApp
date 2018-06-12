package com.prime.optimus.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public EditText nameTxt, emailTxt, passTxt;
    public Button btnReg;
    ProgressDialog progressDialog;
    String email, password, name, uemail, pass;
    public DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameTxt = (EditText) findViewById(R.id.regName);
        emailTxt = (EditText)findViewById(R.id.regEmail);
        passTxt = (EditText)findViewById(R.id.regPassword);
        btnReg = (Button)findViewById(R.id.registerButton);

    }

     public void registerbtn(View view){

         name = nameTxt.getText().toString().trim();
         email = emailTxt.getText().toString().trim();
         password = passTxt.getText().toString().trim();


        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Registering User.....");
        progressDialog.show();

         mAuth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {


                 if (task.isSuccessful()){
                     mUserDatabase.child(mAuth.getCurrentUser().getUid()).child("Basic").child("Name").setValue(name)
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()){
                                 progressDialog.dismiss();

                                 Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                 startActivity(regIntent);
                                 Toast.makeText(RegisterActivity.this, "User added...", Toast.LENGTH_SHORT).show();

                             }else{
                                 progressDialog.dismiss();
                                 Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                             }
                         }
                     });

                 }else{
                     progressDialog.dismiss();
                     Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }

         });


     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
