package me.pajic.soaring_phantoms.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.soaring_phantoms.Main;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Phantom.PhantomAttackPlayerTargetGoal.class)
public class PhantomAttackPlayerTargetGoalMixin {

    @Shadow @Final Phantom field_7319;

    @WrapMethod(method = "canUse")
    private boolean noAttackIfEnderDragonNotKilled(Operation<Boolean> original) {
        if (Main.CONFIG.passivePhantomsBeforeEnderDragon.get()) {
            Phantom phantom = field_7319;
            Level level = phantom.level();
            if (Main.CONFIG.alwaysAggressiveInEnd.get() && level.dimension() == Level.END) {
                return original.call();
            }
            if (!(phantom.getLastAttacker() instanceof Player) && !level.isClientSide()) {
                if (!((ServerLevel) level).getServer().getWorldData().endDragonFightData().previouslyKilled()) {
                    return false;
                }
            }
        }
        return original.call();
    }
}
