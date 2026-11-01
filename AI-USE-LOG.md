# AI-use log

You are encouraged to use AI throughout this project. In return you keep this log. It is part of
every milestone, not just Lab 3, so keep adding to it all semester. It is quick if you do it as you
work and painful if you reconstruct it later.

The point is not to prove you used AI, or that you did not. The point is to show that you **own** what
you submitted: that you understood what the model produced, judged it, and decided what to keep. In
the defense you must be able to explain and modify any of it.

## How to log

One row per meaningful AI interaction. You do not need to log trivial autocompletions; do log
anything where the model produced a chunk of code or a design decision you accepted or rejected.

| Date | What you were trying to do | What you prompted (short) | What the model produced | What you accepted or rejected, and WHY |
|---|---|---|---|---|
| 01.11.2026 | Define the `BrewLog` data model, in-memory sample list, and on-device grind recommendation helper. | Create `BrewLog` data class with our 10 specified fields, 5 hardcoded sample logs, and `recommendGrindAdjustment` helper function. | A `BrewLog.kt` file with the `BrewLog` data class, a `sampleBrewLogs` list of 5 entries, and the `recommendGrindAdjustment` function. | Accepted the whole file because the 10 data class fields matched our `app-idea.md` specification|
| 01.11.2026 | Build Screen 1 (`BrewListScreen`) with brew method filter chips, a scrollable list of logs, and wire it into `MainActivity`. | Create `BrewListScreen` composable in Material 3 with `FilterChip` row for brew methods, `LazyColumn` of `BrewLog` cards, and FAB to add a brew. | Created `BrewListScreen.kt` and updated `MainActivity.kt` to replace the template `Greeting` with `BrewListScreen`. | Accepted all changes: using `remember(logs, selectedFilter)` cleanly filters the list in memory, and decomposing the card into `LabelBadge` and `BrewDetailRow` keeps the UI code modular and easy to read. |
| 01.11.2026 | Build Screen 2 (`AddBrewScreen`) with live dial-in recommendation card and wire state navigation in `MainActivity`. | Create `AddBrewScreen` composable with form inputs and live `recommendGrindAdjustment` preview card, and wire state navigation in `MainActivity`. | Created `AddBrewScreen.kt` and `BrewLabApp()` in `MainActivity.kt`. | Accepted all: `canSave` prevents NumberFormatException crashes on invalid number inputs, and `ChipRow` eliminates duplicate `FilterChip` code. |


## Prompts worth remembering

Optionally, keep a few prompts that worked especially well or especially badly, with a note on why.
This is useful for you, and for your team if you are working in one.

-
-
