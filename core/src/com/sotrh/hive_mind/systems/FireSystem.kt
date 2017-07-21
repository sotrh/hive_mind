package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.components.DeadComponent
import com.sotrh.hive_mind.components.FireComponent

/**
 * Created by benjamin on 7/20/17
 */
class FireSystem : IteratingSystem(Aspect.all(
        FireComponent::class.java
)) {

    lateinit var fm : ComponentMapper<FireComponent>
    lateinit var dm : ComponentMapper<DeadComponent>

    override fun initialize() {
        super.initialize()
        fm = world.getMapper(FireComponent::class.java)
    }

    override fun process(entityId: Int) {
        val fc = fm.get(entityId)
        fc.fuel -= fc.burnRate * world.delta

        if (fc.fuel <= 0.0f) {
            fm.remove(entityId)
            if (!dm.has(entityId)) dm.create(entityId)
        }
    }
}