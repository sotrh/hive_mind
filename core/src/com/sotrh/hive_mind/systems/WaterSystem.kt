package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.components.FireComponent
import com.sotrh.hive_mind.components.PositionComponent

/**
 * Created by benjamin on 7/23/17
 */
class WaterSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, FireComponent::class.java)) {
    override fun process(entityId: Int) {

    }
}