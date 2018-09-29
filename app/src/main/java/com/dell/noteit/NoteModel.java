package com.dell.noteit;

import java.util.HashMap;
import java.util.Map;

public class NoteModel {
    String mTitle,mContent,mColor;
    int mImage;
    Map<String, String> mTime;

    NoteModel()
    {
        mImage = -1;
    }

    public NoteModel(String mTitle, String mContent, String mColor) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mColor = mColor;
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

    public Map<String, String>  getmTime() {
        return mTime;
    }

    public void setmTime(Map<String, String> mTime) {
        this.mTime = mTime;
    }
}
