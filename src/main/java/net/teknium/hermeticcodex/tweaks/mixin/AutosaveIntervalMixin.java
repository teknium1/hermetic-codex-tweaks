package net.teknium.hermeticcodex.tweaks.mixin;

import net.minecraft.server.MinecraftServer;
import net.teknium.hermeticcodex.tweaks.TweaksConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides the vanilla autosave cadence. Vanilla computeNextAutosaveInterval()
 * returns ~6000 ticks (5 minutes at 20 TPS). On a heavily-dimensioned pack
 * (Create Aeronautics / Sable sub-levels + 8 Cataclysm dimensions + nether/end/
 * deeperdarker/raid), the synchronous saveEverything() flush of every loaded
 * dimension stalls the server thread for multiple seconds each save.
 *
 * We replace the computed interval with a configurable tick count (default
 * 72000 = 60 minutes). The vanilla scaling math (which keeps wall-clock cadence
 * stable across tickrate changes) is bypassed entirely — we want a fixed,
 * infrequent cadence, not a tickrate-relative one.
 *
 * defaultRequire is 0 in the mixin config, so if the target method is ever
 * renamed in a future NeoForge release this mixin silently no-ops and the
 * server falls back to vanilla 5-minute autosaves rather than crashing.
 */
@Mixin(MinecraftServer.class)
public abstract class AutosaveIntervalMixin {

    @Inject(method = "computeNextAutosaveInterval", at = @At("HEAD"), cancellable = true)
    private void hermetic$overrideAutosaveInterval(CallbackInfoReturnable<Integer> cir) {
        int ticks = TweaksConfig.AUTOSAVE_INTERVAL_TICKS.get();
        // 0 or negative means "leave vanilla behavior alone".
        if (ticks > 0) {
            cir.setReturnValue(ticks);
        }
    }
}
