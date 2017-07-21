package com.sotrh.hive_mind.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import com.sotrh.hive_mind.systems.FireSpreadSystem
import com.sotrh.hive_mind.systems.FireSystem

/**
 * Created by benjamin on 7/20/17
 */
class EnvironmentPlugin : ArtemisPlugin {
    override fun setup(b: WorldConfigurationBuilder?) {
        b?.with(
                FireSystem(),
                FireSpreadSystem()
        )
    }
}