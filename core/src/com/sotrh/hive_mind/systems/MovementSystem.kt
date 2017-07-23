package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.sotrh.hive_mind.circleCollision
import com.sotrh.hive_mind.components.DeadComponent
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
    private lateinit var dm: ComponentMapper<DeadComponent>

    override fun begin() {
        super.begin()
        Gdx.app.log("MovementSystem", "entities: ${subscription.entities.size()}")
    }

    override fun process(entityId: Int) {
        val positionComponent = pm.get(entityId)
        val velocityComponent = vm.get(entityId)

        positionComponent.x += velocityComponent.vx * world.delta
        positionComponent.y += velocityComponent.vy * world.delta

        val windowWidth = Gdx.graphics.width
        val windowHeight = Gdx.graphics.height
        val centerX = windowWidth / 2f
        val centerY = windowHeight / 2f
        val radius = Math.max(windowWidth * 2f, windowHeight * 2f)
        if (!circleCollision(centerX, centerY, radius, positionComponent.x, positionComponent.y, 0.0f)) {
            if (!dm.has(entityId)) dm.create(entityId)
        }
    }
}