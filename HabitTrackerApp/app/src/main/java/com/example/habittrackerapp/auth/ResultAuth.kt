package com.example.habittrackerapp.auth

/**
 * results if the action where successful failure inactive and inProgress
 */
sealed class ResultAuth<out T> {
    data class Success<out T>(val data: T) : ResultAuth<T>()
    data class Failure(val exception: Throwable) : ResultAuth<Nothing>()
    object Inactive : ResultAuth<Nothing>()
    object InProgress : ResultAuth<Nothing>()

}