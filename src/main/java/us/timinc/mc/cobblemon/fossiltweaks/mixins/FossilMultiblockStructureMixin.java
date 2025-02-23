package us.timinc.mc.cobblemon.fossiltweaks.mixins;

import com.cobblemon.mod.common.block.multiblock.FossilMultiblockStructure;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.timinc.mc.cobblemon.fossiltweaks.FossilTweaks;

@Mixin(FossilMultiblockStructure.class)
public class FossilMultiblockStructureMixin {
    @Shadow
    private int timeRemaining;

    @Inject(method = "startMachine", at = @At("TAIL"), remap = false)
    private void startMachineMixin(World world, CallbackInfo ci) {
        this.timeRemaining = us.timinc.mc.cobblemon.fossiltweaks.FossilTweaks.config.getFossilMachineTicks();
    }
}