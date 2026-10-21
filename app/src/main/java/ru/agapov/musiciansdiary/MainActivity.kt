package ru.agapov.musiciansdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.agapov.musiciansdiary.domain.Entry
import ru.agapov.musiciansdiary.domain.EntryType
import ru.agapov.musiciansdiary.ui.screens.HomeScreen
import ru.agapov.musiciansdiary.ui.screens.EntryDetailScreen
import ru.agapov.musiciansdiary.ui.screens.EntryFormScreen
import ru.agapov.musiciansdiary.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mockEntries = listOf(
                        Entry(
                            title = "Battle Theme",
                            type = EntryType.COMPOSITION.name,
                            date = System.currentTimeMillis(),
                            bpm = 140,
                            key = "G minor",
                            notes = "Добавил струнные в середину. Поработать над ударными в начале."
                        ),
                        Entry(
                            title = "Night of Nights",
                            type = EntryType.PRACTICE.name,
                            date = System.currentTimeMillis() - 86400000,
                            notes = "Доучить переход в другую тональность."
                        ),
                        Entry(
                            title = "Идея саундтрека для игры",
                            type = EntryType.IDEA.name,
                            date = System.currentTimeMillis() - 172800000,
                            notes = "Записал вступление, середина пока сырая."
                        )
                    )

                    EntryFormScreen(
                        initialEntry = null,
                        onBackClick = { },
                        onSaveClick = { }
                    )
                }
            }
        }
    }
}