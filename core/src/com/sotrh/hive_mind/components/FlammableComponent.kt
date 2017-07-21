package com.sotrh.hive_mind.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * Created by benjamin on 7/20/17
 */
@PooledWeaver
class FlammableComponent : Component() {
    var fuelValue = 0.0f
}