package com.rashoooodi.gemnotecloud.ui.home
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun HomeScreen(onNavigate: (String) -> Unit) {
  Box(Modifier.fillMaxSize()) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
      Text("your notes", style = MaterialTheme.typography.titleLarge)
      Spacer(Modifier.height(12.dp))
      Text("empty for now. hit the mic.", style = MaterialTheme.typography.bodyMedium)
    }
    FloatingActionButton(onClick = { onNavigate("record") }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Text("mic") }
  }
}
