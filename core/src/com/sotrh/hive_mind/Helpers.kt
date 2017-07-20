package com.sotrh.hive_mind

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Disposable

/**
 * Created by benjamin on 7/19/17
 */

inline fun <T : Disposable> T.use(block: (T) -> Unit) {
    block(this)
    dispose()
}

fun log(tag: String, message: String) {
    Gdx.app?.log(tag, message)
}

fun debugLog(message: String) = log("Hive Mind: debug", message)