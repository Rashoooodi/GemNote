package com.rashoooodi.gemnotecloud.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "notes")
data class NoteEntity(
  @PrimaryKey val id: String,
  val title: String,
  val summary: String,
  val transcript: String,
  val tagsJson: String,
  val durationMs: Long,
  val createdAt: Long,
  val audioLocal: String?,
  val audioRemote: String?,
  val pdfRemote: String?,
  val model: String
)
