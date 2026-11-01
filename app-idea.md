# App idea

Fill this in before you start prototyping. Keep it short and concrete. You will defend these answers
in the project spot-check, especially the mobile-computing framing at the bottom.

## The basics

- **App name:** BrewLab
- **One-line pitch:** An offline-first coffee extraction journal and grind dial-in assistant for home coffee enthusiasts to log brews and calculate optimal recipe adjustments. 
- **The problem:** Dialing in coffee across different brewing methods requires tracking grind clicks, dose, time, and taste; without structured logs, users waste beans guessing how to fix sour or bitter brews.
- **Target users:** Home coffee enthusiasts brewing in kitchens, cafes, or outdoors with spotty signal who need quick recipe adjustments at the grinder.

## The prototype scope

Keep this tiny: one or two screens for Lab 3.

- **Core screen(s):**
  1. **Brew Log List Screen:** Shows past brews with bean details, grind settings, taste badges, and a filter by brew method. 
  2. **Add Brew & Dial-In Screen:** A form to log a new brew alongside an instant grind click recommendation based on previous extraction results.
- **The main "thing" the app manages:** A `BrewLog` record representing a single coffee brew session.
- **A few fields of that thing:** `id`, `beanName`, `roastLevel`, `brewMethod` (Moka Pot, Espresso, V60), `grindClicks`, `doseGrams`, `brewTimeSeconds`, `tasteProfile` (Sour, Balanced, Bitter), `rating` (1–5), `isSynced`.

## The data

- **What records does the app create or collect?** User-generated `BrewLog` records and saved `CoffeeBean` profiles.
- **Where does data come from?** User input (grind clicks, dose, time, taste) and a remote REST API providing a shared catalog of coffee beans.
- **Where does data need to go?** Saved locally on the device and synced to a backend server to back up logs and update the coffee bean catalog.

## Mobile-computing framing (required)

This is what makes it a mobile-computing project rather than just an app. Answer honestly; rough
answers are fine for now.

- **What must persist on the device** (survive a restart)?
  The user's `BrewLog` history, saved `CoffeeBean` profiles, and a cached copy of the remote bean catalog.
- **What crosses the network** (comes from or goes to a server or API)?
  Fetching the shared coffee bean catalog and uploading new or edited user brew logs to the backend server.
- **What must still work with no signal**, and how should it behave offline?
  Viewing past brews, getting grind dial-in suggestions, and logging new brews must work 100% offline. Offline logs are saved locally with `isSynced = false` and synced automatically in the background when connectivity returns.
- **The placement question:** is there any computation that could run on-device instead of in the
  cloud (for example a model, a search, image processing)? If you moved it on-device, what would you
  trade in latency, energy, privacy, and bandwidth?
  - **Computation:** *Dial-In Grind Recommendation Engine* — calculating how many clicks finer or coarser to adjust the grinder based on roast level, brew time, and past taste results (sour/under-extracted vs. bitter/over-extracted).
  - **Latency:** Running the calculation on-device over local logs gives instant (<10 ms) UI feedback as the user adjusts parameters, avoiding a 200–500 ms network round-trip.
  - **Bandwidth:** On-device execution uses zero network traffic and works offline, whereas cloud inference requires JSON requests on every parameter tweak.
  - **Energy:** Running lightweight math locally on the CPU uses much less battery than waking up the Wi-Fi/cellular radio for cloud requests.
  - **Privacy & Accuracy Trade-off:** On-device computation keeps personal routines local, whereas a cloud model could trade away offline capability for better "cold-start" accuracy on new beans by learning across all users' global logs.

## What comes next

You do not need to plan the rest of the course here. Each upcoming lab takes one part of this app
and makes it real (structure first, then networking, storage, offline behaviour, and so on). You will
sketch that roadmap for your own app in `reflection.md`, once you have built the prototype and seen
first hand where it falls short.
