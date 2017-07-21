package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.sotrh.hive_mind.circleCollision
import com.sotrh.hive_mind.components.FireComponent
import com.sotrh.hive_mind.components.FlammableComponent
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.components.RadiusComponent

/**
 * Created by benjamin on 7/20/17
 * todo: fire spread should take in a collision component, not position and radius
 */
class FireSpreadSystem : BaseEntitySystem(Aspect.all(PositionComponent::class.java, RadiusComponent::class.java).one(FlammableComponent::class.java, FireComponent::class.java)) {

    lateinit var pm: ComponentMapper<PositionComponent>
    lateinit var rm: ComponentMapper<RadiusComponent>
    lateinit var flm: ComponentMapper<FlammableComponent>
    lateinit var fm: ComponentMapper<FireComponent>

    override fun processSystem() {
        val flammables = subscription.entities.data.filter { flm.has(it) }
        val fires = subscription.entities.data.filter { fm.has(it) }

        flammables.forEach { flammable ->
            fires.forEach { fire ->
                val firePc = pm[fire]
                val fireRc = rm[fire]
                val flammablePc = pm[flammable]
                val flammableRc = rm[flammable]
                val flammableFlc = flm[flammable]

                // todo: don't process collisions here
                if (circleCollision(firePc.x, firePc.y, fireRc.radius, flammablePc.x, flammablePc.y, flammableRc.radius)) {
                    if (!fm.has(flammable)) {
                        val flammableFc = fm.create(flammable)
                        flammableFc.fuel = flammableFlc.fuelValue
                        flammableFc.burnRate = 1.0f
                        flm.remove(flammable)
                    }
                }
            }
        }
    }
}