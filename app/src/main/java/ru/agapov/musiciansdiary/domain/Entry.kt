package ru.agapov.musiciansdiary.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val type: String,
    val date: Long,
    val bpm: Int? = null,
    val key: String? = null,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)