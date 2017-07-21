package com.sotrh.hive_mind

import com.artemis.*
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sotrh.hive_mind.components.PositionComponent
import com.sotrh.hive_mind.components.VelocityComponent
import com.sotrh.hive_mind.plugins.EnvironmentPlugin
import com.sotrh.hive_mind.systems.DebugRenderSystem
import com.sotrh.hive_mind.systems.InputSystem
import com.sotrh.hive_mind.systems.MovementSystem

class HiveMindGame : Game() {
    private lateinit var debugTexture: Texture
    private lateinit var batch: SpriteBatch
    private lateinit var camera: OrthographicCamera

    private lateinit var world: World

    override fun create() {
        camera = OrthographicCamera()
        batch = SpriteBatch()

        Pixmap(1, 1, Pixmap.Format.RGBA4444).use {
            it.setColor(1f, 1f, 1f, 1f)
            it.fill()
            debugTexture = Texture(it)
        }

        world = World(WorldConfigurationBuilder()
                .with(
                        EnvironmentPlugin()
                )
                .with(
                        InputSystem(),
                        MovementSystem(),
                        DebugRenderSystem(batch, camera, debugTexture)
                )
                .build())
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        camera.position.set(width / 2.0f, height / 2.0f, 0f)
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        world.delta = Gdx.graphics.deltaTime
        world.process()
    }

    override fun dispose() {
        batch.dispose()
        world.dispose()
        debugTexture.dispose()
    }
}
