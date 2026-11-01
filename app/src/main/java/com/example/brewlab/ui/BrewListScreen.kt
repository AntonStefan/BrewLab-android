package com.example.brewlab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.brewlab.BrewLog
import com.example.brewlab.sampleBrewLogs
import com.example.brewlab.ui.theme.BrewLabTheme

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

private val filterOptions = listOf("All", "Moka Pot", "Espresso", "V60")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrewListScreen(
    logs: List<BrewLog>,
    onAddClick: () -> Unit,
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredLogs = remember(logs, selectedFilter) {
        if (selectedFilter == "All") logs
        else logs.filter { log -> log.brewMethod == selectedFilter }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BrewLab Journal", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ── Filter chips ──────────────────────────────────────────────────
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filterOptions) { option ->
                    FilterChip(
                        selected = selectedFilter == option,
                        onClick = { selectedFilter = option },
                        label = { Text(option) },
                    )
                }
            }

            // ── Brew log list ─────────────────────────────────────────────────
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(filteredLogs, key = { it.id }) { log ->
                    BrewLogCard(log)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun BrewLogCard(log: BrewLog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header row: bean name + rating stars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = log.beanName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "★".repeat(log.rating) + "☆".repeat(5 - log.rating),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(Modifier.height(6.dp))

            // Method badges row
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                LabelBadge(log.brewMethod)
                LabelBadge(log.roastLevel)
                LabelBadge(log.tasteProfile)
            }

            Spacer(Modifier.height(8.dp))

            // Brew details
            BrewDetailRow("Grind", "${log.grindClicks} clicks")
            BrewDetailRow("Dose", "${log.doseGrams} g")
            BrewDetailRow("Brew time", "${log.brewTimeSeconds} s")

            Spacer(Modifier.height(8.dp))

            // Sync status
            val (syncText, syncColor) = if (log.isSynced) {
                "✓ Synced" to MaterialTheme.colorScheme.tertiary
            } else {
                "⏳ Offline (Pending)" to MaterialTheme.colorScheme.error
            }
            Text(
                text = syncText,
                style = MaterialTheme.typography.labelSmall,
                color = syncColor,
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Small helpers
// ─────────────────────────────────────────────────────────────────────────────

/** A pill-shaped tonal label. */
@Composable
private fun LabelBadge(text: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
        )
    }
}

/** A single key → value detail row. */
@Composable
private fun BrewDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BrewListScreenPreview() {
    BrewLabTheme {
                BrewListScreen(logs = sampleBrewLogs) {}
    }
}
