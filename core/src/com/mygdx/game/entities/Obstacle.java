package com.mygdx.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jga.utils.entity.EntityBase;
import com.mygdx.game.configs.GameConfig;

/**
 * Created by Jay Nguyen on 4/16/2017.
 */

public class Obstacle extends EntityBase implements Pool.Poolable {

    private Rectangle sensor = new Rectangle(); //invisible
    private float angleDegree;
    private float sensorAngleDegree;
    private float radius = GameConfig.PLANET_RADIUS - GameConfig.OBSTACLE_SIZE;

    public Obstacle() {
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    public void update(float delta){

        //obstacle
        if(radius < GameConfig.PLANET_RADIUS) {
            radius += delta;
            float originX = GameConfig.WORLD_CENTER_X;
            float originY = GameConfig.WORLD_CENTER_Y;

            float newX = originX + MathUtils.cosDeg(-angleDegree) * radius;
            float newY = originY + MathUtils.sinDeg(-angleDegree) * radius;

            setPosition(newX, newY);

            //sensor
            float sensorX = originX + MathUtils.cosDeg(-sensorAngleDegree) * radius;
            float sensorY = originY + MathUtils.sinDeg(-sensorAngleDegree) * radius;

            sensor.set(sensorX, sensorY, getWidth(), getHeight());
        }
    }

    public void setAngleDegree(float value) {
        angleDegree = value % 360;
        sensorAngleDegree = angleDegree + 20;
    }

    public Rectangle getSensor() {
        return sensor;
    }

    public float getSensorAngleDegree() {
        return sensorAngleDegree;
    }

    public float getAngleDegree() {

        return angleDegree;
    }

    @Override
    public void reset() {
        //don't need to reset angles because it is resetted when put back into pool
        radius = GameConfig.PLANET_RADIUS - GameConfig.OBSTACLE_SIZE;
    }
}
