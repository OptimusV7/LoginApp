package com.prime.optimus.loginapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public RecyclerView mNoteList;
    public GridLayoutManager gridLayoutManager;
    public FirebaseAuth mAuth;

    //+++++++++++++++++++++++++++++++++++++++++++++
    private final List<NoteModel> noteModelList = new ArrayList<>();
    private NoteViewAdapter mAdapter;

    public DatabaseReference mNotesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteList = findViewById(R.id.main_note_list);
        gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mNoteList.setHasFixedSize(true);
        mNoteList.setLayoutManager(gridLayoutManager);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(mAuth.getCurrentUser().getUid());
            mNotesDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query noteQuery = mNotesDatabase.orderByKey();
        FirebaseRecyclerOptions<NoteModel> noteOptions = new FirebaseRecyclerOptions.Builder<NoteModel>().setQuery(noteQuery, NoteModel.class).build();

        FirebaseRecyclerAdapter<NoteModel, NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>(noteOptions) {



            @Override
            public void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteModel model) {

                holder.setNoteTitle(model.getNoteTitle());
                holder.setNoteTime(model.getNoteTime());

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_note, parent, false);
                return new NoteViewHolder(view);


            }

        };
        mNoteList.setAdapter(firebaseRecyclerAdapter);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.men_notebtn:
                Intent newIntent = new Intent(this, ContentActivity.class);
                startActivity(newIntent);
                break;
        }

        return true;
    }
}
