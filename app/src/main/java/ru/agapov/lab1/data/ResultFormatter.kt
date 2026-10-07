package ru.agapov.lab1.data

import ru.agapov.lab1.domain.SearchResult

object ResultFormatter {

    fun format(result: SearchResult): String {
        val negativeLine = if (result.hasNegative) {
            "Индекс первого отрицательного: ${result.firstNegativeIndex}"
        } else {
            "Отрицательных элементов нет"
        }

        val positiveLine = if (result.hasPositive) {
            "Индекс последнего положительного: ${result.lastPositiveIndex}"
        } else {
            "Положительных элементов нет"
        }

        return "$negativeLine\n$positiveLine"
    }
}