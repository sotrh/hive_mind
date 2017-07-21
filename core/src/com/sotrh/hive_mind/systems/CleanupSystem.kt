package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.components.DeadComponent

/**
 * Created by benjamin on 7/20/17
 */
class CleanupSystem : IteratingSystem(Aspect.all(DeadComponent::class.java)) {
    override fun process(entityId: Int) {
        world.delete(entityId)
    }
}