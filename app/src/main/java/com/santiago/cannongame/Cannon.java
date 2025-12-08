package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Cannon {
    private Point position;
    private float baseRadius;
    private float barrelLength;
    private float barrelWidth;
    private double angle; // ângulo em radianos

    private Paint basePaint;
    private Paint barrelPaint;

    public Cannon(int x, int y, float baseRadius) {
        this.position = new Point(x, y);
        this.baseRadius = baseRadius;
        this.barrelLength = baseRadius * 2.5f;
        this.barrelWidth = baseRadius * 0.6f;
        this.angle = Math.PI / 4; // 45 graus inicial

        // Inicializar Paint objects
        basePaint = new Paint();
        basePaint.setColor(0xFF8B4513); // Brown
        basePaint.setAntiAlias(true);

        barrelPaint = new Paint();
        barrelPaint.setColor(0xFFA0522D); // Lighter brown
        barrelPaint.setAntiAlias(true);
        barrelPaint.setStrokeWidth(barrelWidth);
        barrelPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void draw(Canvas canvas) {
        // Desenhar base do canhão (círculo)
        canvas.drawCircle(position.x, position.y, baseRadius, basePaint);

        // Calcular ponto final do cano
        float endX = position.x + (float) (Math.cos(angle) * barrelLength);
        float endY = position.y - (float) (Math.sin(angle) * barrelLength);

        // Desenhar cano do canhão (linha)
        canvas.drawLine(position.x, position.y, endX, endY, barrelPaint);
    }

    public void setAngle(double angle) {
        // Limitar ângulo entre 0 e 90 graus
        if (angle < 0) angle = 0;
        if (angle > Math.PI / 2) angle = Math.PI / 2;
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public Point getPosition() {
        return position;
    }

    public float getBarrelLength() {
        return barrelLength;
    }
}
