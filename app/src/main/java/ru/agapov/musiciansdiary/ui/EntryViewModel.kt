package ru.agapov.musiciansdiary.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.agapov.musiciansdiary.data.AppDatabase
import ru.agapov.musiciansdiary.data.EntryDao
import ru.agapov.musiciansdiary.domain.Entry

class EntryViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: EntryDao = AppDatabase.getDatabase(application).entryDao()

    val entries: StateFlow<List<Entry>> = dao.getAllEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEntry(entry: Entry) {
        viewModelScope.launch {
            dao.insertEntry(entry)
        }
    }

    fun updateEntry(entry: Entry) {
        viewModelScope.launch {
            dao.updateEntry(entry)
        }
    }

    fun deleteEntry(entry: Entry) {
        viewModelScope.launch {
            dao.deleteEntry(entry)
        }
    }

    fun getEntryById(id: String, onResult: (Entry?) -> Unit) {
        viewModelScope.launch {
            val entry = dao.getEntryById(id)
            onResult(entry)
        }
    }
}