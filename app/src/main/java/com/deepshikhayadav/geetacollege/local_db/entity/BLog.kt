package com.deepshikhayadav.geetacollege.local_db.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class BLog (
    val id: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("heading")
    val heading: String,

    @SerializedName("desc")
    val desc: String,

    @SerializedName("author")
    val author: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(heading)
        parcel.writeString(desc)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BLog> {
        override fun createFromParcel(parcel: Parcel): BLog {
            return BLog(parcel)
        }

        override fun newArray(size: Int): Array<BLog?> {
            return arrayOfNulls(size)
        }
    }
}