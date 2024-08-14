package us.timinc.mc.cobblemon.fossiltweaks

import com.cobblemon.mod.common.api.events.CobblemonEvents
import net.fabricmc.api.ModInitializer
import us.timinc.mc.cobblemon.fossiltweaks.config.ConfigBuilder
import us.timinc.mc.cobblemon.fossiltweaks.config.MainConfig
import kotlin.random.Random

object FossilTweaks : ModInitializer {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "fossiltweaks"

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var config: MainConfig

    override fun onInitialize() {
        config = ConfigBuilder.load(MainConfig::class.java, MOD_ID);

        CobblemonEvents.FOSSIL_REVIVED.subscribe { evt ->
            val pokemon = evt.pokemon
            val roll = Random.nextInt(config.fossilShinyRate)
            if (roll == 0) {
                pokemon.shiny = true
            }
        }
    }
}