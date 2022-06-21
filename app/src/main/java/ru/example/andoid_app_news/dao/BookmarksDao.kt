package ru.example.andoid_app_news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.example.andoid_app_news.model.entity.BookmarkEntity

@Dao
interface BookmarksDao {
    @Query("SELECT * FROM bookmark ORDER BY created_date DESC")
    fun getBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmark WHERE url = :url Limit 1")
    fun getBookmark(url: String): Flow<BookmarkEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmark where id = :id1")
    suspend fun deleteById(id1: Int)
}