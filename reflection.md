# Prototype reflection: where AI stopped

Fill this in after Part 4, once you have stress-tested your prototype. This is the bridge from Lab 3
to the rest of the course. Be honest and specific; a vague "it works fine" means you did not push
hard enough.

## What AI did well

Where did AI genuinely accelerate you? (screens, sample data, navigation, boilerplate, ...)

- **Building the screens quickly:** AI generated the brew list screen, filter chips, and the form for adding a new coffee in just a few minutes.
- **Realistic sample data:** It came up with five believable coffee logs right away.
- **Form checks and live tips:** It set up the Grind Tip box to update immediately when changing the grind clicks or taste, and disabled the Save button until all fields are filled out properly.

## What broke when you stressed it

Go through the checks from Part 4 and record what actually happened.

- **Rotate the device:** Everything resets. If you add a new coffee, select a filter, or start typing in the "New Brew" form and then rotate the screen, the app goes right back to the main screen with only the original 5 sample coffees.
- **Kill and reopen the app:** Nothing is saved. Any new brews added during the session disappear as soon as the app is closed and reopened.
- **Airplane mode:** The app doesn't crash because it doesn't use the internet yet. However, new brews always show up as "Offline (Pending)" and never actually sync, even after turning Airplane mode off.
- **500 items in a list:** Scrolling through the list and filtering stay smooth because only the cards visible on the screen are drawn at a time
- **Two people editing the same data:** There is no way for two phones to share data or handle conflicts yet. Since each new brew just gets the next number as its ID (like 6, 7, 8), two people logging coffees offline would create different brews with the exact same ID.

## What is missing for production

List what your prototype does not have yet. For each gap, name the lab that will address it.

| Gap in the prototype | Lab that fixes it |
|---|---|
| No separation of UI and logic; state resets on rotation and the system back button closes the app | Lab 4 (architecture) |
| No data from a real server; no shared coffee bean catalog | Lab 5 (networking) |
| Nothing is saved on the device; new brews vanish when the app closes | Lab 6 (persistence) |
| "Offline (Pending)" badge is fake; no real background sync or conflict handling | Lab 7 (offline-first, WorkManager) |
| Dependencies and data lists are created directly inside the screens | Lab 8 (dependency injection) |
| No tests; nothing checks if the grind tip logic or form breaks | Lab 9 (testing, CI) |
| Grind tip is just a simple rule instead of learning from past brew history | Lab 10 (integrated concept) |

Add any gaps specific to your app that are not in the table above.

## One sentence

In your own words: what is the difference between what AI gave you today and a production app, and why
can AI not simply close that gap for you?

> AI made a good-looking visual shell that only holds temporary data in memory, while a real app needs proper state management, local storage, and offline syncing rather than just some generated UI code.
