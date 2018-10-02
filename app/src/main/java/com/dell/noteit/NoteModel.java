package com.dell.noteit;

import java.util.HashMap;
import java.util.Map;

/*public class NoteModel {
    String mTitle,mContent;
    //String mColor;
    //int mImage;
    String mTime;

    NoteModel()
    {

    }

    public NoteModel(String mTitle, String mContent, String mTime) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTime = mTime;
        //this.mColor = mColor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}*/

public class NoteModel {

    public String noteTitle;
    public String noteTime;
    public  String noteContent;
    public String noteColor;

    public NoteModel() {

    }

    public NoteModel(String noteTitle,String noteContent, String noteTime,String noteColor) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        //this.noteColor=noteColor;
        this.noteContent=noteContent;
    }

   /* public String getNoteColor(){return noteColor;}

    public void  setNoteColor(String noteColor){this.noteColor=noteColor;}*/


    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }


    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }
}

