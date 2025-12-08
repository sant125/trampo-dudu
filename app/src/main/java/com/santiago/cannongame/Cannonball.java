package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Cannonball {
    private Point position;
    private float velocityX;
    private float velocityY;
    private float radius;
    private boolean active;

    private Paint paint;

    private static final float GRAVITY = 500f; // Pixels por segundo²

    public Cannonball(float x, float y, float angle, float velocity, float radius) {
        this.position = new Point((int) x, (int) y);
        this.velocityX = (float) (Math.cos(angle) * velocity);
        this.velocityY = -(float) (Math.sin(angle) * velocity); // Negativo porque Y cresce para baixo
        this.radius = radius;
        this.active = true;

        // Inicializar Paint
        paint = new Paint();
        paint.setColor(0xFF000000); // Black
        paint.setAntiAlias(true);
    }

    public void update(double elapsedTime) {
        if (active) {
            // Aplicar gravidade
            velocityY += GRAVITY * elapsedTime;

            // Atualizar posição
            position.x += velocityX * elapsedTime;
            position.y += velocityY * elapsedTime;
        }
    }

    public void draw(Canvas canvas) {
        if (active) {
            canvas.drawCircle(position.x, position.y, radius, paint);
        }
    }

    public boolean isOutOfBounds(int screenWidth, int screenHeight) {
        return position.x < 0 || position.x > screenWidth ||
               position.y < 0 || position.y > screenHeight;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public Point getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }
}
