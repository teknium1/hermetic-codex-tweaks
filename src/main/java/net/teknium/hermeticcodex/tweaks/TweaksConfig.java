package net.teknium.hermeticcodex.tweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Client config — controls Cobblemon party HUD position offset.
 *
 * Config lives at: <minecraft>/config/hermetic_codex_tweaks-client.toml
 *
 *   partyOffsetX = 0   # negative = left, positive = right
 *   partyOffsetY = 0   # negative = up,   positive = down
 *
 * Defaults are 0/0 (no change vs. vanilla Cobblemon). Set partyOffsetY to a
 * positive integer to slide the party HUD downward. The values are pixels
 * in GUI-scaled coordinates, so they're consistent across GUI scale levels.
 */
public final class TweaksConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue PARTY_OFFSET_X;
    public static final ModConfigSpec.IntValue PARTY_OFFSET_Y;

    static {
        ModConfigSpec.Builder b = new ModConfigSpec.Builder();
        b.push("party_hud");
        b.comment("Horizontal offset for the Cobblemon party HUD in GUI-scaled pixels. "
                + "Negative values move the HUD left, positive values move it right. "
                + "Vanilla Cobblemon anchors at x=0 (left edge of the screen).");
        PARTY_OFFSET_X = b.defineInRange("partyOffsetX", 0, -2000, 2000);
        b.comment("Vertical offset for the Cobblemon party HUD in GUI-scaled pixels. "
                + "Negative values move the HUD up, positive values move it down. "
                + "Vanilla Cobblemon vertically centers the party.");
        PARTY_OFFSET_Y = b.defineInRange("partyOffsetY", 0, -2000, 2000);
        b.pop();
        SPEC = b.build();
    }

    private TweaksConfig() {}
}
