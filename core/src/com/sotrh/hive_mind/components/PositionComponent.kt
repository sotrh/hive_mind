package com.sotrh.hive_mind.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * Created by benjamin on 7/19/17
 */
@PooledWeaver
class PositionComponent : Component() {
    var x = 0f
    var y = 0f
}