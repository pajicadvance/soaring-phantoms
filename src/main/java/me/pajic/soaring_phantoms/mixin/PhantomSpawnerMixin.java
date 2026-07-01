package me.pajic.soaring_phantoms.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.soaring_phantoms.SoaringPhantoms;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
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
    private void modifySpawnCheckFrequency(PhantomSpawner instance, int value, @Local(name = "random") RandomSource random) {
        instance.nextTick += (SoaringPhantoms.CONFIG.spawnFrequencyBase.get() + random.nextInt(SoaringPhantoms.CONFIG.spawnFrequencyRandomOffsetBound.get())) * 20;
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isSpectator()Z"
            )
    )
    private boolean repelIfHoldingRepellentItem(boolean original, @Local(name = "player") ServerPlayer player) {
        if (original || !SoaringPhantoms.CONFIG.phantomsRepelledByItem.get()) return original;
        return SoaringPhantoms.CONFIG.repellentItems.get().contains(BuiltInRegistries.ITEM.getKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem())) ||
				SoaringPhantoms.CONFIG.repellentItems.get().contains(BuiltInRegistries.ITEM.getKey(player.getItemInHand(InteractionHand.OFF_HAND).getItem()));
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 1
            )
    )
    private int modifyCondition(int original, @Local(name = "playerPos") BlockPos playerPos, @Local(name = "random") RandomSource random) {
        if (SoaringPhantoms.CONFIG.doAltitudeBasedSpawning.get()) {
			int y = playerPos.getY();
            return y < 1 ? 0 : random.nextInt(y);
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
    private int modifyConditionCheckValue(int original, @Local(name = "level", argsOnly = true) ServerLevel level) {
        if (SoaringPhantoms.CONFIG.doAltitudeBasedSpawning.get()) {
            if (SoaringPhantoms.CONFIG.passivePhantomsBeforeEnderDragon.get()) {
				EnderDragonFight fight = level.getServer().getDataStorage().get(EnderDragonFight.TYPE);
                if (fight == null || !fight.hasPreviouslyKilledDragon()) {
                    return SoaringPhantoms.CONFIG.passiveSpawnStartHeight.get();
                }
            }
            return SoaringPhantoms.CONFIG.spawnStartHeight.get();
        }
        return original;
    }
}
