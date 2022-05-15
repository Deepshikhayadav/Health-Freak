package com.deepshikhayadav.geetacollege.local_db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.deepshikhayadav.geetacollege.local_db.typeconverter.BlogTypeConverter

@Entity(tableName = "tbl_blog_data")
data class BlogResponse (
    @PrimaryKey
    val page: Int = 1,

    @ColumnInfo(name = "blog_response")
    @TypeConverters(BlogTypeConverter::class)
    val data: List<BLog>
)