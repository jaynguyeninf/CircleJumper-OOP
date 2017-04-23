package com.mygdx.game.entities;

import com.badlogic.gdx.math.Circle;
import com.mygdx.game.configs.GameConfig;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class Planet {

    private Circle bounds;

    private float x;
    private float y;
    private float width = 1;
    private float height = 1;

    public Planet() {
        bounds = new Circle(x, y, GameConfig.PLANET_RADIUS);
        setSize(GameConfig.PLANET_DIMENSION, GameConfig.PLANET_DIMENSION);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }


    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public void setWidth(float width) {
        this.width = width;
        updateBounds();
    }

    public void setHeight(float height) {
        this.height = height;
        updateBounds();
    }

    private void updateBounds() {
        float halfWidth = getWidth() / 2;
        float halfHeight = getHeight() / 2;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
