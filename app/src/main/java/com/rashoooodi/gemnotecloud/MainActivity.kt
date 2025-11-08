package com.rashoooodi.gemnotecloud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rashoooodi.gemnotecloud.ui.home.HomeScreen
import com.rashoooodi.gemnotecloud.ui.record.RecordScreen
import com.rashoooodi.gemnotecloud.ui.detail.DetailScreen
import com.rashoooodi.gemnotecloud.ui.settings.SettingsScreen
import com.rashoooodi.gemnotecloud.ui.theme.GemNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot() {
    val nav = rememberNavController()
    GemNoteTheme {
        Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("gemnote cloud") }) }) { padding ->
            NavHost(nav, startDestination = "home", modifier = Modifier.padding(padding)) {
                composable("home") { HomeScreen(onNavigate = { nav.navigate(it) }) }
                composable("record") { RecordScreen(onBack = { nav.popBackStack() }) }
                composable("detail/{id}") { back ->
                    val id = back.arguments?.getString("id") ?: ""
                    DetailScreen(id = id, onBack = { nav.popBackStack() })
                }
                composable("settings") { SettingsScreen(onBack = { nav.popBackStack() }) }
            }
        }
    }
}
