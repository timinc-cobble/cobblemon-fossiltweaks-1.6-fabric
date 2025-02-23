package us.timinc.mc.cobblemon.fossiltweaks.events

import com.cobblemon.mod.common.api.reactive.EventObservable

object FossilTweaksEvents {
    @JvmField
    val FOSSIL_TIME_CALCULATION = EventObservable<FossilTimeCalculationEvent>()
}