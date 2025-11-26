package me.pajic.soaring_phantoms.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.soaring_phantoms.SoaringPhantoms;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Phantom.PhantomAttackPlayerTargetGoal.class)
public class PhantomAttackPlayerTargetGoalMixin {

	//? if fabric
    @Shadow @Final Phantom field_7319;
	//? if neoforge
	//@Shadow @Final Phantom this$0;

    @SuppressWarnings("resource")
	@WrapMethod(method = "canUse")
    private boolean noAttackIfEnderDragonNotKilled(Operation<Boolean> original) {
        if (SoaringPhantoms.CONFIG.passivePhantomsBeforeEnderDragon.get()) {
            Phantom phantom = /*? if fabric {*/field_7319/*?} else {*//*this$0*//*?}*/;
            Level level = phantom.level();
            if (SoaringPhantoms.CONFIG.alwaysAggressiveInEnd.get() && level.dimension() == Level.END) {
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
