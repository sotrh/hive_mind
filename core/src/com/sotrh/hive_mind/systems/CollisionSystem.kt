package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.sotrh.hive_mind.Environment
import com.sotrh.hive_mind.circleCollision
import com.sotrh.hive_mind.components.*

/**
 * Created by benjamin on 7/21/17
 */
class CollisionSystem(val environment: Environment) : BaseEntitySystem(
        Aspect.all(PositionComponent::class.java, RadiusComponent::class.java, TileComponent::class.java).exclude(DeadComponent::class.java)
) {

    private lateinit var positionMapper: ComponentMapper<PositionComponent>
    private lateinit var radiusMapper: ComponentMapper<RadiusComponent>
    private lateinit var collisionMapper: ComponentMapper<CollisionComponent>
    private lateinit var tileMapper: ComponentMapper<TileComponent>

    override fun processSystem() {
        val entities = subscription.entities.data

        (0..entities.size - 1).forEach { entityA ->
            if (positionMapper.has(entityA) && radiusMapper.has(entityA)) {
                val pca = positionMapper[entities[entityA]]
                val rca = radiusMapper[entities[entityA]]
                (entityA + 1..entities.size - 1).forEach { entityB ->
                    if (positionMapper.has(entityB) && radiusMapper.has(entityB)) {
                        val rcb = radiusMapper[entities[entityB]]
                        val pcb = positionMapper[entities[entityB]]

                        if (areEntitiesInTheSameTile(entityA, entityB) &&
                                circleCollision(pca.x, pca.y, rca.radius, pcb.x, pcb.y, rcb.radius)) {
                            val entityId = world.create()
                            val collision = collisionMapper.create(entityId)
                            collision.entityA = entities[entityA]
                            collision.entityB = entities[entityB]
                        }
                    }
                }
            }
        }
    }

    private fun areEntitiesInTheSameTile(entityA: Int, entityB: Int): Boolean {
        val tileA = tileMapper.get(entityA)?.tile
        val tileB = tileMapper.get(entityB)?.tile

        return tileA != null && tileB != null && tileA == tileB
    }
}