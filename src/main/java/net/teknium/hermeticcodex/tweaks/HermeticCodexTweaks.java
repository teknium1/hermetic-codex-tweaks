package net.teknium.hermeticcodex.tweaks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(HermeticCodexTweaks.MOD_ID)
public final class HermeticCodexTweaks {
    public static final String MOD_ID = "hermetic_codex_tweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger("HermeticCodexTweaks");

    public HermeticCodexTweaks(IEventBus modEventBus, ModContainer modContainer) {
        // Register client-only config.
        modContainer.registerConfig(ModConfig.Type.CLIENT, TweaksConfig.SPEC);
        LOGGER.info("Hermetic Codex Tweaks loaded.");
    }
}
