package net.yeoxuhang.endit.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.yeoxuhang.endit.config.EndPlatformSpawnConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow @Final @Mutable public static BlockPos END_SPAWN_POINT;

    @Inject(method = "tick", at = @At("TAIL"))
    private void endit$updateEndSpawnPoint(CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        END_SPAWN_POINT = EndPlatformSpawnConfig.get().blockPos();

        // Remove Endermen that already existed in saved chunks before this feature was installed.
        // Run once per second rather than on every tick to keep overhead low.
        if (level.dimension() == Level.END && level.getGameTime() % 20L == 0L) {
            level.getEntities(EntityType.ENDERMAN, enderman -> true)
                    .forEach(EnderMan::discard);
        }
    }

    @Inject(method = "addFreshEntity", at = @At("HEAD"), cancellable = true)
    private void endit$blockEndermanInEnd(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (endit$isEndermanInEnd(entity)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tryAddFreshEntityWithPassengers", at = @At("HEAD"), cancellable = true)
    private void endit$blockEndermanWithPassengersInEnd(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (endit$isEndermanInEnd(entity)) {
            cir.setReturnValue(false);
        }
    }

    private boolean endit$isEndermanInEnd(Entity entity) {
        ServerLevel level = (ServerLevel) (Object) this;
        return level.dimension() == Level.END && entity.getType() == EntityType.ENDERMAN;
    }
}
