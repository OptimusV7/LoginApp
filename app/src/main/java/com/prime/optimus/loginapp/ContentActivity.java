package com.prime.optimus.loginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ContentActivity extends AppCompatActivity {

    public Button NoteBtn;
    public EditText edTitle, ContentText;


    public FirebaseAuth fAuth;
    public DatabaseReference fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(Objects.requireNonNull(fAuth.getCurrentUser()).getUid());

        NoteBtn = (Button)findViewById(R.id.contentSubmitBtn);
        edTitle = (EditText)findViewById(R.id.titleText);
        ContentText = (EditText)findViewById(R.id.contentText);

    }

    public void createNote(View view){
        if (fAuth.getCurrentUser() != null){

            final DatabaseReference newNoteRef = fDatabase.push();

            String title = edTitle.getText().toString().trim();
            String content = ContentText.getText().toString().trim();

            final Map<String, Object> noteMap = new HashMap<>();
            noteMap.put("title", title);
            noteMap.put("content", content);
            noteMap.put("timeStamp", ServerValue.TIMESTAMP);

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    newNoteRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ContentActivity.this, "Note Created", Toast.LENGTH_SHORT).show();
                                Intent createIntent = new Intent(ContentActivity.this, MainActivity.class);
                                startActivity(createIntent);
                            }else{
                                Toast.makeText(ContentActivity.this, "Error:"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
            mainThread.start();



        }else{
            Toast.makeText(this, "User is not signed in", Toast.LENGTH_SHORT).show();
        }
    }
}
