package com.rashoooodi.gemnotecloud.ui.detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(id: String, onBack: () -> Unit) {
    val sampleSummary = "• Discussed architecture for voice capture pipeline\n" +
        "• Gemini will summarize and tag once synced\n" +
        "• Upload connectors remain TODO"
    val sampleTags = listOf("Voice", "Gemini", "Backlog")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Note detail", style = MaterialTheme.typography.headlineSmall)
        Text("ID: $id", style = MaterialTheme.typography.bodyMedium)

        ElevatedCard(colors = CardDefaults.elevatedCardColors()) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Gemini summary", style = MaterialTheme.typography.titleMedium)
                Text(sampleSummary, style = MaterialTheme.typography.bodyMedium)
            }
        }

        ElevatedCard(colors = CardDefaults.elevatedCardColors()) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Suggested tags", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sampleTags.forEach { tag -> AssistChip(onClick = {}, label = { Text(tag) }) }
                }
            }
        }

        ElevatedCard(colors = CardDefaults.elevatedCardColors()) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Attachments", style = MaterialTheme.typography.titleMedium)
                Text("• Audio: stored locally until synced", style = MaterialTheme.typography.bodyMedium)
                Text("• Transcript: generated on-device", style = MaterialTheme.typography.bodyMedium)
                Text("• PDF export: pending upload integration", style = MaterialTheme.typography.bodyMedium)
            }
        }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to timeline", fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
