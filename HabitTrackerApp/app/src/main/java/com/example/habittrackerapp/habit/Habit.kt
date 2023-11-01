package com.example.habittrackerapp.habit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
 * Class that defines what a habit is.
 * Each habit has description, start date, frequency, type and id.
 */
@Parcelize
data class Habit (
    var description : String = "",
    var startDate : String = "",
    var frequency: String = "",
    var type: String = "",
    var streak: Int = 0
) : Parcelable {
    val id: UUID = UUID.randomUUID()
}