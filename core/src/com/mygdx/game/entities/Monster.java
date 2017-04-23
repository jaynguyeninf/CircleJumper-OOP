package com.mygdx.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.jga.utils.entity.EntityBase;
import com.mygdx.game.configs.GameConfig;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class Monster extends EntityBase {

    private MonsterState state = MonsterState.WALKING; //default state

    private float angleDegree = GameConfig.START_ANGLE; //start angle
    private float angleDegreeSpeed = GameConfig.MONSTER_START_ANGLER_SPEED; //walking speed (angular)
    private float jumpSpeed = 0;
    private float acceleration = GameConfig.MONSTER_START_ACCELERATION;

    // == constructor ==
    public Monster() {
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE);
    }

    //move monster around circle

    public void update(float delta) {

        if (state.isJumping()) {
            jumpSpeed += acceleration * delta;
            if (jumpSpeed >= GameConfig.MONSTER_MAX_SPEED) {
                fall();
            }
        } else if (state.isFalling()) {
            jumpSpeed -= acceleration * delta;
            if (jumpSpeed <= 0) {
                jumpSpeed = 0;
                walk();
            }
        }

        angleDegree += angleDegreeSpeed * delta;
        angleDegree = angleDegree % 360;

        float radius = GameConfig.PLANET_RADIUS + jumpSpeed;
        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegree) * radius; // makes monster far away from radius which is on top of planet
        float newY = originY + MathUtils.sinDeg(-angleDegree) * radius;

        setPosition(newX, newY);
    }

    public void reset(){
        angleDegree = GameConfig.START_ANGLE;
    }

    public float getAngleDegree() {
        return angleDegree;
    }

    public boolean isWalking(){
        return state.isWalking();
    }

    private void walk() {
        state = MonsterState.WALKING;
    }

    public void jump() {
        state = MonsterState.JUMPING;
    }

    private void fall() {
        state = MonsterState.FALLING;
    }
}
