package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Target {
    private Rect bounds;
    private float velocityY;
    private boolean hit;

    private Paint paint;
    private Paint hitPaint;

    public Target(int x, int y, int width, int height, float velocityY) {
        this.bounds = new Rect(x, y, x + width, y + height);
        this.velocityY = velocityY;
        this.hit = false;

        // Inicializar Paint objects
        paint = new Paint();
        paint.setColor(0xFFFF0000); // Red
        paint.setAntiAlias(true);

        hitPaint = new Paint();
        hitPaint.setColor(0xFFFF6347); // Tomato (red lighter)
        hitPaint.setAntiAlias(true);
        hitPaint.setAlpha(150);
    }

    public void update(double elapsedTime) {
        if (!hit) {
            // Mover alvo verticalmente
            bounds.offset(0, (int) (velocityY * elapsedTime));
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, hit ? hitPaint : paint);
    }

    public boolean checkCollision(Cannonball cannonball) {
        if (!hit && cannonball.isActive()) {
            Point ballPos = cannonball.getPosition();
            float radius = cannonball.getRadius();

            // Verificar colisão círculo-retângulo
            if (ballPos.x + radius >= bounds.left && ballPos.x - radius <= bounds.right &&
                ballPos.y + radius >= bounds.top && ballPos.y - radius <= bounds.bottom) {
                hit = true;
                return true;
            }
        }
        return false;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isOutOfBounds(int screenHeight) {
        return bounds.top > screenHeight || bounds.bottom < 0;
    }

    public Rect getBounds() {
        return bounds;
    }
}
