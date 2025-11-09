package com.rashoooodi.gemnotecloud.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

private data class HomeNote(
    val id: String,
    val title: String,
    val summary: String,
    val tags: List<String>,
    val duration: String,
    val model: String
)

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val sampleNotes = remember {
        listOf(
            HomeNote(
                id = "alpha",
                title = "Stand-up recap",
                summary = "• Team unblocked API release\n• Mobile QA wraps tomorrow\n• Assign follow-up to Jordan",
                tags = listOf("Sprint", "Daily", "Action"),
                duration = "02:14",
                model = "gemini-1.5-flash"
            ),
            HomeNote(
                id = "beta",
                title = "Interview debrief",
                summary = "• Candidate comfortable with Compose\n• Needs deeper Kotlin coroutines practice\n• Share take-home review",
                tags = listOf("Hiring", "Compose", "Gemini"),
                duration = "04:32",
                model = "gemini-1.5-pro"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Your AI-boosted notebook", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FilledTonalButton(onClick = { onNavigate("record") }) { Text("Capture note") }
            OutlinedButton(onClick = { onNavigate("settings") }) { Text("Gemini setup") }
        }
        Text(
            "Recent summaries are generated locally using your chosen Gemini model once you provide an API key.",
            style = MaterialTheme.typography.bodyMedium
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f, fill = true)) {
            items(sampleNotes) { note ->
                ElevatedCard(colors = CardDefaults.elevatedCardColors()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            note.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            note.tags.forEach { tag ->
                                AssistChip(
                                    onClick = { onNavigate("detail/${note.id}") },
                                    label = { Text(tag) },
                                    colors = AssistChipDefaults.assistChipColors()
                                )
                            }
                        }
                        Text(
                            "${note.duration} • ${note.model}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}
