package com.deepshikhayadav.geetacollege.local_db.entity

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
)