package us.timinc.mc.cobblemon.fossiltweaks.events

import com.cobblemon.mod.common.block.multiblock.FossilMultiblockStructure

class FossilTimeCalculationEvent(
    val baseRate: Float,
    val machine: FossilMultiblockStructure,
) {
    private val modifiers = mutableListOf<Float>()
    private val modificationFunctions = mutableListOf<(Float, FossilMultiblockStructure) -> Float>()

    fun addModifier(modifier: Float) {
        modifiers.add(modifier)
    }

    fun addModificationFunction(function: (Float, FossilMultiblockStructure) -> Float) {
        modificationFunctions.add(function)
    }

    fun calculate(): Int {
        var result = baseRate
        for (modifier in modifiers) {
            result *= modifier
        }
        for (modificationFunction in modificationFunctions) {
            result = modificationFunction(result, machine)
        }
        return result.toInt()
    }
}