package us.timinc.mc.cobblemon.fossiltweaks.mixins;

import com.cobblemon.mod.common.block.multiblock.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(FossilMultiblockStructure.class)
public class FossilMultiblockStructureMixin {
    @Shadow(remap = false)
    private int timeRemaining;

    @Inject(method = "startMachine", at = @At("TAIL"), remap = false)
    private void startMachineMixin(World world, CallbackInfo ci) {
        this.timeRemaining = us.timinc.mc.cobblemon.fossiltweaks.FossilTweaks.config.getFossilMachineTicks();
    }
}