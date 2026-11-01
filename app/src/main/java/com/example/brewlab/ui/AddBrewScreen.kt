package com.example.brewlab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.brewlab.BrewLog
import com.example.brewlab.recommendGrindAdjustment
import com.example.brewlab.ui.theme.BrewLabTheme

// ─────────────────────────────────────────────────────────────────────────────
// Constants
// ─────────────────────────────────────────────────────────────────────────────

private val roastLevels  = listOf("Light", "Medium", "Dark")
private val brewMethods  = listOf("Moka Pot", "Espresso", "V60")
private val tasteOptions = listOf("Sour", "Balanced", "Bitter")

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBrewScreen(
    onSaveBrew: (BrewLog) -> Unit,
    onCancel: () -> Unit,
    nextId: Int = 0,
) {
    // ── Form state ────────────────────────────────────────────────────────────
    var beanName        by remember { mutableStateOf("") }
    var roastLevel      by remember { mutableStateOf(roastLevels[1]) }   // "Medium"
    var brewMethod      by remember { mutableStateOf(brewMethods[0]) }   // "Moka Pot"
    var grindClicks     by remember { mutableIntStateOf(15) }
    var doseGramsText   by remember { mutableStateOf("") }
    var brewTimeText    by remember { mutableStateOf("") }
    var tasteProfile    by remember { mutableStateOf(tasteOptions[1]) }  // "Balanced"
    var rating          by remember { mutableIntStateOf(3) }

    // ── Live grind recommendation ─────────────────────────────────────────────
    val grindTip by remember {
        derivedStateOf { recommendGrindAdjustment(grindClicks, tasteProfile) }
    }

    // ── Save guard ────────────────────────────────────────────────────────────
    val canSave = (beanName.isNotBlank()
            && (doseGramsText.toDoubleOrNull() != null)
            && (brewTimeText.toIntOrNull() != null))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Brew", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // ── Bean name ─────────────────────────────────────────────────────
            SectionLabel("Bean Name")
            OutlinedTextField(
                value = beanName,
                onValueChange = { beanName = it },
                placeholder = { Text("e.g. Ethiopia Yirgacheffe") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            // ── Roast level ───────────────────────────────────────────────────
            SectionLabel("Roast Level")
            ChipRow(options = roastLevels, selected = roastLevel) { roastLevel = it }

            // ── Brew method ───────────────────────────────────────────────────
            SectionLabel("Brew Method")
            ChipRow(options = brewMethods, selected = brewMethod) { brewMethod = it }

            // ── Grind clicks ──────────────────────────────────────────────────
            SectionLabel("Grind Clicks")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                TextButton(onClick = { if (grindClicks > 1) grindClicks-- }) {
                    Text("−", style = MaterialTheme.typography.titleLarge)
                }
                Text(
                    text = "$grindClicks clicks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                TextButton(onClick = { grindClicks++ }) {
                    Text("+", style = MaterialTheme.typography.titleLarge)
                }
            }

            // ── Dose ──────────────────────────────────────────────────────────
            SectionLabel("Dose (grams)")
            OutlinedTextField(
                value = doseGramsText,
                onValueChange = { doseGramsText = it },
                placeholder = { Text("e.g. 18.0") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
            )

            // ── Brew time ─────────────────────────────────────────────────────
            SectionLabel("Brew Time (seconds)")
            OutlinedTextField(
                value = brewTimeText,
                onValueChange = { brewTimeText = it },
                placeholder = { Text("e.g. 240") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )

            // ── Taste profile ─────────────────────────────────────────────────
            SectionLabel("Taste Profile")
            ChipRow(options = tasteOptions, selected = tasteProfile) { tasteProfile = it }

            // ── Rating ────────────────────────────────────────────────────────
            SectionLabel("Rating")
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                (1..5).forEach { star ->
                    TextButton(onClick = { rating = star }) {
                        Text(
                            text = if (star <= rating) "★" else "☆",
                            style = MaterialTheme.typography.titleLarge,
                            color = if (star <= rating)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            // ── Live grind-adjustment card ────────────────────────────────────
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "☕ Grind Tip",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = grindTip,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                }
            }

            // ── Action buttons ────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        onSaveBrew(
                            BrewLog(
                                id               = nextId,
                                beanName         = beanName.trim(),
                                roastLevel       = roastLevel,
                                brewMethod       = brewMethod,
                                grindClicks      = grindClicks,
                                doseGrams        = doseGramsText.toDouble(),
                                brewTimeSeconds  = brewTimeText.toInt(),
                                tasteProfile     = tasteProfile,
                                rating           = rating,
                                isSynced         = false,
                            )
                        )
                    },
                    enabled = canSave,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Save Brew")
                }
            }

            Spacer(Modifier.height(24.dp)) // breathing room above the keyboard
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun ChipRow(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { option ->
            FilterChip(
                selected = selected == option,
                onClick  = { onSelect(option) },
                label    = { Text(option) },
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddBrewScreenPreview() {
    BrewLabTheme {
        AddBrewScreen(onSaveBrew = {}, onCancel = {})
    }
}
