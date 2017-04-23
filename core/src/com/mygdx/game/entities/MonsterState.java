package com.mygdx.game.entities;

/**
 * Created by Jay Nguyen on 4/14/2017.
 */

public enum MonsterState {
    WALKING,
    JUMPING,
    FALLING;

    public boolean isWalking() {
        return this == WALKING;
    }

    public boolean isJumping() {
        return this == JUMPING;
    }

    public boolean isFalling() {
        return this == FALLING;
    }
}
