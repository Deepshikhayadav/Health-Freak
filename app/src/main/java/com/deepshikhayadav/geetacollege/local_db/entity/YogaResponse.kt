package com.deepshikhayadav.geetacollege.local_db.entity

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


)