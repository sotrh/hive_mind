package com.sotrh.hive_mind.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * Created by benjamin on 7/19/17
 */
@PooledWeaver
class VelocityComponent : Component() {
    var vx = 0f
    var vy = 0f
}