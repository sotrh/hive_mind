package com.sotrh.hive_mind

import com.badlogic.gdx.math.MathUtils

/**
 * Created by benjamin on 7/23/17
 */
class Environment(val width: Int, val height: Int) {

    private val terrain: List<List<Tile>> = (0..width - 1).map { x ->
        (0..height - 1).map { y ->
            Environment.Tile(Environment.TILE_DIRT, "$x,$y")
        }
    }

    companion object {
        val TILE_DIRT = 0
        val TILE_LAVA = 1
        val TILE_LAVA_DEEP = 2
        val TILE_WATER = 3
        val TILE_WATER_DEEP = 4
        val TILE_RENDER_SIZE = 100f
    }

    data class Tile(var type: Int, val tag: String)

    fun getTile(x: Int, y: Int): Tile? {
        if (x < 0 || y < 0 || x >= width || y >= height) return null
        return terrain[y][x]
    }

    fun getClosestTile(x: Float, y: Float): Tile? {
        return getTile(MathUtils.floor(x / TILE_RENDER_SIZE), MathUtils.floor(y / TILE_RENDER_SIZE))
    }

    fun process(delta: Float) {

    }
}