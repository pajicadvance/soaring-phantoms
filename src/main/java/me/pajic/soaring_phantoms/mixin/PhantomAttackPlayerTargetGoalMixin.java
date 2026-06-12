package me.pajic.soaring_phantoms.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.soaring_phantoms.SoaringPhantoms;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Phantom.PhantomAttackPlayerTargetGoal.class)
public class PhantomAttackPlayerTargetGoalMixin {

	@Shadow @Final Phantom this$0;

	@SuppressWarnings("resource")
	@WrapMethod(method = "canUse")
    private boolean noAttackIfEnderDragonNotKilled(Operation<Boolean> original) {
        if (SoaringPhantoms.CONFIG.passivePhantomsBeforeEnderDragon.get()) {
            Level level = this$0.level();
            if (SoaringPhantoms.CONFIG.alwaysAggressiveInEnd.get() && level.dimension() == Level.END) {
                return original.call();
            }
            if (!(this$0.getLastAttacker() instanceof Player) && !level.isClientSide()) {
				EnderDragonFight fight = ((ServerLevel) level).getServer().getDataStorage().get(EnderDragonFight.TYPE);
                if (fight == null || !fight.hasPreviouslyKilledDragon()) return false;
            }
        }
        return original.call();
    }
}
