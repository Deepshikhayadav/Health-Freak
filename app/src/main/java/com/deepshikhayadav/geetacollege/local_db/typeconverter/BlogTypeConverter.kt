package com.deepshikhayadav.geetacollege.local_db.typeconverter

import androidx.room.TypeConverter
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import com.deepshikhayadav.geetacollege.local_db.entity.Yoga
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
    @TypeConverter
    @JvmStatic
    fun fromListYoga(value: List<Yoga>) = Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun toListYoga(value: String): List<Yoga> {
        val listType: Type = object : TypeToken<List<Yoga>>() {}.type
        return Gson().fromJson(value, listType)
    }
}