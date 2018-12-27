package com.adityap.flashy_createflashcards.models;

/**
 * Created by Aditya on 12/26/2018.
 */

    public  DeckModel() {
        mDeckName = null;
        mDeckDescription = null;
    }

    public FlashcardModel(String deckName, String deckDescription){
        mDeckName = deckName;
        mDeckDescription = deckDescription;
    }

    public String getDeckName() {
        return mDeckName;
    }

    public void setDeckname(String mDeckName) {
        this.mDeckName = mDeckname;
    }

    public String getDeckDescription() {
        return mDeckDescription;
    }

    public void setDeckDescription(String mDeckDescription) {
        this.mDeckDescription = mdeckDescription;
    }
