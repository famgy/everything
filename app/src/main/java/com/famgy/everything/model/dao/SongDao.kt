package com.famgy.everything.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.famgy.everything.model.bean.Song

/* 数据操作类，包含用于访问数据库的方法。 */
@Dao
interface SongDao {

    @Query("SELECT * FROM song ORDER BY name")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM song WHERE id = :songId")
    fun getSong(songId: String): LiveData<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songs: List<Song>)
}