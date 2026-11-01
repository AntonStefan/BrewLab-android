package com.example.brewlab

// ─────────────────────────────────────────────────────────────────────────────
// Data Model
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Represents a single brew session recorded in BrewLab.
 *
 * @property id            Unique identifier for the brew log entry.
 * @property beanName      Name of the coffee bean used.
 * @property roastLevel    Roast level of the bean: "Light", "Medium", or "Dark".
 * @property brewMethod    Brewing method used: "Moka Pot", "Espresso", or "V60".
 * @property grindClicks   Number of grinder clicks (coarseness setting).
 * @property doseGrams     Amount of coffee used, in grams.
 * @property brewTimeSeconds Total brew time in seconds.
 * @property tasteProfile  Perceived taste outcome: "Sour", "Balanced", or "Bitter".
 * @property rating        User rating from 1 (poor) to 5 (excellent).
 * @property isSynced      Whether this entry has been synced to a remote source.
 */
data class BrewLog(
    val id: Int,
    val beanName: String,
    val roastLevel: String,       // "Light" | "Medium" | "Dark"
    val brewMethod: String,       // "Moka Pot" | "Espresso" | "V60"
    val grindClicks: Int,
    val doseGrams: Double,
    val brewTimeSeconds: Int,
    val tasteProfile: String,     // "Sour" | "Balanced" | "Bitter"
    val rating: Int,              // 1..5
    val isSynced: Boolean,
)

// ─────────────────────────────────────────────────────────────────────────────
// Sample Data
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Five realistic hardcoded brew-log entries spanning all three brew methods.
 */
val sampleBrewLogs: List<BrewLog> = listOf(
    BrewLog(
        id = 1,
        beanName = "Ethiopia Yirgacheffe",
        roastLevel = "Light",
        brewMethod = "V60",
        grindClicks = 24,
        doseGrams = 15.0,
        brewTimeSeconds = 185,
        tasteProfile = "Sour",
        rating = 3,
        isSynced = true
    ),
    BrewLog(
        id = 2,
        beanName = "Colombia Huila",
        roastLevel = "Medium",
        brewMethod = "V60",
        grindClicks = 22,
        doseGrams = 15.5,
        brewTimeSeconds = 210,
        tasteProfile = "Balanced",
        rating = 5,
        isSynced = true
    ),
    BrewLog(
        id = 3,
        beanName = "Brazil Santos",
        roastLevel = "Dark",
        brewMethod = "Moka Pot",
        grindClicks = 10,
        doseGrams = 18.0,
        brewTimeSeconds = 240,
        tasteProfile = "Bitter",
        rating = 2,
        isSynced = false
    ),
    BrewLog(
        id = 4,
        beanName = "Guatemala Antigua",
        roastLevel = "Medium",
        brewMethod = "Moka Pot",
        grindClicks = 12,
        doseGrams = 17.0,
        brewTimeSeconds = 225,
        tasteProfile = "Balanced",
        rating = 4,
        isSynced = true
    ),
    BrewLog(
        id = 5,
        beanName = "Kenya AA",
        roastLevel = "Medium",
        brewMethod = "Espresso",
        grindClicks = 8,
        doseGrams = 18.5,
        brewTimeSeconds = 28,
        tasteProfile = "Balanced",
        rating = 5,
        isSynced = false
    )
)

// ─────────────────────────────────────────────────────────────────────────────
// Helper Function
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Suggests a grind adjustment based on the perceived taste profile.
 *
 * - **Sour** → extract more → go **2 clicks finer** (lower click count).
 * - **Bitter** → extract less → go **2 clicks coarser** (higher click count).
 * - **Balanced** → the grind is dialed in; keep current setting.
 *
 * @param grindClicks  Current grinder click setting.
 * @param tasteProfile One of "Sour", "Balanced", or "Bitter".
 * @return A human-readable recommendation string.
 */
fun recommendGrindAdjustment(grindClicks: Int, tasteProfile: String): String =
    when (tasteProfile) {
        "Sour" -> {
            val target = grindClicks - 2
            "Your brew tastes sour — try going 2 clicks finer ($grindClicks → $target clicks) to increase extraction."
        }
        "Bitter" -> {
            val target = grindClicks + 2
            "Your brew tastes bitter — try going 2 clicks coarser ($grindClicks → $target clicks) to reduce extraction."
        }
        "Balanced" -> {
            "Your brew tastes balanced — keep your current grind at $grindClicks clicks. Great dial-in!"
        }
        else -> "Unknown taste profile \"$tasteProfile\". Expected \"Sour\", \"Balanced\", or \"Bitter\"."
    }
