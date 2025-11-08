package com.rashoooodi.gemnotecloud.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao interface NoteDao {
  @Query("SELECT * FROM notes ORDER BY createdAt DESC") fun observeAll(): Flow<List<NoteEntity>>
  @Query("SELECT * FROM notes WHERE id = :id") suspend fun get(id: String): NoteEntity?
  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(note: NoteEntity)
  @Query("DELETE FROM notes WHERE id = :id") suspend fun delete(id: String)
}
