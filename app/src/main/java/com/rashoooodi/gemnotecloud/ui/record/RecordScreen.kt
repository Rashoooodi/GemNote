package com.rashoooodi.gemnotecloud.ui.record
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun RecordScreen(onBack: () -> Unit) {
  var isRecording by remember { mutableStateOf(false) }
  Box(Modifier.fillMaxSize()) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
      Text(if (isRecording) "recording..." else "tap to record")
      Spacer(Modifier.height(24.dp))
      Button(onClick = { isRecording = !isRecording }) { Text(if (isRecording) "stop" else "start") }
    }
    TextButton(onClick = onBack, modifier = Modifier.align(Alignment.TopStart).padding(8.dp)) { Text("back") }
  }
}
