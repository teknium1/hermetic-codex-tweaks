# Hermetic Codex Tweaks

A small NeoForge 1.21.1 companion mod for [The Hermetic Codex](https://github.com/teknium1/hermetic-codex) modpack.

## What it does

Adds a configurable X/Y pixel offset to the Cobblemon party HUD so you can slide it away from the centered-left default position.

## Config

Config file: `<minecraft>/config/hermetic_codex_tweaks-client.toml`

```toml
[party_hud]
partyOffsetX = 0   # negative = left, positive = right
partyOffsetY = 0   # negative = up,   positive = down
```

Values are in GUI-scaled pixels.

## Implementation notes

The mixin uses matrix translation (`PoseStack.translate`) rather than rewriting Kotlin local variables in `PartyOverlay#render`. This is resilient across Cobblemon refactors — if Cobblemon renames or reshapes the render method, the worst case is that the mixin doesn't apply and players don't get the offset (config in `hermetic_codex_tweaks.mixins.json` sets `defaultRequire = 0`).

## Building

Requires JDK 21 with javac.

```bash
JAVA_HOME=/path/to/jdk-21 ./gradlew build
```

Output: `build/libs/hermetic_codex_tweaks-<version>.jar`

## License

MIT.
