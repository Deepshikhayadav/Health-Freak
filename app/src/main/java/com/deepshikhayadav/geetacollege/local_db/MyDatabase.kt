package com.deepshikhayadav.geetacollege.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deepshikhayadav.geetacollege.local_db.dao.MyDao
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.local_db.entity.YogaResponse
import com.deepshikhayadav.geetacollege.local_db.typeconverter.BlogTypeConverter


@Database(entities = [BlogResponse::class, YogaResponse::class], version = 1)
@TypeConverters(BlogTypeConverter::class)
abstract class MyDatabase  : RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object {
        private const val DATABASE_NAME = "Fitness-app"

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}