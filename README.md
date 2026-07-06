# Flappy Bird

A Flappy Bird clone built with **LibGDX 1.10.0** for Android.

## How to Play

Tap anywhere on the screen to make the bird flap (fly upward). Navigate through the gaps between the green pipes. Each pipe pair you pass scores one point. Hitting a pipe or the top/bottom of the screen ends the game. Tap again to restart.

## Features

- Touch-based flap controls
- 2-frame wing animation
- 4 scrolling pipe pairs with random gap positions
- Score & high score tracking (persisted during the session)
- Progressive difficulty – pipe speed increases from 4.0 up to 11.0 as your score grows
- Forgiving collision hitboxes (100px smaller than visible pipes)

## Project Structure

```
flappybird/
├── core/                          # Game logic (pure Java/LibGDX)
│   └── src/com/mygdx/game/
│       └── FlappyBird.java        # Main game class (183 lines)
├── android/                       # Android launcher & assets
│   ├── src/com/mygdx/game/
│   │   └── AndroidLauncher.java   # Android entry point
│   └── assets/                    # Game textures
│       ├── bg.png                 # Background
│       ├── bird.png / bird2.png   # Bird sprites (wing animation)
│       └── pillar.png             # Pipe texture
│       └── gameover.jpg           # Game over overlay
│       └── tap.png                # "Tap to start" prompt
├── build.gradle                   # Root build file
├── settings.gradle                # Module includes
└── gradle/                        # Gradle wrapper
```

## Building & Running

```bash
# Build the Android APK
gradlew android:assembleDebug

# Install on connected device/emulator
gradlew android:installDebug android:run
```

Requires Android SDK (minSdk 14, targetSdk 30). Open the project in Android Studio and run on a device or emulator.

## Tech Stack

- **Framework:** LibGDX 1.10.0
- **Language:** Java
- **Platform:** Android (via gdx-backend-android)
- **Physics:** Manual (gravity + velocity) with `Intersector.overlaps()` for collision detection
