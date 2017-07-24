package com.sotrh.hive_mind

/**
 * Created by benjamin on 7/23/17
 */
class Environment(val width: Int, val height: Int,
                  private val terrain: List<List<Tile>> = (0..width-1).map { (0..height-1).map { Environment.Tile(Environment.TILE_DIRT) } }) {

    companion object {
        val TILE_DIRT = 0
        val TILE_LAVA = 1
        val TILE_LAVA_DEEP = 2
        val TILE_WATER = 3
        val TILE_WATER_DEEP = 4
    }
    data class Tile(var type: Int)

    fun getTile(x: Int, y: Int): Tile {
        return terrain[y][x]
    }
}