package com.deepshikhayadav.geetacollege.local_db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.deepshikhayadav.geetacollege.local_db.typeconverter.BlogTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_yoga_data")
data class YogaResponse (
    @PrimaryKey
    val page: Int = 1,

    @ColumnInfo(name = "yoga_response")
    @TypeConverters(BlogTypeConverter::class)
    val data: List<Yoga>
    )

data class Yoga (
    val id: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("heading")
    val heading: String,

    @SerializedName("desc")
    val desc: String,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(heading)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Yoga> {
        override fun createFromParcel(parcel: Parcel): Yoga {
            return Yoga(parcel)
        }

        override fun newArray(size: Int): Array<Yoga?> {
            return arrayOfNulls(size)
        }
    }
}