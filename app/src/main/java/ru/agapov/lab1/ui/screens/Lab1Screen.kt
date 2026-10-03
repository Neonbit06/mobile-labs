package ru.agapov.lab1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.agapov.lab1.ui.theme.Lab1Theme

@Composable
fun Lab1Screen(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    var outputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "Лабораторная работа №1",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Поиск первого отрицательного и последнего положительного элементов",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Список чисел") },
            enabled = false,
        )

        OutlinedTextField(
            value = outputText,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Результат") },
            minLines = 2,
            enabled = false,
        )

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Найти")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Lab1ScreenPreview() {
    Lab1Theme {
        Lab1Screen()
    }
}