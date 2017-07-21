package com.sotrh.hive_mind.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * Created by benjamin on 7/20/17
 */
@PooledWeaver
class FireComponent : Component() {
    var fuel = 0.0f
    var burnRate = 0.0f
}