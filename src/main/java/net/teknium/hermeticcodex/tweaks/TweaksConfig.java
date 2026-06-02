package net.teknium.hermeticcodex.tweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Config for Hermetic Codex Tweaks. Two specs:
 *
 *  CLIENT (config/hermetic_codex_tweaks-client.toml) — Cobblemon party HUD offset.
 *      partyOffsetX = 0   # negative = left,  positive = right
 *      partyOffsetY = 0   # negative = up,    positive = down
 *
 *  SERVER (config/hermetic_codex_tweaks-server.toml) — autosave cadence.
 *      autosaveIntervalTicks = 72000   # ticks between autosaves (72000 = 60 min @ 20 TPS)
 *                                      # set to 0 to restore vanilla (~6000 / 5 min) behavior
 *
 * The autosave override exists because this pack loads many dimensions (Create
 * Aeronautics / Sable sub-levels + Cataclysm dimensions + nether/end/etc.), and
 * the synchronous saveEverything() flush stalls the server thread for multiple
 * seconds on every vanilla 5-minute autosave. Stretching the interval to 60 min
 * trades save frequency (more rollback on a hard crash) for far fewer hitches.
 */
public final class TweaksConfig {
    // CLIENT spec
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfigSpec.IntValue PARTY_OFFSET_X;
    public static final ModConfigSpec.IntValue PARTY_OFFSET_Y;

    // SERVER spec
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfigSpec.IntValue AUTOSAVE_INTERVAL_TICKS;

    static {
        ModConfigSpec.Builder c = new ModConfigSpec.Builder();
        c.push("party_hud");
        c.comment("Horizontal offset for the Cobblemon party HUD in GUI-scaled pixels. "
                + "Negative values move the HUD left, positive values move it right. "
                + "Vanilla Cobblemon anchors at x=0 (left edge of the screen).");
        PARTY_OFFSET_X = c.defineInRange("partyOffsetX", 0, -2000, 2000);
        c.comment("Vertical offset for the Cobblemon party HUD in GUI-scaled pixels. "
                + "Negative values move the HUD up, positive values move it down. "
                + "Vanilla Cobblemon vertically centers the party.");
        PARTY_OFFSET_Y = c.defineInRange("partyOffsetY", 0, -2000, 2000);
        c.pop();
        CLIENT_SPEC = c.build();

        ModConfigSpec.Builder s = new ModConfigSpec.Builder();
        s.push("autosave");
        s.comment("Ticks between server autosaves (20 ticks = 1 second). "
                + "Vanilla is ~6000 (5 minutes). Default here is 72000 (60 minutes) to avoid "
                + "the multi-second save-stall hitch caused by flushing every loaded dimension. "
                + "Set to 0 to restore vanilla autosave behavior. "
                + "WARNING: a longer interval means more progress is lost if the server hard-crashes "
                + "between saves. Manual /save-all still works any time.");
        AUTOSAVE_INTERVAL_TICKS = s.defineInRange("autosaveIntervalTicks", 72000, 0, 1728000);
        s.pop();
        SERVER_SPEC = s.build();
    }

    private TweaksConfig() {}
}
