package com.dell.noteit;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button createNote;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference fNotesDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNote = findViewById(R.id.create_new_note);
        recyclerView = findViewById(R.id.recycler_view);

        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(MainActivity.this,NewNoteActivity.class);
                startActivity(createIntent);
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));




        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(mAuth.getCurrentUser().getUid());
        }

        updateUI();



        //loadData();       //Doesn't work with this



    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    //*****Something is wrong here , maybe something might be wrong with used classes or I dont really know
    //Also I want a way to store images too for the notes
    private void loadData() {

        Log.e("MainActivity","Load Data");

        FirebaseRecyclerAdapter<NoteModel, NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>(

                NoteModel.class,
                R.layout.note_card,
                NoteViewHolder.class,
                fNotesDatabase

        ) {
            @Override
            protected void populateViewHolder(final NoteViewHolder viewHolder, NoteModel model, int position) {
                final String noteId = getRef(position).getKey();

                Log.e("MainActivity","Populate View");

                fNotesDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("mTitle") && dataSnapshot.hasChild("mTime")) {
                            String title = dataSnapshot.child("mTitle").getValue().toString();
                            String timestamp = dataSnapshot.child("mTime").getValue().toString();
                            String content =  dataSnapshot.child("mContent").getValue().toString();

                            GetTimeAgo getTimeAgo = new GetTimeAgo();
                            viewHolder.setTime(getTimeAgo.getTimeAgo(Long.parseLong(timestamp), getApplicationContext()));

                            viewHolder.setTitle(title);
                            //viewHolder.setNoteTime(timestamp);
                            viewHolder.setContent(content);
                            viewHolder.setTime(timestamp);
                            Log.e("MainActivity","Data retrieval"+title);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void updateUI()
    {
        if(mAuth.getCurrentUser() != null)
        {

            Log.i("MainActivity","fAuth != null");
        }else
        {
            Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity","fAuth != null");
        }
    }


    //dont know what this does but anyway..
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}
