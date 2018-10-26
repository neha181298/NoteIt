package com.dell.noteit;


import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    View mView;

    TextView textTitle, textTime,textContent;
    CardView noteCard;
    ImageView reminder;

    public NoteViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        textTitle = mView.findViewById(R.id.note_title);
        textTime = mView.findViewById(R.id.note_timestamp);
        textContent= mView.findViewById(R.id.note_content);
        noteCard = mView.findViewById(R.id.card_view);
        reminder = mView.findViewById(R.id.reminder_icon);

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

    public void setReminderIcon(){reminder.setVisibility(View.VISIBLE);}

}
