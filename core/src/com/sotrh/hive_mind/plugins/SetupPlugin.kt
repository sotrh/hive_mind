package com.sotrh.hive_mind.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import com.sotrh.hive_mind.systems.CollisionSystem
import com.sotrh.hive_mind.systems.InputSystem

/**
 * Created by benjamin on 7/21/17
 */
class SetupPlugin : ArtemisPlugin {
    override fun setup(b: WorldConfigurationBuilder?) {
        b?.with(
                InputSystem(),
                CollisionSystem()
        )
    }
}