package com.mygdx.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.utils.entity.EntityBase;
import com.mygdx.game.configs.GameConfig;

/**
 * Created by Jay Nguyen on 4/15/2017.
 */

public class Coin extends EntityBase implements Pool.Poolable {

    private boolean offSet;
    private float angleDegree;
    private float scale;

    //==constructor==
    public Coin() {
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE);
    }

    public void update(float delta) {
        if (scale < GameConfig.COIN_SIZE) {
            scale += delta;
        }
    }

    public void setAngleDegree(float value) {
        angleDegree = value % 360;

        float radius = GameConfig.PLANET_RADIUS;

        if (offSet) {
            radius += GameConfig.COIN_SIZE;
        }

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegree) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegree) * radius;

        setPosition(newX, newY);
    }

    public float getAngleDegree() {
        return angleDegree;
    }

    public void setOffSet(boolean offSet) {
        this.offSet = offSet;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public void reset() {
        //dont need to reset because it is always resetted when put back into pool
        offSet = false;
        scale = 0;
    }
}
