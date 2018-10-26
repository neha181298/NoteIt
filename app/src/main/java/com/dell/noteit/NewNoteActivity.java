package com.dell.noteit;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    EditText eTitle,eContent;
    private FirebaseAuth fAuth;
    private DatabaseReference fNotesDatabase;
    Button create;
    TableLayout bottomToolbar;
    RadioGroup colorPickerRadioGroup;
    String noteColor,color;
    LinearLayout noteLayout, noteActionsLayout;
    android.support.v7.app.ActionBar    actionBar;
    TextView lastModificationDate;
    ImageButton noteActionsButton,noteShareButton;
    ImageView imageView;
    Uri imageUri=null;
    ScrollView scrollView;
    String mRecognizedText,voiceNoteText=null;


    private String noteID;

    private boolean isExist,isImage,isVoice;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);



        actionBar = getSupportActionBar();
        eTitle = findViewById(R.id.title_edit_text);
        eContent = findViewById(R.id.content_edit_text);
        create = findViewById(R.id.create_note);
        bottomToolbar = findViewById(R.id.bottom_toolbar);
        colorPickerRadioGroup = findViewById(R.id.color_picker_radio_group);
        noteLayout = findViewById(R.id.simple_note_creation_linear_layout);
        noteActionsLayout = findViewById(R.id.note_actions_layout);
        lastModificationDate = findViewById(R.id.last_modification_date);
        imageView = findViewById(R.id.imageView);
        scrollView = findViewById(R.id.imagescrollview);
        noteShareButton = findViewById(R.id.note_share_button);

        noteActionsButton = findViewById(R.id.note_actions_button);
        noteColor = "#FAFAFA";





        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();
        fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());


        try {
            noteID = getIntent().getStringExtra("noteId");
            imageUri = getIntent().getParcelableExtra("image");
            voiceNoteText = getIntent().getStringExtra("voiceNote");
            //Toast.makeText(this, noteID, Toast.LENGTH_SHORT).show();

            if(voiceNoteText != null){
                isVoice = true;
                eContent.setText(voiceNoteText);
            }

            if(imageUri != null) {
                isImage = true;
                imageView.setImageURI(imageUri);
                runTextRecognition();
            }
            else {
                isImage = false;
            }

            if (!noteID.trim().equals("")) {
                isExist = true;
                create.setText("UPDATE");
                putData();

            } else {
                isExist = false;


            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = eTitle.getText().toString().trim();
                String content = eContent.getText().toString().trim();

                if ( !TextUtils.isEmpty(content)) {
                    createNote(title, content);
                } else {
                    Snackbar.make(view, "Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        noteShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNote();
            }
        });



        // Hide note actions by default
        noteActionsLayout.setVisibility(View.GONE);

        // Hide/Show note actions
        noteActionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (noteActionsLayout.getVisibility() == View.GONE) {
                    noteActionsLayout.setVisibility(View.VISIBLE);
                    //noteActionsButton.setBackgroundColor(darkenNoteColor(Color.parseColor(noteColor), 0.9f));
                }
                else if (noteActionsLayout.getVisibility() == View.VISIBLE) {
                    noteActionsButton.setBackgroundColor(Color.parseColor(noteColor));
                    noteActionsLayout.setVisibility(View.GONE);
                }
            }
        });




        // Check the color picker
        colorPickerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.default_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteDefault);

                }
                else if (checkedId == R.id.red_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteRed);
                }
                else if (checkedId == R.id.orange_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteOrange);
                }
                else if (checkedId == R.id.yellow_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteYellow);
                }
                else if (checkedId == R.id.green_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteGreen);
                }
                else if (checkedId == R.id.cyan_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteCyan);
                }
                else if (checkedId == R.id.light_blue_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteLightBlue);
                }
                else if (checkedId == R.id.dark_blue_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteDarkBlue);
                }
                else if (checkedId == R.id.purple_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNotePurple);
                }
                else if (checkedId == R.id.pink_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNotePink);
                }
                else if (checkedId == R.id.brown_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteBrow);
                }
                else if (checkedId == R.id.grey_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteGrey);
                }
                noteLayout.setBackgroundColor(Color.parseColor(noteColor));
                noteActionsLayout.setBackgroundColor(Color.parseColor(noteColor));
                bottomToolbar.setBackgroundColor(Color.parseColor(noteColor));
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(noteColor)));
                scrollView.setBackgroundColor(Color.parseColor(noteColor));
                getWindow().setStatusBarColor(darkenNoteColor(Color.parseColor(noteColor), 0.7f));

                noteActionsButton.setBackgroundColor(darkenNoteColor(Color.parseColor(noteColor), 0.9f));
            }
        });



    }


    private void putData() {

        if (isExist) {
            fNotesDatabase.child(noteID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("content")) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String content = dataSnapshot.child("content").getValue().toString();
                        String color = dataSnapshot.child("color").getValue().toString();
                        String time = dataSnapshot.child("time").getValue().toString();
                        GetTimeAgo getTimeAgo = new GetTimeAgo();
                        eTitle.setText(title);
                        noteColor=color;
                        eContent.setText(content);
                        lastModificationDate.setText(getTimeAgo.getTimeAgo(Long.parseLong(time), getApplicationContext()));
                        noteLayout.setBackgroundColor(Color.parseColor(noteColor));
                        noteActionsLayout.setBackgroundColor(Color.parseColor(noteColor));
                        bottomToolbar.setBackgroundColor(Color.parseColor(noteColor));
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(noteColor)));
                        scrollView.setBackgroundColor(Color.parseColor(noteColor));
                        getWindow().setStatusBarColor(darkenNoteColor(Color.parseColor(noteColor), 0.7f));

                        noteActionsButton.setBackgroundColor(darkenNoteColor(Color.parseColor(noteColor), 0.9f));

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    private void createNote(String title,String content) {


            if (fAuth.getCurrentUser() != null) {

                if (isExist) {
                    // UPDATE A NOTE
                    Map updateMap = new HashMap();
                    updateMap.put("title", eTitle.getText().toString().trim());
                    updateMap.put("content", eContent.getText().toString().trim());
                    updateMap.put("time", ServerValue.TIMESTAMP);
                    updateMap.put("color", noteColor);


                    fNotesDatabase.child(noteID).updateChildren(updateMap);

                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                } else {

                    // CREATE A NEW NOTE
                    final DatabaseReference newNoteRef = fNotesDatabase.push();


                    final Map noteMap = new HashMap();
                    noteMap.put("title", title);
                    noteMap.put("content", content);
                    noteMap.put("time", ServerValue.TIMESTAMP);
                    noteMap.put("color", noteColor);
                    noteMap.put("reminder time", null);


                    Thread mainThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            newNoteRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(NewNoteActivity.this, "Note added to database", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(NewNoteActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    });
                    mainThread.start();

                }


            } else {
                Toast.makeText(this, "USERS IS NOT SIGNED IN", Toast.LENGTH_SHORT).show();
            }
        }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.new_note_delete_btn:
                if (isExist) {
                    deleteNote();
                } else {
                    Toast.makeText(this, "Nothing to delete", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.note_reminder:
                createReminder();
                break;
        }

        return true;
    }

    public static int darkenNoteColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }


    private void deleteNote() {

        fNotesDatabase.child(noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(NewNoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                    noteID = "no";
                    finish();
                } else {
                    Log.e("NewNoteActivity", task.getException().toString());
                    Toast.makeText(NewNoteActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = eTitle.getText().toString().trim();
        String content = eContent.getText().toString().trim();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            createNote(title, content);
        }
    }


    private void runTextRecognition() {
        FirebaseVisionImage image = null;
        try {
            image = FirebaseVisionImage.fromFilePath(getApplicationContext(),imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        detector.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {

                                processTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception

                                e.printStackTrace();
                            }
                        });
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }

        mRecognizedText = texts.getText().toString();
        eContent.setText(mRecognizedText);

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void shareNote()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, eContent.getText().toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via" ));
    }



    private void createReminder()
    {
        new CustomDateTimePicker(this,
                new CustomDateTimePicker.ICustomDateTimeListener() {
                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year,
                                      String monthFullName,
                                      String monthShortName,
                                      int monthNumber, int date,
                                      String weekDayFullName,
                                      String weekDayShortName, int hour24,
                                      int hour12,
                                      int min, int sec, String AM_PM) {

                        showToast("Reminder set for "+calendarSelected.getTime().toString());
                        setReminder(getApplicationContext(),ReminderReceiver.class,calendarSelected);
                        if(fAuth.getCurrentUser()!=null)
                        {
                           fNotesDatabase.child(noteID).child("reminder time").setValue(calendarSelected.getTime().toString());
                        }

                    }

                    @Override
                    public void onCancel() {

                    }
                }).set24HourFormat(true).setDate(Calendar.getInstance())
                .showDialog();
    }

    public void setReminder(Context context, Class<?> cls, Calendar calendar) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("TIME", calendar.getTime().toString());
        intent.putExtra("RequestCode", 10);
        intent.putExtra("Note", eTitle.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 10, intent,
                PendingIntent.FLAG_ONE_SHOT);/* Find more about flags: https://developer.android.com/reference/android/app/PendingIntent */

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);//Add time in milliseconds. if you want to minute or hour mutiply by 60.. For ex: You want to trigger 5 Min then here you need to change 5 * 60 * 1000

    }


    public void cancelReminder(Context context,Class<?> cls,Calendar calendar)
    {
        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("TIME", calendar.getTime().toString());
        intent1.putExtra("RequestCode", 10);
        intent1.putExtra("Note", eTitle.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                10, intent1, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(pendingIntent != null) {
            am.cancel(pendingIntent);
        }
    }

    }
