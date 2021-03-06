package com.adityap.flashy_createflashcards.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Aditya on 9/30/2018.
 */
data class FlashcardModel(val id: Int = 0, val word: String, val definition: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            requireNotNull(parcel.readString()),
            requireNotNull(parcel.readString()))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(word)
        parcel.writeString(definition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlashcardModel> {
        override fun createFromParcel(parcel: Parcel): FlashcardModel {
            return FlashcardModel(parcel)
        }

        override fun newArray(size: Int): Array<FlashcardModel?> {
            return arrayOfNulls(size)
        }
    }

    fun toCardPair(): CardPair =
            CardPair(front = CardModel(title = this.word),
                    back = CardModel(description = this.definition))

}
