# Playable Pehkui
---
## Practical Pehkui Playability Patches
A collection of patches and additional features for pehkui designed to make the game playable at larger and smaller scales.

### Features:
- small players can climb certain blocks with climbable-appearing textures, especially groundcover blocks for navigation

### Planned features:
- change certain default pehkui scaling dependencies to be more realistic and playable (less linear)
- large players can slowly pass through certain obstructions such as leaves
- large players can break and place multiple blocks at once on a custom large-scale grid

## Build Instructions
Current Branch: `main`
Mod Loader: `fabric`
Minecraft Version: `1.20.1`

- **Note:** Build and Pre-Setup Instructions may change because of different modloaders and/or different versions. These instructions are only valid for the current branch/modloader/mc version combination.

### Pre-Setup
1. Change the Java SDK under `Project Structure > Project Settings > Project` to be >21.
    - Loom requires a newer version of the JDK for the gradle plugin, even if it's perfectly capable of exporting JDK 17 jars (which is the case for this Minecraft version).
2. Optionally run `:genSourcesWithVineflower`.
    - You don't really need this since fabric does binary patching instead of source patching, but it's a nice to have as the Minecraft sources are indexed and can be easily navigated, this would also get you parameter names and javadocs via Parchment.
    - To attach the sources open a Minecraft source file so that IDEA decompiles it, then click add sources. It may be necessary to press the "Refresh" button a couple of times on the modal that appears, IDEA should put you on the correct folder for the current iteration of the jar (since the hash changes if you add/remove/modify access wideners).
        - To check if things are working open `ResourceLocation`. The parameters should be readable (ex: `location`, `separator`, `namespace`, `path`) and not just `p1`, `p2`, etc.
        - For completion, the path where the sources can be found if IDEA cannot automatically detect it is: `.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-<hash>/`. Sorting this folder's subfolders by "Last modified" time usually is enough to resolve the current Minecraft source.
### Build Instructions
1. Use the `gradle :build` task. The mod can be found under `build/libs/`.