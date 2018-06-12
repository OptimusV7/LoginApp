package com.prime.optimus.loginapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder> {

    private List<NoteModel> mNoteModelList;
    public NoteViewAdapter(List<NoteModel> mNoteModelList){
        this.mNoteModelList = mNoteModelList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note, parent, false);
        return  new NoteViewHolder(v);
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle,noteTime;
        public NoteViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteTime = itemView.findViewById(R.id.note_time);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        NoteModel noteModel = mNoteModelList.get(position);
        holder.noteTitle.setText(noteModel.getNoteTitle());
        holder.noteTime.setText(noteModel.getNoteTime());

    }

    @Override
    public int getItemCount() {
        return mNoteModelList.size();
    }
}
