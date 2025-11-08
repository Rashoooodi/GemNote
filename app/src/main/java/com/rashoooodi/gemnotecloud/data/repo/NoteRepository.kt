package com.rashoooodi.gemnotecloud.data.repo
import com.rashoooodi.gemnotecloud.data.local.NoteDao
import com.rashoooodi.gemnotecloud.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow
class NoteRepository(private val dao: NoteDao) {
  fun observe(): Flow<List<NoteEntity>> = dao.observeAll()
  suspend fun get(id: String) = dao.get(id)
  suspend fun upsert(note: NoteEntity) = dao.upsert(note)
  suspend fun delete(id: String) = dao.delete(id)
}
