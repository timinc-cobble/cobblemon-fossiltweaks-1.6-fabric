package us.timinc.mc.cobblemon.fossiltweaks

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.ShinyChanceCalculationEvent
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.block.multiblock.FossilMultiblockStructure
import net.fabricmc.api.ModInitializer
import us.timinc.mc.cobblemon.fossiltweaks.config.ConfigBuilder
import us.timinc.mc.cobblemon.fossiltweaks.config.MainConfig
import us.timinc.mc.cobblemon.fossiltweaks.events.FossilTimeCalculationEvent
import us.timinc.mc.cobblemon.fossiltweaks.events.FossilTweaksEvents
import kotlin.random.Random

object FossilTweaks : ModInitializer {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "fossiltweaks"

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var config: MainConfig

    override fun onInitialize() {
        config = ConfigBuilder.load(MainConfig::class.java, MOD_ID)

        CobblemonEvents.FOSSIL_REVIVED.subscribe { evt ->
            val pokemon = evt.pokemon
            val player = evt.player

            pokemon.persistentData.putBoolean("ft_isFossil", true)

            var shinyRate = config.fossilShinyRate
            CobblemonEvents.SHINY_CHANCE_CALCULATION.post(ShinyChanceCalculationEvent(shinyRate, pokemon)) { event ->
                shinyRate = event.calculate(player)
            }

            pokemon.shiny = shinyRate.checkRate()
        }

        FossilTweaksEvents.FOSSIL_TIME_CALCULATION.subscribe { evt ->
            evt.addModificationFunction { time, machine ->
                val product = machine.resultingFossil?.result
                    ?: throw Error("Why are we calculating fossil time without a fossil result?")
                val pokemon = product.create()
                for ((props, rate) in config.granularRates) {
                    if (PokemonProperties.parse(props).matches(pokemon)) {
                        return@addModificationFunction rate.toFloat()
                    }
                }
                return@addModificationFunction time
            }
        }
    }

    fun calculateTimeToTake(machine: FossilMultiblockStructure): Int {
        var time = config.fossilMachineTicks
        FossilTweaksEvents.FOSSIL_TIME_CALCULATION.post(FossilTimeCalculationEvent(time.toFloat(), machine)) { event ->
            time = event.calculate()
        }
        return time
    }

    private fun Float.checkRate(): Boolean =
        if (this >= 1) (Random.Default.nextFloat() < 1 / this) else Random.Default.nextFloat() < this
}