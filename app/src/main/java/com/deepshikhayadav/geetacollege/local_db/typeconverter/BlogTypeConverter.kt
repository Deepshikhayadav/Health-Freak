package com.deepshikhayadav.geetacollege.local_db.typeconverter

import androidx.room.TypeConverter
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object BlogTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromList(value: List<BLog>) = Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<BLog> {
        val listType: Type = object : TypeToken<List<BLog>>() {}.type
        return Gson().fromJson(value, listType)
    }
}