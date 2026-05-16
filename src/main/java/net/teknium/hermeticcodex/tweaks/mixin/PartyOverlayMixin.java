package net.teknium.hermeticcodex.tweaks.mixin;

import com.cobblemon.mod.common.client.gui.PartyOverlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.teknium.hermeticcodex.tweaks.TweaksConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Translates the entire Cobblemon party HUD by the configured (X, Y) pixel
 * offset. We do this with a matrix push/translate at HEAD and a pop at every
 * RETURN site so the early-return paths in PartyOverlay#render (no-party,
 * cannot-render) are balanced automatically.
 *
 * Approach: matrix translation. We do NOT modify local variables (panelX /
 * startY) inside Cobblemon's render method — Kotlin local-variable matching
 * is brittle across recompiles and Cobblemon updates. Translating the
 * PoseStack achieves the same visual result and survives all Cobblemon
 * refactors short of a complete render-pipeline rewrite.
 *
 * defaultRequire = 0 in the mixin config means if this mixin fails to apply
 * (e.g. Cobblemon renames the class), the game still boots — the player just
 * won't get the offset.
 */
@Mixin(PartyOverlay.class)
public abstract class PartyOverlayMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void hermetic_codex_tweaks$pushPartyOffset(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        int offsetX = TweaksConfig.PARTY_OFFSET_X.get();
        int offsetY = TweaksConfig.PARTY_OFFSET_Y.get();
        PoseStack pose = context.pose();
        pose.pushPose();
        if (offsetX != 0 || offsetY != 0) {
            pose.translate((float) offsetX, (float) offsetY, 0.0F);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void hermetic_codex_tweaks$popPartyOffset(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.pose().popPose();
    }
}
