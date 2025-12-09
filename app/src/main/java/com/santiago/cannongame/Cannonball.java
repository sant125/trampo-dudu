package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Cannonball {
    private float posX;
    private float posY;
    private float velocityX;
    private float velocityY;
    private float radius;
    private boolean active;

    private Paint paint;

    private static final float GRAVITY = 300f;

    public Cannonball(float x, float y, float angle, float velocity, float radius) {
        this.posX = x;
        this.posY = y;
        this.velocityX = (float) (Math.cos(angle) * velocity);
        this.velocityY = -(float) (Math.sin(angle) * velocity);
        this.radius = radius;
        this.active = true;

        paint = new Paint();
        paint.setColor(0xFF000000);
        paint.setAntiAlias(true);
    }

    public void update(double elapsedTime) {
        if (active) {
            velocityY += GRAVITY * elapsedTime;
            posX += velocityX * elapsedTime;
            posY += velocityY * elapsedTime;
        }
    }

    public void draw(Canvas canvas) {
        if (active) {
            canvas.drawCircle(posX, posY, radius, paint);
        }
    }

    public boolean isOutOfBounds(int screenWidth, int screenHeight) {
        return posX < 0 || posX > screenWidth ||
               posY < 0 || posY > screenHeight;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public Point getPosition() {
        return new Point((int) posX, (int) posY);
    }

    public float getRadius() {
        return radius;
    }
}
