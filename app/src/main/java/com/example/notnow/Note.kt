package com.example.notnow

import android.os.Parcel
import android.os.Parcelable
import khronos.Dates
import khronos.toString
import java.io.Serializable


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class Note(var text: String = "",
                var date: String = Dates.today.toString("dd-MM-yyyy"),
                var filename: String = "") : Parcelable, Serializable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(date)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        private val serialVersionUid: Long = 4242424242
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }


}