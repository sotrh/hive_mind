package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.components.CollisionComponent
import com.sotrh.hive_mind.components.DeadComponent

/**
 * Created by benjamin on 7/21/17
 */
class CollisionCleanupSystem : IteratingSystem(Aspect.all(CollisionComponent::class.java)) {

    lateinit var deadMapper: ComponentMapper<DeadComponent>

    override fun process(entityId: Int) {
        if (!deadMapper.has(entityId)) deadMapper.create(entityId)
    }
}