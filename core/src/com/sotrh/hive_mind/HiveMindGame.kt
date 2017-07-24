package com.sotrh.hive_mind

import com.artemis.*
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sotrh.hive_mind.plugins.CleanupPlugin
import com.sotrh.hive_mind.plugins.EnvironmentPlugin
import com.sotrh.hive_mind.systems.CollisionSystem
import com.sotrh.hive_mind.systems.DebugRenderSystem
import com.sotrh.hive_mind.systems.EnvironmentRenderSystem
import com.sotrh.hive_mind.systems.InputSystem

class HiveMindGame : Game() {
    private lateinit var debugTexture: Texture
    private lateinit var debugFont: BitmapFont
    private lateinit var batch: SpriteBatch
    private lateinit var camera: OrthographicCamera
    private lateinit var enviroment: Environment

    private lateinit var world: World

    override fun create() {
        camera = OrthographicCamera()
        batch = SpriteBatch()

        Pixmap(1, 1, Pixmap.Format.RGBA4444).use {
            it.setColor(1f, 1f, 1f, 1f)
            it.fill()
            debugTexture = Texture(it)
        }

        debugFont = BitmapFont()

        enviroment = Environment(128, 128)
        enviroment.getTile(1, 1).type = Environment.TILE_LAVA

        world = World(WorldConfigurationBuilder()
                .with(
                        InputSystem(camera),
                        CollisionSystem()
                )
                .with(
                        EnvironmentPlugin(),
                        CleanupPlugin()
                )
                .with(
                        EnvironmentRenderSystem(enviroment, batch, camera, debugTexture),
                        DebugRenderSystem(batch, camera, debugTexture, debugFont)
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
        val deltaTime = Gdx.graphics.deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += 100f * deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= 100f * deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= 100f * deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += 100f * deltaTime

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        world.delta = Gdx.graphics.deltaTime
        world.process()
    }

    override fun dispose() {
        batch.dispose()
        world.dispose()
        debugFont.dispose()
        debugTexture.dispose()
    }
}
