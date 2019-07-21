package com.adityap.flashy_createflashcards.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aditya on 12/26/2018.
 */

    public class DeckModel implements Parcelable {
        private int mId;
        private String mDeckName;
        private String mDeckDescription;



        public DeckModel(String deckName, String deckDescription){
            mDeckName = deckName;
            mDeckDescription = deckDescription;
        }

    protected DeckModel(Parcel in) {
        mId = in.readInt();
        mDeckName = in.readString();
        mDeckDescription = in.readString();
    }

    public static final Creator<DeckModel> CREATOR = new Creator<DeckModel>() {
        @Override
        public DeckModel createFromParcel(Parcel in) {
            return new DeckModel(in);
        }

        @Override
        public DeckModel[] newArray(int size) {
            return new DeckModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mDeckName);
        dest.writeString(mDeckDescription);
    }
}


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
