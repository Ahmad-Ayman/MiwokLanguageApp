package com.example.android.miwok;

/**
 * Created by ahmed on 12/8/2017.
 */

public class Word {
    private String mMiwokWord;
    private String mDefaultWord;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioResourceId;
    public Word(String defaultWord , String miwokWord , int audioResourceId){
        mMiwokWord = miwokWord;
        mDefaultWord = defaultWord;
        mAudioResourceId= audioResourceId;
    }
    public Word(String defaultWord , String miwokWord , int ImageResourceId,  int audioResourceId){
        mMiwokWord = miwokWord;
        mDefaultWord = defaultWord;
        mImageResourceId = ImageResourceId;
        mAudioResourceId= audioResourceId;
    }
    public String getDefaultTranslation() {
        return mDefaultWord;
    }
    public String getMiwokTranslation() {
        return mMiwokWord;
    }
    public int getImageResourceId(){ return mImageResourceId; }
    public boolean hasImage(){
            return mImageResourceId != NO_IMAGE_PROVIDED;
    }
    public int getAudioResourceId(){
        return mAudioResourceId;
    }
}
