package com.sotrh.hive_mind.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.sotrh.hive_mind.components.*

/**
 * Created by benjamin on 7/19/17
 */
class InputSystem(val camera: OrthographicCamera) : BaseSystem(), InputProcessor {

    lateinit var pm: ComponentMapper<PositionComponent>
    lateinit var vm: ComponentMapper<VelocityComponent>
    lateinit var rm: ComponentMapper<RadiusComponent>
    lateinit var fm: ComponentMapper<FireComponent>
    lateinit var flm: ComponentMapper<FlammableComponent>

    private val firstTouch = Vector2()
    private val workVector2 = Vector2()
    private val workVector3 = Vector3()

    override fun initialize() {
        super.initialize()
        Gdx.input.inputProcessor = this
    }

    override fun processSystem() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
            return
        }
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return when (button) {
            Input.Buttons.LEFT -> {
                val entityId = world.create()

                workVector3.set(firstTouch.x, firstTouch.y, 0f)
                camera.unproject(workVector3)
                val pc = pm.create(entityId)
                pc.x = workVector3.x
                pc.y = workVector3.y

                workVector3.set(screenX.toFloat(), screenY.toFloat(), 0f)
                camera.unproject(workVector3)
                val velocity = workVector2.set(workVector3.x, workVector3.y).sub(pc.x, pc.y)

                val vc = vm.create(entityId)
                vc.vx = velocity.x
                vc.vy = velocity.y

                rm.create(entityId).radius = 10f
                flm.create(entityId).fuelValue = 5.0f

                true
            }
            Input.Buttons.RIGHT -> {
                val entityId = world.create()

                val fc = fm.create(entityId)
                fc.fuel = 1.0f
                fc.burnRate = 0.0f

                workVector3.set(screenX.toFloat(),  screenY.toFloat(), 0f)
                camera.unproject(workVector3)
                val pc = pm.create(entityId)
                pc.x = workVector3.x
                pc.y = workVector3.y

                rm.create(entityId).radius = 10f

                true
            }
            else -> false
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        firstTouch.set(screenX.toFloat(), screenY.toFloat())
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }
}