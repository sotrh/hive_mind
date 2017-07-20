package com.sotrh.hive_mind.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.debugLog

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

    override fun initialize() {
        super.initialize()
        pm = world.getMapper(PositionComponent::class.java)
    }

    override fun begin() {
        super.begin()
        batch.projectionMatrix = camera.combined
        batch.begin()
    }

    override fun process(entityId: Int) {
        val position = pm[entityId]
        batch.draw(debugTexture, position.x, position.y, 10f, 10f)
    }

    override fun end() {
        super.end()
        batch.end()
    }
}