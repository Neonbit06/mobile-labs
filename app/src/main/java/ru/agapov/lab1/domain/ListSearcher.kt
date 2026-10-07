package ru.agapov.lab1.domain

object ListSearcher {
    fun search(list: List<Int>): SearchResult {
        val firstNegative = list.indexOfFirst { it < 0 }.takeIf { it >= 0 }
        val lastPositive = list.indexOfLast { it > 0 }.takeIf { it >= 0 }
        return SearchResult(
            firstNegativeIndex = firstNegative,
            lastPositiveIndex = lastPositive,
        )
    }
}