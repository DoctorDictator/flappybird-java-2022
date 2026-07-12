# Flappy Bird — Java/LibGDX Android Game

A lightweight Flappy Bird-style game written in Java with [LibGDX](https://libgdx.com/) and packaged as an Android application.

The player taps the screen to keep the bird airborne, passes through randomly positioned pipe gaps, earns points, and attempts to beat the highest score recorded during the current app session.

> [!IMPORTANT]
> This repository is an educational fan-made clone. It is not affiliated with, endorsed by, or sponsored by the original Flappy Bird creator, publisher, or any related trademark owner. Review the [Licensing and asset notice](#licensing-and-asset-notice) before redistributing the project or its artwork.

---

## Table of Contents

- [Overview](#overview)
- [Gameplay](#gameplay)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Building from the Command Line](#building-from-the-command-line)
- [Project Structure](#project-structure)
- [Architecture and Game Flow](#architecture-and-game-flow)
- [Gameplay Configuration](#gameplay-configuration)
- [Assets](#assets)
- [Android Configuration](#android-configuration)
- [Known Limitations](#known-limitations)
- [Troubleshooting](#troubleshooting)
- [Suggested Improvements](#suggested-improvements)
- [Contributing](#contributing)
- [Licensing and Asset Notice](#licensing-and-asset-notice)

---

## Overview

This project contains two active Gradle modules:

- **`core`** — platform-independent game logic and rendering code.
- **`android`** — Android launcher, Android resources, native libraries, and game assets.

The implementation deliberately stays small. Most gameplay behavior is contained in one class, `FlappyBird.java`, making the project useful for beginners who want to study:

- A basic LibGDX application lifecycle.
- Touch input handling.
- Sprite rendering with `SpriteBatch`.
- Simple velocity-and-gravity movement.
- Procedural obstacle placement.
- Collision detection with circles and rectangles.
- Score tracking and state-based game flow.

The project currently targets Android only. It does not include desktop, iOS, HTML, or headless launchers.

---

## Gameplay

### Objective

Guide the bird through the gaps between the pipes for as long as possible. Every pipe pair passed increases the score by one.

### Controls

| Action | Control |
|---|---|
| Start the game | Tap anywhere on the screen |
| Flap upward | Tap anywhere while playing |
| Restart after losing | Tap anywhere on the game-over screen |

### Game Rules

1. The bird begins near the horizontal and vertical center of the screen.
2. Gravity continuously pulls the bird downward while the game is active.
3. Tapping gives the bird an immediate upward velocity.
4. Pipe pairs move from right to left.
5. Each recycled pipe pair receives a new random vertical offset.
6. Passing a pipe pair awards one point.
7. Hitting a pipe or leaving the playable vertical area ends the run.
8. The highest score is retained only while the application remains open.

---

## Features

- Single-tap mobile controls.
- Portrait-screen Android gameplay.
- Two-frame bird wing animation.
- Four reusable scrolling pipe pairs.
- Randomized pipe-gap positions.
- Current score display.
- Session-only high-score display.
- Increasing pipe speed as the score rises.
- Circle-versus-rectangle collision detection.
- Start prompt and game-over overlay.
- Android ARM and x86 native library packaging.

---

## Technology Stack

| Component | Version or implementation |
|---|---|
| Language | Java |
| Game framework | LibGDX `1.10.0` |
| Android backend | `gdx-backend-android` |
| Build system | Gradle wrapper `6.7.1` |
| Android Gradle Plugin | `3.5.4` |
| Compile SDK | Android API 30 |
| Target SDK | Android API 30 |
| Minimum SDK | Android API 14 |
| Java source compatibility | Java 7 |
| Rendering | LibGDX `SpriteBatch` and `Texture` |
| Text rendering | LibGDX `BitmapFont` |
| Collision detection | LibGDX `Circle`, `Rectangle`, and `Intersector` |
| Physics | Custom frame-based velocity and gravity |

Although Box2D dependencies are declared in the Gradle files, the current gameplay code does not use Box2D.

---

## Requirements

To build and run the project, install the following:

- Android Studio with Android SDK support, or a compatible command-line Android SDK installation.
- Android SDK Platform 30.
- Android SDK Build Tools 30.0.3.
- A Java Development Kit compatible with the legacy Android Gradle Plugin configuration.
- An Android device or emulator running API 14 or later.
- Internet access for the first Gradle build, unless all dependencies are already cached.

> [!NOTE]
> This project uses a legacy Android build configuration. A current Android Studio or JDK may report compatibility errors. See [Troubleshooting](#troubleshooting) and [Suggested Improvements](#suggested-improvements) for guidance.

---

## Getting Started

### 1. Extract or clone the project

Place the project in a directory where you have read and write permission.

```bash
git clone <repository-url>
cd flappybird-java-2022-main
```

When using the downloaded ZIP, extract it and open the extracted root directory—the folder containing `settings.gradle`, `build.gradle`, `gradlew`, `core`, and `android`.

### 2. Open the project in Android Studio

1. Start Android Studio.
2. Select **Open**.
3. Choose the repository root directory.
4. Allow Gradle to synchronize the project.
5. Install API 30 and Build Tools 30.0.3 if Android Studio requests them.
6. Select the **android** run configuration.
7. Choose a connected device or emulator.
8. Run the application.

### 3. Configure the Android SDK path when necessary

If Gradle cannot locate the Android SDK, create a `local.properties` file in the project root:

```properties
sdk.dir=/absolute/path/to/your/Android/Sdk
```

Example on Windows:

```properties
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
```

Example on Linux:

```properties
sdk.dir=/home/yourname/Android/Sdk
```

Example on macOS:

```properties
sdk.dir=/Users/yourname/Library/Android/sdk
```

Do not commit `local.properties`; it contains machine-specific configuration.

---

## Building from the Command Line

Run all commands from the repository root.

### Linux and macOS

Make the Gradle wrapper executable if required:

```bash
chmod +x gradlew
```

Build a debug APK:

```bash
./gradlew android:assembleDebug
```

Install the debug APK on a connected device or running emulator:

```bash
./gradlew android:installDebug
```

Launch the installed application through the custom Gradle task:

```bash
./gradlew android:run
```

Run a clean build:

```bash
./gradlew clean android:assembleDebug
```

### Windows

Build a debug APK:

```powershell
.\gradlew.bat android:assembleDebug
```

Install it:

```powershell
.\gradlew.bat android:installDebug
```

Launch it:

```powershell
.\gradlew.bat android:run
```

### APK output

A successful debug build normally creates the APK under:

```text
android/build/outputs/apk/debug/android-debug.apk
```

A release build can be requested with:

```bash
./gradlew android:assembleRelease
```

The release configuration enables code shrinking. A distributable release APK must also be signed using your own Android signing configuration.

### Useful Gradle tasks

```bash
# List available tasks
./gradlew tasks

# Compile the core Java module
./gradlew core:compileJava

# Show dependency information
./gradlew android:dependencies

# Remove generated build output
./gradlew clean
```

---

## Project Structure

```text
flappybird-java-2022-main/
├── android/
│   ├── AndroidManifest.xml             # Android app and activity declarations
│   ├── assets/                         # Runtime textures loaded by LibGDX
│   │   ├── bg.png                      # Scaled game background
│   │   ├── bird.png                    # Bird animation frame 1
│   │   ├── bird2.png                   # Bird animation frame 2
│   │   ├── gameover.jpg                # Game-over overlay
│   │   ├── pillar.png                  # Texture used for both upper/lower pipes
│   │   ├── tap.png                     # Tap-to-start prompt
│   │   └── ...                         # Additional currently unused assets
│   ├── res/                            # Android icons, styles, colors, and app name
│   ├── src/com/mygdx/game/
│   │   └── AndroidLauncher.java        # Android entry point
│   ├── build.gradle                    # Android module configuration
│   └── proguard-rules.pro              # Release shrinking rules
├── core/
│   ├── src/com/mygdx/game/
│   │   └── FlappyBird.java             # Game state, movement, rendering, scoring
│   └── build.gradle                    # Core Java module configuration
├── gradle/
│   ├── wrapper/                        # Active Gradle wrapper files
│   ├── build.gradle                    # Duplicate/legacy nested project file
│   ├── settings.gradle                 # Duplicate/legacy nested project file
│   └── ...                             # Extra copied project configuration
├── build.gradle                        # Root dependencies and shared versions
├── settings.gradle                     # Includes the android and core modules
├── gradle.properties                   # Gradle daemon and memory settings
├── gradlew                             # Unix Gradle wrapper script
├── gradlew.bat                         # Windows Gradle wrapper script
├── LICENSE.md                          # Source-code license and asset notice
└── README.md                           # Project documentation
```

### About the nested `gradle/` directory

The root `gradle/` folder contains the expected wrapper files, but it also contains what appears to be a duplicate or legacy project configuration, including another `build.gradle`, `settings.gradle`, wrapper scripts, and module folders.

The active project is the repository root. Use the root-level:

- `settings.gradle`
- `build.gradle`
- `gradlew`
- `gradlew.bat`
- `android/`
- `core/`

Do not open the nested `gradle/` folder as the main Android Studio project unless you intentionally want to inspect or remove the duplicate copy.

---

## Architecture and Game Flow

### Android entry point

`android/src/com/mygdx/game/AndroidLauncher.java` extends LibGDX's `AndroidApplication`. Its `onCreate` method constructs an `AndroidApplicationConfiguration` and starts a new `FlappyBird` instance.

```java
initialize(new FlappyBird(), config);
```

### Core game class

`core/src/com/mygdx/game/FlappyBird.java` extends `ApplicationAdapter` and contains the entire game implementation.

The two primary lifecycle methods are:

- **`create()`** — loads textures, creates the sprite batch and fonts, calculates spacing, initializes collision objects, and calls `startGame()`.
- **`render()`** — handles input, updates movement, recycles pipes, detects collisions, renders the scene, and switches between game states.

### Game states

The integer `gameState` controls the current phase:

| Value | State | Behavior |
|---:|---|---|
| `0` | Ready | Shows the tap prompt and waits for the first touch |
| `1` | Playing | Updates the bird, pipes, score, speed, and collisions |
| `2` | Game over | Shows the game-over image and waits for a restart tap |

### Bird movement

The bird uses two values:

- `velocity` — current vertical velocity.
- `gravity` — amount added to velocity every rendered frame.

While playing:

```java
velocity += gravity;
birdY -= velocity;
```

A tap sets:

```java
velocity = -30.0f;
```

Because the render code subtracts velocity from `birdY`, a negative velocity moves the bird upward.

### Pipe system

The game creates four pipe pairs. Each pair has:

- A horizontal position in `tubeX`.
- A randomized vertical offset in `tubeOffset`.
- One upper collision rectangle.
- One lower collision rectangle.

When a pipe exits the left side of the screen, it is moved behind the other pipes instead of being destroyed and recreated. This object-recycling approach keeps the implementation simple and avoids repeated allocations for pipe positions.

### Pipe spacing

The spacing between pipe pairs is calculated from the screen width:

```text
distanceBetweenTubes = screenWidth × 3.5 ÷ 4
```

The first pipe begins approximately one screen width to the right of the center position, and the remaining pipes are placed at equal intervals.

### Scoring

`scoringTube` identifies the next pipe pair that can award a point. When that pipe's x-position passes the horizontal center of the screen:

1. `score` is incremented.
2. Pipe speed may increase.
3. `scoringTube` advances to the next pipe index and wraps back to zero after the fourth pipe.

### Difficulty progression

The initial pipe speed is `4.0` pixels per rendered frame.

- Up to score 100, the speed increases by `0.1` after each point while the speed is at or below `8.0`.
- From score 101 through 200, it increases by `0.1` while the speed is at or below `11.0`.
- Beyond score 200, speed is assigned `11.0`.

Because the checks use `<=` before adding `0.1`, the value can briefly exceed the intended threshold by `0.1`. For example, a value of `8.0` can become `8.1`.

### Collision detection

The bird is represented by a `Circle`. Each pipe is represented by a `Rectangle`. LibGDX's `Intersector.overlaps` checks the bird against all upper and lower pipe rectangles every frame.

The rectangle width and height are each reduced by 100 pixels compared with the visible pipe texture. This creates a more forgiving collision area, although the reduction is applied asymmetrically because the rectangle origin is not adjusted.

The game also ends when the bird's y-position reaches or passes the bottom or top screen boundary.

### High score behavior

`highScore` is updated when the player taps to restart after a game over. It is stored only in memory, so it resets when the application process closes or restarts.

---

## Gameplay Configuration

The current gameplay values are fields in `FlappyBird.java`.

| Setting | Field | Default | Purpose |
|---|---|---:|---|
| Number of pipe pairs | `numberOfTubes` | `4` | Number of obstacle pairs recycled on screen |
| Pipe gap | `gap` | `500.0f` | Vertical space between upper and lower pipes |
| Gravity | `gravity` | `2.0f` | Downward acceleration added each frame |
| Initial pipe speed | `tubeVelocity` | `4.0f` | Leftward pipe movement per frame |
| Flap velocity | assigned to `velocity` | `-30.0f` | Upward impulse after a tap |
| Score font scale | `font` scale | `10.0f` | Main score text size |
| High-score label scale | `high` scale | `5.0f` | High-score text size |
| Pipe hitbox reduction | rectangle dimensions | `100 px` | Makes collisions more forgiving |

### Changing the difficulty

Common adjustments can be made directly in `FlappyBird.java`:

- Increase `gap` to make the pipe opening larger.
- Decrease `gravity` for slower falling.
- Make the flap impulse more negative for stronger jumps.
- Reduce `tubeVelocity` for slower obstacles.
- Increase `numberOfTubes` for more simultaneously recycled obstacles.

After changing a value, rebuild and reinstall the application.

> [!CAUTION]
> The current values are pixel-based and frame-based. They do not scale consistently across every display resolution or frame rate.

---

## Assets

LibGDX loads runtime assets from `android/assets/` by filename.

### Assets used by the current code

| File | Dimensions | Usage |
|---|---:|---|
| `bg.png` | 288 × 512 | Stretched to fill the entire screen |
| `bird.png` | 136 × 96 | Bird animation frame 1 |
| `bird2.png` | 136 × 96 | Bird animation frame 2 |
| `pillar.png` | 500 × 2000 | Used for both upper and lower pipes |
| `gameover.jpg` | 397 × 127 | Centered game-over message |
| `tap.png` | 456 × 547 | Ready-screen tap prompt |

### Assets present but not used by `FlappyBird.java`

- `badlogic.jpg`
- `bottomtube.png`
- `pillars.png`
- `tap.jpg`
- `toptube.png`

Unused assets can be removed after confirming that no future feature depends on them.

### Replacing assets

To replace a texture without changing the Java code:

1. Keep the same filename.
2. Put the replacement in `android/assets/`.
3. Preserve transparency where appropriate.
4. Rebuild the application.

Different dimensions may affect centering, collisions, pipe placement, and the visual gap. Update the relevant calculations when replacing assets with significantly different sizes.

---

## Android Configuration

The Android module currently uses these values:

| Property | Value |
|---|---|
| Application ID | `com.mygdx.game` |
| Java package | `com.mygdx.game` |
| App label | `Flappy Bird` |
| Version code | `1` |
| Version name | `1.0` |
| Orientation | Portrait |
| Minimum SDK | 14 |
| Target SDK | 30 |
| Compile SDK | 30 |
| Build Tools | 30.0.3 |

### Renaming the application

To change the display name, edit:

```text
android/res/values/strings.xml
```

```xml
<string name="app_name">Your Game Name</string>
```

### Changing the package/application ID

A complete package rename requires updating:

- `applicationId` in `android/build.gradle`.
- The package declaration in both Java source files.
- The Java source directory structure.
- Activity references in `AndroidManifest.xml`.
- The launch component in the `android:run` Gradle task.

Use Android Studio's refactoring tools where possible to avoid leaving inconsistent references.

---

## Known Limitations

The project is functional as a compact learning example, but it has several technical limitations:

1. **Frame-rate-dependent gameplay**  
   Movement and acceleration are applied per frame rather than using `Gdx.graphics.getDeltaTime()`. The game can therefore run at different speeds on devices with different frame rates.

2. **Session-only high score**  
   The high score is not saved to `Preferences`, a file, or a database.

3. **No resource disposal**  
   Textures, fonts, and the sprite batch are not disposed in an overridden `dispose()` method.

4. **Animation is tied to frame rate**  
   The bird texture alternates every rendered frame, which can make the wings animate too quickly.

5. **Pixel-based layout**  
   Gameplay and UI positions use fixed pixel values such as `500`, `450`, `200`, and `100`. Results can vary across aspect ratios and screen densities.

6. **Limited state modeling**  
   Integer constants are used instead of a dedicated enum or screen/state classes.

7. **Asymmetric pipe hitboxes**  
   Hitbox dimensions are reduced without shifting their origins to keep them centered inside the visible textures.

8. **Boundary collision approximation**  
   Screen-boundary checks use `birdY` rather than the full circle or texture bounds.

9. **Potential speed-threshold overshoot**  
   The pipe speed may increase one step above `8.0` or `11.0` because of the current condition ordering.

10. **No automated tests**  
    The repository does not include unit, integration, or gameplay tests.

11. **No desktop launcher**  
    Testing requires an Android device/emulator unless a desktop module is added.

12. **Legacy Android toolchain**  
    The Android Gradle Plugin, target SDK, and source compatibility are old relative to modern Android projects.

13. **Unused dependencies and assets**  
    Box2D is declared but unused, and several image files are not referenced by the game.

14. **Duplicate nested project files**  
    The `gradle/` directory contains extra copied project files in addition to the wrapper.

15. **No audio or accessibility options**  
    The game has no sound, vibration controls, pause behavior, alternate input, or accessibility settings.

---

## Troubleshooting

### `Permission denied: ./gradlew`

Grant execute permission:

```bash
chmod +x gradlew
```

Then rerun the command.

### Gradle cannot download its distribution or dependencies

The first build needs access to Gradle and Maven repositories. Check:

- Internet connectivity.
- Proxy settings in Android Studio or Gradle.
- Firewall restrictions.
- Whether Gradle offline mode is enabled.

The wrapper is configured to download Gradle 6.7.1 from `services.gradle.org`.

### `SDK location not found`

Create `local.properties` in the project root and add the correct `sdk.dir`, or set the `ANDROID_HOME` environment variable.

### Missing Android API 30 or Build Tools 30.0.3

Install them through Android Studio's SDK Manager, then synchronize Gradle again.

### JDK or Gradle compatibility errors

This repository combines Android Gradle Plugin 3.5.4 with a Gradle 6.7.1 wrapper and Java 7 source compatibility. Legacy Android tooling can be sensitive to the JDK and Gradle versions used.

Possible approaches:

- Configure Android Studio to use an older compatible JDK.
- Align the wrapper version with the Android Gradle Plugin expected by the project.
- Upgrade the Android Gradle Plugin, Gradle wrapper, SDK levels, and Java compatibility together.

Make version changes on a separate branch and test them as a group rather than changing one component at a time.

### App installs but the `android:run` task fails

Confirm that:

- `adb` is available under the configured Android SDK.
- A device or emulator is connected.
- USB debugging is enabled for a physical device.
- `adb devices` lists the target as authorized.
- The application ID and launcher activity still match the values hardcoded in the task.

### Game appears too fast or too slow

The simulation is frame-based. Devices rendering more or fewer frames per second will change gameplay speed. Converting movement to delta-time-based calculations is the recommended fix.

### Images are stretched or misplaced

The background is explicitly stretched to the display size, while most other textures use their native pixel dimensions. Update rendering calculations or introduce a LibGDX `Viewport` for resolution-independent behavior.

### Release build fails during shrinking

Review `android/proguard-rules.pro`, inspect the Gradle error output, and temporarily set `minifyEnabled false` to determine whether shrinking is the cause. Re-enable shrinking after adding appropriate rules.

---

## Suggested Improvements

A practical modernization roadmap could include:

### Gameplay and code quality

- Replace integer game states with an enum.
- Split rendering, world state, input, and collision logic into separate classes.
- Use delta time for movement and animation.
- Add a fixed-step update loop for consistent gameplay.
- Center and tune collision hitboxes independently of texture dimensions.
- Add pause/resume handling.
- Persist the high score with LibGDX `Preferences`.
- Add sound effects and volume controls.
- Add a proper restart delay or button to prevent accidental restarts.

### Rendering and device support

- Introduce an `OrthographicCamera` and `Viewport`.
- Use a virtual world size instead of physical screen pixels.
- Pack textures into a `TextureAtlas`.
- Use `Animation<TextureRegion>` for timed bird animation.
- Support tablets, notches, and varied aspect ratios.
- Add a desktop launcher for faster development.

### Build and maintenance

- Upgrade Gradle and the Android Gradle Plugin.
- Increase `compileSdkVersion` and `targetSdkVersion` as required for current Android distribution.
- Move to a supported Java version.
- Remove unused Box2D dependencies if physics remains custom.
- Remove or document unused assets.
- Delete the duplicated nested project after confirming it is unnecessary.
- Add CI to compile the core and Android modules.
- Add tests for scoring, speed progression, pipe recycling, and game-state transitions.

### Resource cleanup

Add a `dispose()` implementation similar to:

```java
@Override
public void dispose() {
    batch.dispose();
    background.dispose();
    gameover.dispose();
    tap.dispose();
    topTube.dispose();
    bottomTube.dispose();
    font.dispose();
    high.dispose();

    for (Texture bird : birds) {
        bird.dispose();
    }
}
```

---

## Contributing

Contributions are welcome when you have permission to contribute the relevant code and assets.

A typical contribution workflow is:

1. Fork the repository.
2. Create a focused branch:

   ```bash
   git checkout -b feature/delta-time-physics
   ```

3. Make and test your changes.
4. Keep commits small and descriptive.
5. Update documentation for behavior or build changes.
6. Open a pull request describing:
   - What changed.
   - Why it changed.
   - How it was tested.
   - Any screenshots or recordings relevant to visual changes.

### Contribution guidelines

- Keep core gameplay code platform-independent where practical.
- Avoid committing generated build directories.
- Do not commit `local.properties`, signing keys, or credentials.
- Only submit artwork, audio, fonts, or other media that you created or are legally allowed to redistribute.
- Clearly document the source and license of new third-party assets.
- Preserve the license and copyright notices.

---

## Licensing and Asset Notice

The project source code is provided under the terms described in [`LICENSE.md`](LICENSE.md).

The source-code license does **not automatically grant rights** to third-party artwork, names, logos, characters, trademarks, or other media included in or associated with the project. The ownership and original licensing of the images currently stored in `android/assets/` are not documented in this repository.

Before publishing, selling, or redistributing this game:

1. Verify that you own or have permission to use every asset.
2. Replace any asset whose license cannot be confirmed.
3. Rename and rebrand the application where necessary.
4. Preserve notices required by LibGDX and any other dependencies.
5. Review the distribution requirements for the target app store.

“Flappy Bird” may be a protected name or trademark. Its use here is descriptive of the clone-style gameplay and does not imply affiliation with the original game or its rights holders.

---

## Build Verification Note

The project files and configuration have been reviewed for this documentation. A complete build was not executed in the documentation environment because the Gradle wrapper distribution and external dependencies could not be downloaded without network access. Build behavior should therefore be verified locally using a configured Android development environment.
