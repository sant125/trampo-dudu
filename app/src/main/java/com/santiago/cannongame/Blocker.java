package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Blocker {
    private Rect bounds;
    private float velocityY;

    private Paint paint;

    public Blocker(int x, int y, int width, int height, float velocityY) {
        this.bounds = new Rect(x, y, x + width, y + height);
        this.velocityY = velocityY;

        // Inicializar Paint
        paint = new Paint();
        paint.setColor(0xFF000000); // Black
        paint.setAntiAlias(true);
    }

    public void update(double elapsedTime) {
        // Mover bloqueador verticalmente
        bounds.offset(0, (int) (velocityY * elapsedTime));
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, paint);
    }

    public boolean checkCollision(Cannonball cannonball) {
        if (cannonball.isActive()) {
            Point ballPos = cannonball.getPosition();
            float radius = cannonball.getRadius();

            // Verificar colisão círculo-retângulo
            if (ballPos.x + radius >= bounds.left && ballPos.x - radius <= bounds.right &&
                ballPos.y + radius >= bounds.top && ballPos.y - radius <= bounds.bottom) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutOfBounds(int screenHeight) {
        return bounds.top > screenHeight || bounds.bottom < 0;
    }

    public Rect getBounds() {
        return bounds;
    }
}
