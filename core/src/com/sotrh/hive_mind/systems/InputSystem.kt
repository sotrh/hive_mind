package com.sotrh.hive_mind.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.sotrh.hive_mind.Environment
import com.sotrh.hive_mind.components.*

/**
 * Created by benjamin on 7/19/17
 */
class InputSystem(val camera: OrthographicCamera, val environment: Environment) : BaseSystem(), InputProcessor {

    lateinit var flammableMapper: ComponentMapper<FlammableComponent>
    lateinit var positionMapper: ComponentMapper<PositionComponent>
    lateinit var velocityMapper: ComponentMapper<VelocityComponent>
    lateinit var radiusMapper: ComponentMapper<RadiusComponent>
    lateinit var fireMapper: ComponentMapper<FireComponent>
    lateinit var tileMapper: ComponentMapper<TileComponent>

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
                val pc = positionMapper.create(entityId)
                pc.x = workVector3.x
                pc.y = workVector3.y

                workVector3.set(screenX.toFloat(), screenY.toFloat(), 0f)
                camera.unproject(workVector3)
                val velocity = workVector2.set(workVector3.x, workVector3.y).sub(pc.x, pc.y)

                val vc = velocityMapper.create(entityId)
                vc.vx = velocity.x
                vc.vy = velocity.y

                radiusMapper.create(entityId).radius = 10f
                flammableMapper.create(entityId).fuelValue = 5.0f
                tileMapper.create(entityId).tile = environment.getClosestTile(pc.x, pc.y)

                Gdx.app.debug("InputSytem", "Created entity $entityId")

                true
            }
            Input.Buttons.RIGHT -> {
                val entityId = world.create()

                val fc = fireMapper.create(entityId)
                fc.fuel = 1.0f
                fc.burnRate = 0.0f

                workVector3.set(screenX.toFloat(),  screenY.toFloat(), 0f)
                camera.unproject(workVector3)
                val pc = positionMapper.create(entityId)
                pc.x = workVector3.x
                pc.y = workVector3.y

                radiusMapper.create(entityId).radius = 10f
                tileMapper.create(entityId).tile = environment.getClosestTile(pc.x, pc.y)

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