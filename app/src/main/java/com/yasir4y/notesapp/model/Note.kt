package com.yasir4y.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes")
@Parcelize

data class Note(
    @PrimaryKey(autoGenerate = true)

    val noteTitle: String,
    val id: String,
    val noteDesc: String
): Parcelable

