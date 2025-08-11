package me.pajic.soaring_phantoms.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.soaring_phantoms.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/world/level/levelgen/PhantomSpawner;nextTick:I",
                    ordinal = 1
            )
    )
    private void modifySpawnCheckFrequency(PhantomSpawner instance, int value, @Local RandomSource randomSource) {
        instance.nextTick += (Main.CONFIG.spawnFrequencyBase.get() + randomSource.nextInt(Main.CONFIG.spawnFrequencyRandomOffsetBound.get())) * 20;
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isSpectator()Z"
            )
    )
    private boolean repelIfHoldingRepellentItem(boolean original, @Local ServerPlayer serverPlayer) {
        if (original || !Main.CONFIG.phantomsRepelledByItem.get()) {
            return true;
        }
        return Main.CONFIG.repellentItems.get().contains(BuiltInRegistries.ITEM.getKey(serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem())) ||
                Main.CONFIG.repellentItems.get().contains(BuiltInRegistries.ITEM.getKey(serverPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem()));
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 1
            )
    )
    private int modifyCondition(int original, @Local BlockPos playerBlockPos, @Local RandomSource randomSource) {
        if (Main.CONFIG.doAltitudeBasedSpawning.get()) {
            return randomSource.nextInt(playerBlockPos.getY());
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=72000"
            )
    )
    private int modifyConditionCheckValue(int original, @Local BlockPos playerBlockPos, @Local(argsOnly = true) ServerLevel level) {
        if (Main.CONFIG.doAltitudeBasedSpawning.get()) {
            if (Main.CONFIG.passivePhantomsBeforeEnderDragon.get()) {
                if (!level.getServer().getWorldData().endDragonFightData().previouslyKilled()) {
                    return Main.CONFIG.passiveSpawnStartHeight.get();
                }
            }
            return Main.CONFIG.spawnStartHeight.get();
        }
        return original;
    }
}