package ru.agapov.lab1.domain

data class SearchResult(
    val firstNegativeIndex: Int?,
    val lastPositiveIndex: Int?,
) {
    val hasNegative: Boolean = firstNegativeIndex != null
    val hasPositive: Boolean = lastPositiveIndex != null
}