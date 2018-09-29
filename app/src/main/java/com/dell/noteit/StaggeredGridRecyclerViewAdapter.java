package com.dell.noteit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class StaggeredGridRecyclerViewAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private static final String TAG = "StaggerViewAdapterActiviity";
    private ViewGroup _parent;

    ArrayList<NoteModel> arrayList;

    StaggeredGridRecyclerViewAdapter(ArrayList<NoteModel> arr)
    {
        arrayList = arr;
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card,parent,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Log.d(TAG,"OnBindViewHolder Called");

        holder.title.setText(arrayList.get(position).getmTitle());
        // If title is empty, hide title edit text
        if (holder.title.getText().toString().isEmpty()) {
            holder.title.setHeight(0);
        }
        holder.content.setText(arrayList.get(position).getmContent());
        // If content is empty, hide content edit text
        if (holder.content.getText().toString().isEmpty()) {
            holder.content.setHeight(0);
        }

        if (arrayList.get(position).getmImage() != -1) {

        }

        if (arrayList.get(position).getmImage() != -1 || (!holder.title.getText().toString().isEmpty() && holder.content.getText().toString().isEmpty())) {

            final float scale = _parent.getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (8 * scale + 0.5f);
            holder.title.setPadding(pixels, pixels, pixels, pixels);
        }

        if (holder.content.getText().toString().length() > 0 && holder.content.getText().toString().length() < 6) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_thin);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(70);
        }
        if (holder.content.getText().toString().length() >= 6 && holder.content.getText().toString().length() < 10) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(50);
        }
        if (holder.content.getText().toString().length() >= 10 && holder.content.getText().toString().length() < 13) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(36);
        }
        if (holder.content.getText().toString().length() >= 13 && holder.content.getText().toString().length() < 19) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(24);
        }
        if (holder.content.getText().toString().length() >= 19 && holder.content.getText().toString().length() < 24) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_regular);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(18);
        }
        if (holder.content.getText().toString().length() >= 24) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_regular);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(16);
        }
        if (!holder.title.getText().toString().isEmpty())
            holder.title.setBackgroundColor(Color.parseColor(arrayList.get(position).getmColor()));
        if (!holder.content.getText().toString().isEmpty())
            holder.content.setBackgroundColor(Color.parseColor(arrayList.get(position).getmColor()));
        holder.color = arrayList.get(position).getmColor();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}
