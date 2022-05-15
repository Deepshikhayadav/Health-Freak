package com.deepshikhayadav.geetacollege.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse


@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBlogs(movieResponse: BlogResponse)

    @Query("select * from tbl_blog_data")
    fun getBlogs(): BlogResponse

}