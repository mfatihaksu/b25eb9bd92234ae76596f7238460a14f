package com.example.starsship.domain

sealed class StarShipsResult<out R> {
    data class Success<out T>(val data: T) : StarShipsResult<T>()
    data class Error(val exception: Exception) : StarShipsResult<Nothing>()
}

fun <T> StarShipsResult<T>.successOr(fallback: T): T {
    return (this as? StarShipsResult.Success<T>)?.data ?: fallback
}