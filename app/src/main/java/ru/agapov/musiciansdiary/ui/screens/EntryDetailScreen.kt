package ru.agapov.musiciansdiary.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.agapov.musiciansdiary.R
import ru.agapov.musiciansdiary.domain.Entry
import ru.agapov.musiciansdiary.domain.EntryType
import ru.agapov.musiciansdiary.ui.components.BackHeader
import ru.agapov.musiciansdiary.ui.theme.DeleteRedStroke
import ru.agapov.musiciansdiary.ui.theme.PrimaryPurpleStroke
import java.text.SimpleDateFormat
import java.util.Date

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
        EntryType.COMPOSITION.name -> stringResource(R.string.type_composition)
        EntryType.PRACTICE.name -> stringResource(R.string.type_practice)
        EntryType.IDEA.name -> stringResource(R.string.type_idea)
        else -> stringResource(R.string.type_other)
    }

    val metaText = buildString {
        append(dateString)
        entry.bpm?.let { append(" · $it BPM") }
        entry.key?.let { if (it.isNotBlank()) append(" · $it") }
    }

    val colors = MaterialTheme.colorScheme
    val dividerColor = colors.outline.copy(alpha = 0.6f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackHeader(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 240.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = entry.title,
                    fontSize = 30.sp,
                    color = colors.onBackground
                )
                Column {
                    Text(
                        text = typeString,
                        fontSize = 16.sp,
                        color = colors.onSurfaceVariant
                    )
                    Text(
                        text = metaText,
                        fontSize = 18.sp,
                        color = colors.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(color = dividerColor)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.screen_detail_notes_title),
                    fontSize = 26.sp,
                    color = colors.onBackground
                )
                Spacer(Modifier.height(56.dp))
                Text(
                    text = entry.notes.ifBlank {
                        stringResource(R.string.screen_detail_no_notes) },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 28.sp,
                    color = colors.onBackground
                )
            }

            HorizontalDivider(color = dividerColor)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Button(
                onClick = onEditClick,
                modifier = Modifier
                    .weight(1f)
                    .height(68.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, PrimaryPurpleStroke),
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.screen_detail_edit_button),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }

            OutlinedButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .weight(1f)
                    .height(68.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, DeleteRedStroke),
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.error
                )
            ) {
                Text(
                    text = stringResource(R.string.screen_detail_delete_button),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}
