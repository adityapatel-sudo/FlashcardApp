package com.adityap.flashy_createflashcards.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Aditya on 12/26/2018.
 */
data class DeckModel(val id : Int = 0, val deckName: String, val deckDescription: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            requireNotNull(parcel.readString()),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(deckName)
        parcel.writeString(deckDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeckModel> {
        override fun createFromParcel(parcel: Parcel): DeckModel {
            return DeckModel(parcel)
        }

        override fun newArray(size: Int): Array<DeckModel?> {
            return arrayOfNulls(size)
        }
    }
}
