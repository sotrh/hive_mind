package com.sotrh.hive_mind.systems

import com.artemis.BaseSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.sotrh.hive_mind.Environment

/**
 * Created by benjamin on 7/23/17
 */
class EnvironmentRenderSystem(val environment: Environment, val batch: SpriteBatch, val camera: OrthographicCamera, val debugTexture: Texture) : BaseSystem() {
    companion object {
        val TILE_RENDER_SIZE = 100f
    }

    override fun processSystem() {
        batch.projectionMatrix = camera.combined
        batch.begin()
        drawVisibleTilesFromEnvironment()
        batch.end()
    }

    private fun drawVisibleTilesFromEnvironment() {
        val topVisibleRowIndex = Math.min(MathUtils.ceil((camera.viewportHeight / 2f + camera.position.y) / TILE_RENDER_SIZE), environment.height - 1)
        val leftMostVisibleColumnIndex = Math.max(MathUtils.floor((camera.viewportWidth / 2f - camera.position.x) / TILE_RENDER_SIZE), 0)
        val bottomVisibleRowIndex = Math.max(MathUtils.floor((camera.viewportHeight / 2f - camera.position.y) / TILE_RENDER_SIZE), 0)
        val rightMostVisibleColumnIndex = Math.min(MathUtils.ceil((camera.viewportWidth / 2f + camera.position.x) / TILE_RENDER_SIZE), environment.width - 1)

        (leftMostVisibleColumnIndex..rightMostVisibleColumnIndex).forEach { x ->
            (bottomVisibleRowIndex..topVisibleRowIndex).forEach { y ->
                batch.color = when(environment.getTile(x, y).type) {
                    Environment.TILE_DIRT -> Color.BROWN
                    Environment.TILE_LAVA -> Color.ORANGE
                    Environment.TILE_LAVA_DEEP -> Color.CORAL
                    Environment.TILE_WATER -> Color.CYAN
                    Environment.TILE_WATER_DEEP -> Color.BLUE
                    else -> Color.WHITE
                }
                batch.draw(debugTexture, x * TILE_RENDER_SIZE, y * TILE_RENDER_SIZE, TILE_RENDER_SIZE, TILE_RENDER_SIZE)
            }
        }
    }
}