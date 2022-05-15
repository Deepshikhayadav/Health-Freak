package com.deepshikhayadav.geetacollege.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deepshikhayadav.geetacollege.local_db.dao.MyDao
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.local_db.typeconverter.BlogTypeConverter

@Database(entities = [BlogResponse::class], version = 1)
@TypeConverters(BlogTypeConverter::class)
abstract class BlogDatabase  : RoomDatabase() {

    abstract fun movieDao(): MyDao

    companion object {
        private const val DATABASE_NAME = "Blog-app"

        @Volatile
        private var INSTANCE: BlogDatabase? = null

        fun getInstance(context: Context): BlogDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BlogDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}