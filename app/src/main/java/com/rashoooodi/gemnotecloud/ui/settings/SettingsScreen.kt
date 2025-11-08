package com.rashoooodi.gemnotecloud.ui.settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun SettingsScreen(onBack: () -> Unit) {
  var key by remember { mutableStateOf("") }
  var model by remember { mutableStateOf("gemini-2.5-flash") }
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    Text("settings", style = MaterialTheme.typography.titleLarge)
    Spacer(Modifier.height(12.dp))
    OutlinedTextField(value = key, onValueChange = { key = it }, label = { Text("gemini api key") }, modifier = Modifier.fillMaxWidth())
    Spacer(Modifier.height(12.dp))
    Row { Text("model: "); Spacer(Modifier.width(8.dp)); Text(model) }
    Spacer(Modifier.height(24.dp))
    Button(onClick = onBack) { Text("done") }
  }
}
