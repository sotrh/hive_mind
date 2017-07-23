package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.sotrh.hive_mind.components.*

/**
 * Created by benjamin on 7/20/17
 */
class FireSpreadSystem : IteratingSystem(Aspect.all(CollisionComponent::class.java)) {

    lateinit var collisionMapper: ComponentMapper<CollisionComponent>
    lateinit var flammableMapper: ComponentMapper<FlammableComponent>
    lateinit var fireMapper: ComponentMapper<FireComponent>

    override fun begin() {
        super.begin()
        Gdx.app.log("FireSpreadSystem", "entities: ${subscription.entities.size()}")
    }

    override fun process(entityId: Int) {
        val collision = collisionMapper[entityId]
        if (fireMapper.has(collision.entityA) && flammableMapper.has(collision.entityB)) {
            spreadFire(collision.entityB)
        } else if (fireMapper.has(collision.entityB) && flammableMapper.has(collision.entityA)) {
            spreadFire(collision.entityA)
        }
    }

    private fun spreadFire(flammableEntity: Int) {
        if (!fireMapper.has(flammableEntity) && flammableMapper.has(flammableEntity)) {
            val flammable = flammableMapper[flammableEntity]
            val fire = fireMapper.create(flammableEntity)
            fire.fuel = flammable.fuelValue
            fire.burnRate = 1.0f
            flammableMapper.remove(flammableEntity)
        }
    }
}