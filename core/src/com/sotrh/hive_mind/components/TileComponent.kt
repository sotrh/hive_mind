package com.sotrh.hive_mind.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import com.sotrh.hive_mind.Environment

/**
 * Created by benjamin on 7/31/17
 */
@PooledWeaver
class TileComponent : Component() {
    var tile: Environment.Tile? = null
}