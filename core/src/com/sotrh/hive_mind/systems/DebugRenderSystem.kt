package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sotrh.hive_mind.components.DeadComponent
import com.sotrh.hive_mind.components.FireComponent
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.components.RadiusComponent

/**
 * Created by benjamin on 7/19/17
 */
class DebugRenderSystem(
        val batch: SpriteBatch,
        val camera: OrthographicCamera,
        val debugTexture: Texture
) : IteratingSystem(Aspect.all(
        PositionComponent::class.java
)) {

    private lateinit var pm: ComponentMapper<PositionComponent>
    private lateinit var rm: ComponentMapper<RadiusComponent>
    private lateinit var fm: ComponentMapper<FireComponent>
    private lateinit var dm: ComponentMapper<DeadComponent>

    override fun begin() {
        super.begin()
        batch.projectionMatrix = camera.combined
        batch.begin()
    }

    override fun process(entityId: Int) {
        val pc = pm[entityId]
        val rm = rm[entityId]
        batch.color = if (fm.has(entityId) && !dm.has(entityId)) Color.ORANGE else if (dm.has(entityId)) Color.BROWN else Color.WHITE
        batch.draw(debugTexture, pc.x - rm.radius, pc.y - rm.radius, rm.radius * 2f, rm.radius * 2f)
    }

    override fun end() {
        super.end()
        batch.end()
    }
}