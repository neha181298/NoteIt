package com.dell.noteit;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder
{
    public  ImageView image;
    public int imageView;
    public TextView title;
    public TextView content;
    public String color;
    public  TextView time;
    public View mview;
    public CardView notecard;


    public NoteViewHolder(View itemView) {
        super(itemView);
        mview = itemView;
        itemView.setOnClickListener((View.OnClickListener) this);
        title = itemView.findViewById(R.id.note_title);
        content = itemView.findViewById(R.id.note_content);
        time = itemView.findViewById(R.id.note_timestamp);
        image = itemView.findViewById(R.id.note_image);
        notecard = itemView.findViewById(R.id.card_view);
    }



    public String getTitle() {
        return title.getText().toString();
    }

    public void setTitle(String title) {
        this.title.setText((CharSequence) title);
    }

    public String getContent() {
        return content.getText().toString();
    }

    public void setContent(String content) {
        this.content.setText((CharSequence) content);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTime() {
        return time.getText().toString();
    }

    public void setTime(String time) {
        this.time.setText(time);
    }
}