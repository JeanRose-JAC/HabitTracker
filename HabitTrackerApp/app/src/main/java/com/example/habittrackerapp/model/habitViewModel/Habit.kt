package com.example.habittrackerapp.model.habitViewModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
 * Class that defines what a habit is.
 * Each habit has description, start date, frequency, type and id.
 */
@Parcelize
@Entity(tableName = "habits")
data class Habit (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description : String,
    val startDate : String,
    val frequency: String,
    val type: String,
    val streak: Int
) : Parcelable {
}