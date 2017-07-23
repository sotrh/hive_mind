package com.sotrh.hive_mind.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import com.sotrh.hive_mind.systems.CleanupSystem
import com.sotrh.hive_mind.systems.CollisionCleanupSystem
import com.sotrh.hive_mind.systems.MovementSystem

/**
 * Created by benjamin on 7/21/17
 */
class CleanupPlugin : ArtemisPlugin {
    override fun setup(b: WorldConfigurationBuilder?) {
        b?.with(
                MovementSystem(), // todo: find a home for this
                CollisionCleanupSystem(),
                CleanupSystem()
        )
    }
}