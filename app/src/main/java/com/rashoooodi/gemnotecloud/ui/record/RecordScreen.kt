package com.rashoooodi.gemnotecloud.ui.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecordScreen(onBack: () -> Unit) {
    var isRecording by remember { mutableStateOf(false) }
    var autoSummaries by remember { mutableStateOf(true) }
    var autoTags by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (isRecording) "Recording in progress" else "Tap capture to begin",
                style = MaterialTheme.typography.headlineSmall
            )
            ElevatedCard(colors = CardDefaults.elevatedCardColors()) {
                Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Automations", style = MaterialTheme.typography.titleMedium)
                    ToggleRow(label = "Summarize with Gemini", checked = autoSummaries) { autoSummaries = it }
                    ToggleRow(label = "Suggest smart tags", checked = autoTags) { autoTags = it }
                    Text(
                        "Gemini runs locally when your API key is stored. Toggle off to capture raw audio only.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { isRecording = !isRecording }) {
                Text(if (isRecording) "Stop capture" else "Start capture")
            }
            TextButton(onClick = onBack) { Text("Back") }
        }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors()
        )
    }
}
