package com.adityap.flashy_createflashcards.models;

/**
 * Created by Aditya on 12/26/2018.
 */

    public class DeckModel {
        private int mId;
        private String mDeckName;
        private String mDeckDescription;


        public int getId() {
            return mId;
        }

        public void setId(int mId) {
            this.mId = mId;
        }

        public String getDeckName() {
            return mDeckName;
        }

        public void setDeckName(String mDeckName) {
            this.mDeckName = mDeckName;
        }

        public String getDeckDescription() {
            return mDeckDescription;
        }

        public void setDeckDescription(String mDeckDescription) {
            this.mDeckDescription = mDeckDescription;
        }
    }

//
//    public FlashcardModel(String deckName, String deckDescription){
//        mDeckName = deckName;
//        mDeckDescription = deckDescription;
//    }

//    public String getDeckName() {
//        return mDeckName;
//    }
//
//    public void setDeckname(String mDeckName) {
//        this.mDeckName = mDeckname;
//    }
//
//    public String getDeckDescription() {
//        return mDeckDescription;
//    }
//
//    public void setDeckDescription(String mDeckDescription) {
//        this.mDeckDescription = mdeckDescription;
//    }
