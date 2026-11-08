package ru.agapov.musiciansdiary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.agapov.musiciansdiary.domain.Entry

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun getEntryById(id: String): Entry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Update
    suspend fun updateEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)
}