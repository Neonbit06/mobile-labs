package ru.agapov.musiciansdiary.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.agapov.musiciansdiary.domain.Entry
import ru.agapov.musiciansdiary.domain.EntryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryFormScreen(
    initialEntry: Entry? = null,
    onBackClick: () -> Unit,
    onSaveClick: (Entry) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(initialEntry?.title ?: "") }
    var type by remember { mutableStateOf(initialEntry?.type ?: EntryType.COMPOSITION.name) }
    var bpm by remember { mutableStateOf(initialEntry?.bpm?.toString() ?: "") }
    var key by remember { mutableStateOf(initialEntry?.key ?: "") }
    var notes by remember { mutableStateOf(initialEntry?.notes ?: "") }

    var isTypeMenuExpanded by remember { mutableStateOf(false) }

    val isEditMode = initialEntry != null
    val screenTitle = if (isEditMode) "Редактирование" else "Новая запись"

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(screenTitle, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF121212)
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormTextField(
                value = title,
                onValueChange = { title = it },
                label = "Название *"
            )

            ExposedDropdownMenuBox(
                expanded = isTypeMenuExpanded,
                onExpandedChange = { isTypeMenuExpanded = !isTypeMenuExpanded }
            ) {
                FormTextField(
                    value = when (type) {
                        EntryType.COMPOSITION.name -> "Композиция"
                        EntryType.PRACTICE.name -> "Репетиция"
                        EntryType.IDEA.name -> "Идея"
                        else -> "Прочее"
                    },
                    onValueChange = {},
                    label = "Тип",
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isTypeMenuExpanded) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = isTypeMenuExpanded,
                    onDismissRequest = { isTypeMenuExpanded = false }
                ) {
                    val types = mapOf(
                        EntryType.COMPOSITION.name to "Композиция",
                        EntryType.PRACTICE.name to "Репетиция",
                        EntryType.IDEA.name to "Идея",
                        EntryType.OTHER.name to "Прочее"
                    )
                    types.forEach { (enumValue, label) ->
                        DropdownMenuItem(
                            text = { Text(label, color = Color.Black) },
                            onClick = {
                                type = enumValue
                                isTypeMenuExpanded = false
                            }
                        )
                    }
                }
            }

            FormTextField(
                value = bpm,
                onValueChange = { if (it.all { char -> char.isDigit() }) bpm = it },
                label = "BPM",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            FormTextField(
                value = key,
                onValueChange = { key = it },
                label = "Тональность"
            )

            FormTextField(
                value = notes,
                onValueChange = { notes = it },
                label = "Заметка",
                singleLine = false,
                minLines = 5
            )

            Button(
                onClick = {
                    val newEntry = initialEntry?.copy(
                        title = title,
                        type = type,
                        bpm = bpm.toIntOrNull(),
                        key = key.ifBlank { null },
                        notes = notes,
                        updatedAt = System.currentTimeMillis()
                    ) ?: Entry(
                        title = title,
                        type = type,
                        date = System.currentTimeMillis(),
                        bpm = bpm.toIntOrNull(),
                        key = key.ifBlank { null },
                        notes = notes
                    )
                    onSaveClick(newEntry)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF553D73),
                    disabledContainerColor = Color(0xFF3B2A4F)
                )
            ) {
                Text("Сохранить", color = Color.White, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
private fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFFB0B0B0)) },
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        singleLine = singleLine,
        minLines = minLines,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF2C2C2C),
            unfocusedContainerColor = Color(0xFF2C2C2C),
            focusedBorderColor = Color(0xFF696969),
            unfocusedBorderColor = Color(0xFF696969),
            cursorColor = Color.White
        )
    )
}