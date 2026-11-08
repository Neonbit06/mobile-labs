package ru.agapov.musiciansdiary.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.agapov.musiciansdiary.domain.Entry
import ru.agapov.musiciansdiary.domain.EntryType
import ru.agapov.musiciansdiary.ui.components.BackHeader
import ru.agapov.musiciansdiary.ui.theme.PrimaryPurpleStroke
import java.text.SimpleDateFormat
import java.util.Date

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

    val entryDate = remember { initialEntry?.date ?: System.currentTimeMillis() }
    val dateString = SimpleDateFormat("dd.MM.yyyy", LocalLocale.current.platformLocale)
        .format(Date(entryDate))

    val colors = MaterialTheme.colorScheme

    val types = mapOf(
        EntryType.COMPOSITION.name to "Композиция",
        EntryType.PRACTICE.name to "Репетиция",
        EntryType.IDEA.name to "Идея",
        EntryType.OTHER.name to "Прочее"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        BackHeader(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledField(label = "Название *") {
                BoxedTextField(value = title, onValueChange = { title = it })
            }

            LabeledField(label = "Тип") {
                ExposedDropdownMenuBox(
                    expanded = isTypeMenuExpanded,
                    onExpandedChange = { isTypeMenuExpanded = !isTypeMenuExpanded }
                ) {
                    Box(
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                            .heightIn(min = FieldHeight)
                            .background(colors.surfaceVariant)
                            .border(1.dp, colors.outline)
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = types[type] ?: "Прочее",
                            style = FieldTextStyle(colors.onSurface)
                        )
                        Text(
                            text = "▼",
                            fontSize = 16.sp,
                            color = colors.onSurface,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                    ExposedDropdownMenu(
                        expanded = isTypeMenuExpanded,
                        onDismissRequest = { isTypeMenuExpanded = false },
                        modifier = Modifier.background(colors.surfaceVariant)
                    ) {
                        types.forEach { (enumValue, label) ->
                            DropdownMenuItem(
                                text = { Text(label, color = colors.onSurface) },
                                onClick = {
                                    type = enumValue
                                    isTypeMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            LabeledField(label = "Дата") {
                BoxedTextField(
                    value = dateString,
                    onValueChange = {},
                    readOnly = true
                )
            }

            LabeledField(label = "BPM") {
                BoxedTextField(
                    value = bpm,
                    onValueChange = { if (it.all { char -> char.isDigit() }) bpm = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            LabeledField(label = "Тональность") {
                BoxedTextField(value = key, onValueChange = { key = it })
            }

            LabeledField(label = "Заметка") {
                BoxedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    singleLine = false,
                    minHeight = 136.dp
                )
            }

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
                    .height(68.dp),
                enabled = title.isNotBlank(),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, PrimaryPurpleStroke),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary,
                    disabledContainerColor = colors.primary.copy(alpha = 0.5f),
                    disabledContentColor = colors.onPrimary.copy(alpha = 0.7f)
                )
            ) {
                Text(
                    text = "Сохранить",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private val FieldHeight = 48.dp

private fun FieldTextStyle(color: androidx.compose.ui.graphics.Color) = TextStyle(
    color = color,
    fontSize = 16.sp,
    fontWeight = FontWeight.SemiBold
)

@Composable
private fun LabeledField(
    label: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        content()
    }
}

@Composable
private fun BoxedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    minHeight: Dp = FieldHeight,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val colors = MaterialTheme.colorScheme
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        textStyle = FieldTextStyle(colors.onSurface),
        cursorBrush = SolidColor(colors.onSurface),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .background(colors.surfaceVariant)
                    .border(1.dp, colors.outline)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
            ) {
                innerTextField()
            }
        }
    )
}
