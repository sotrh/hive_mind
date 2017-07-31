package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.sotrh.hive_mind.Environment
import com.sotrh.hive_mind.components.DeadComponent
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.components.TileComponent

/**
 * Created by benjamin on 7/23/17
 */
class EnvironmentUpdateSystem(val environment: Environment) : IteratingSystem(
        Aspect.all(PositionComponent::class.java, TileComponent::class.java).exclude(DeadComponent::class.java)
) {
    lateinit var positionMapper : ComponentMapper<PositionComponent>
    lateinit var tileMapper : ComponentMapper<TileComponent>
    lateinit var deadMapper : ComponentMapper<DeadComponent>

    override fun begin() {
        super.begin()
        environment.process(world.delta)
    }

    override fun process(entityId: Int) {
        positionMapper.get(entityId)?.let { pos ->
            tileMapper.get(entityId)?.let {
                it.tile = environment.getClosestTile(pos.x, pos.y)
                if (it.tile == null)
                    deadMapper.create(entityId)
            }
        }
    }
}