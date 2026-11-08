package ru.agapov.musiciansdiary.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.agapov.musiciansdiary.domain.Entry
import ru.agapov.musiciansdiary.domain.EntryType
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(
    entry: Entry,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateString = SimpleDateFormat("dd.MM.yyyy", LocalLocale.current.platformLocale)
        .format(Date(entry.date))

    val typeString = when (entry.type) {
        EntryType.COMPOSITION.name -> "Композиция"
        EntryType.PRACTICE.name -> "Репетиция"
        EntryType.IDEA.name -> "Идея"
        else -> "Прочее"
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Детали записи", color = Color.White) },
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
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = entry.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = typeString,
                fontSize = 14.sp,
                color = Color(0xFFB0B0B0),
                modifier = Modifier.padding(top = 8.dp)
            )

            val metaText = buildString {
                append(dateString)
                entry.bpm?.let { append(" · $it BPM") }
                entry.key?.let { if (it.isNotBlank()) append(" · $it") }
            }
            Text(
                text = metaText,
                fontSize = 14.sp,
                color = Color(0xFFB0B0B0),
                modifier = Modifier.padding(top = 4.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color(0xFF3A3A3A)
            )

            Text(
                text = "Заметка",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = entry.notes.ifBlank { "Нет заметок" },
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp),
                lineHeight = 24.sp
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color(0xFF3A3A3A)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF553D73)
                    )
                ) {
                    Text("Редактировать", color = Color.White)
                }

                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFCF6679)
                    )
                ) {
                    Text("Удалить")
                }
            }
        }
    }
}