package ru.agapov.musiciansdiary.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.agapov.musiciansdiary.R
import ru.agapov.musiciansdiary.domain.Entry
import ru.agapov.musiciansdiary.domain.EntryType
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun EntryCard(
    entry: Entry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val dateString = SimpleDateFormat("dd.MM.yyyy", LocalLocale.current.platformLocale)
        .format(Date(entry.date))

    val typeString = when (entry.type) {
        EntryType.COMPOSITION.name -> stringResource(R.string.type_composition)
        EntryType.PRACTICE.name -> stringResource(R.string.type_practice)
        EntryType.IDEA.name -> stringResource(R.string.type_idea)
        else -> stringResource(R.string.type_other)
    }

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = entry.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$typeString · $dateString",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (entry.notes.isNotBlank()) {
                Text(
                    text = entry.notes,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
