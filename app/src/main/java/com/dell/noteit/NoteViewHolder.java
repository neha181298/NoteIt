package com.dell.noteit;


import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    View mView;

    TextView textTitle, textTime,textContent;
    CardView noteCard;

    public NoteViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        textTitle = mView.findViewById(R.id.note_title);
        textTime = mView.findViewById(R.id.note_timestamp);
        textContent= mView.findViewById(R.id.note_content);
        noteCard = mView.findViewById(R.id.card_view);

    }

    public void setColor(String color){
    textTitle.setBackgroundColor(Color.parseColor(color));
    textContent.setBackgroundColor(Color.parseColor(color));
    textTime.setBackgroundColor(Color.parseColor(color));
    }

    public void setNoteTitle(String title) {
        textTitle.setText(title);
    }

    public void setNoteContent(String content) {
        textContent.setText(content);
    }

    public void setNoteTime(String time) {
        textTime.setText(time);
    }

}
