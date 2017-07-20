package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.components.VelocityComponent

/**
 * Created by benjamin on 7/19/17
 */
class MovementSystem : IteratingSystem(Aspect.all(
        PositionComponent::class.java,
        VelocityComponent::class.java
)) {

    private lateinit var pm: ComponentMapper<PositionComponent>
    private lateinit var vm: ComponentMapper<VelocityComponent>

    override fun initialize() {
        super.initialize()
        pm = world.getMapper(PositionComponent::class.java)
        vm = world.getMapper(VelocityComponent::class.java)
    }

    override fun process(entityId: Int) {
        val positionComponent = pm.get(entityId)
        val velocityComponent = vm.get(entityId)

        positionComponent.x += velocityComponent.vx * world.delta
        positionComponent.y += velocityComponent.vy * world.delta
    }
}