package com.jga.utils.entity;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Jay Nguyen on 4/4/2017.
 */

public abstract class EntityBase {

    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected Rectangle bounds;

    // == Constructor ==
    public EntityBase() {
        bounds = new Rectangle(x, y, width, height); //at 0x, 0y, 1W, 1H
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    //whenever bounds are updated, it automatically update position and size
    public void updateBounds() {
        bounds.setPosition(x, y);
        bounds.setSize(width, height);
    }
}
