package com.example.brewlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import com.example.brewlab.ui.AddBrewScreen
import com.example.brewlab.ui.BrewListScreen
import com.example.brewlab.ui.theme.BrewLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrewLabTheme {
                BrewLabApp()
            }
        }
    }
}

@Composable
fun BrewLabApp() {
    val brewLogs: SnapshotStateList<BrewLog> = remember {
        sampleBrewLogs.toMutableStateList()
    }
    var showAddScreen by remember { mutableStateOf(false) }

    if (showAddScreen) {
        AddBrewScreen(
            nextId = (brewLogs.maxOfOrNull { log -> log.id } ?: 0) + 1,
            onSaveBrew = { newLog ->
                brewLogs.add(newLog)
                showAddScreen = false
            },
            onCancel = { showAddScreen = false },
        )
    } else {
        BrewListScreen(
            logs = brewLogs,
            onAddClick = { showAddScreen = true },
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BrewLabAppPreview() {
    BrewLabTheme {
        BrewLabApp()
    }
}
