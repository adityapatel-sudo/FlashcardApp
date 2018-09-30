package com.adityap.flashy_createflashcards.models;

/**
 * Created by Aditya on 9/30/2018.
 */

public class FlashcardModel {
    private int mId;
    private String mWord;
    private String mDefinition;


    public  FlashcardModel(){
        mWord = null;
        mDefinition = null;
    }

    public FlashcardModel(String word, String definition){
        mWord = word;
        mDefinition = definition;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
    public String getWord() {
        return mWord;
    }

    public void setWord(String mWord) {
        this.mWord = mWord;
    }

    public String getDefinition() {
        return mDefinition;
    }

    public void setDefinition(String mDefinition) {
        this.mDefinition = mDefinition;
    }
}
