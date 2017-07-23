package com.sotrh.hive_mind.systems

import com.artemis.ArchetypeBuilder
import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.circleCollision
import com.sotrh.hive_mind.components.CollisionComponent
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.components.RadiusComponent
import com.sotrh.hive_mind.components.VelocityComponent

/**
 * Created by benjamin on 7/21/17
 */
class CollisionSystem : BaseEntitySystem(Aspect.all(PositionComponent::class.java, RadiusComponent::class.java)) {

    private lateinit var pm: ComponentMapper<PositionComponent>
    private lateinit var rm: ComponentMapper<RadiusComponent>
    private lateinit var cm: ComponentMapper<CollisionComponent>

    override fun processSystem() {
        val entities = subscription.entities.data

        (0..entities.size-1).forEach { a ->
            if (pm.has(a) && rm.has(a)) {
                val pca = pm[entities[a]]
                val rca = rm[entities[a]]
                (a+1..entities.size-1).forEach { b ->
                    if (pm.has(b) && rm.has(b)) {
                        val rcb = rm[entities[b]]
                        val pcb = pm[entities[b]]
                        if (circleCollision(pca.x, pca.y, rca.radius, pcb.x, pcb.y, rcb.radius)) {
                            val entityId = world.create()
                            val collision = cm.create(entityId)
                            collision.entityA = entities[a]
                            collision.entityB = entities[b]
                        }
                    }
                }
            }
        }
    }
}