package com.rashoooodi.gemnotecloud.ui.detail
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun DetailScreen(id: String, onBack: () -> Unit) {
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    Text("note", style = MaterialTheme.typography.titleLarge); Spacer(Modifier.height(8.dp))
    Text("id: " + id); Spacer(Modifier.height(8.dp))
    Text("transcript will show here."); Spacer(Modifier.height(8.dp))
    Button(onClick = onBack) { Text("back") }
  }
}
