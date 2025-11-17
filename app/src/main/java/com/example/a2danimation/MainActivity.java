package com.example.a2danimation;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout root = findViewById(R.id.root);
        root.addView(new DrawingView(this));
    }


    static class DrawingView extends View {
        private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Позиция облака
        private float cloudX = 200;
        private float cloudY = 200;

        // Для перетаскивания
        private float lastTouchX, lastTouchY;
        private boolean isCloudDragging = false;

        public DrawingView(Context ctx) {
            super(ctx);
            fill.setStyle(Paint.Style.FILL);
            stroke.setStyle(Paint.Style.STROKE);
            stroke.setColor(Color.BLACK);
            stroke.setStrokeWidth(5f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            drawRect(canvas, 0, 0, getWidth(), 800, Color.CYAN);      // небо
            drawRect(canvas, 0, 800, getWidth(), getHeight(), Color.GREEN);  // трава

            // Солнце
            drawCircle(canvas, 900, 150, 160, Color.rgb(255, 255, 204));
            drawCircle(canvas, 900, 150, 140, Color.rgb(255, 255, 153));
            drawCircle(canvas, 900, 150, 120, Color.rgb(255, 255, 102));

            //ОБЛАКО
            drawCloud(canvas, cloudX, cloudY);

            //ДОМ
            drawRect(canvas, 300, 900, 780, 1400, Color.rgb(255, 216, 216)); // стены
            drawRectStroke(canvas, 300, 900, 780, 1400, Color.BLACK, 5);

            drawTriangle(canvas, 270, 900, 540, 650, 810, 900, Color.RED);
            drawTriangleStroke(canvas, 270, 900, 540, 650, 810, 900, Color.BLACK, 6);

            // Темно-серый прямоугольник дороги
            drawRect(canvas, 200, 12000, getWidth() - 200, 1500, Color.rgb(70,70,70));

            // Окна
            drawRect(canvas, 360, 1000, 480, 1140, Color.rgb(204, 255, 255));
            drawRectStroke(canvas, 360, 1000, 480, 1140, Color.BLACK, 4);
            drawLine(canvas, 360, 1070, 480, 1070, Color.BLACK, 3);
            drawLine(canvas, 420, 1000, 420, 1140, Color.BLACK, 3);

            drawRect(canvas, 600, 1000, 720, 1140, Color.rgb(204, 255, 255));
            drawRectStroke(canvas, 600, 1000, 720, 1140, Color.BLACK, 4);
            drawLine(canvas, 600, 1070, 720, 1070, Color.BLACK, 3);
            drawLine(canvas, 660, 1000, 660, 1140, Color.BLACK, 3);

            // Дверь
            drawRect(canvas, 500, 1140, 580, 1400, Color.rgb(120, 70, 30));
            drawRectStroke(canvas, 500, 1140, 580, 1400, Color.BLACK, 5);

            // Человечек
            int cx = 880;
            int cy = 1320;

            drawCircle(canvas, cx, cy - 120, 50, Color.rgb(255, 220, 180));
            drawCircleStroke(canvas, cx, cy - 120, 50, Color.BLACK, 4);

            drawRect(canvas, cx - 25, cy - 70, cx + 25, cy + 90, Color.BLUE);
            drawRectStroke(canvas, cx - 25, cy - 70, cx + 25, cy + 90, Color.BLACK, 4);

            drawLine(canvas, cx - 60, cy - 50, cx + 60, cy - 50, Color.BLACK, 6);
            drawLine(canvas, cx - 20, cy + 90, cx - 50, cy + 180, Color.BLACK, 6);
            drawLine(canvas, cx + 20, cy + 90, cx + 50, cy + 180, Color.BLACK, 6);
        }

        private void drawRect(Canvas c, int l, int t, int r, int b, int color) {
            fill.setColor(color);
            c.drawRect(l, t, r, b, fill);
        }

        private void drawRectStroke(Canvas c, int l, int t, int r, int b, int color, float sw) {
            stroke.setColor(color);
            stroke.setStrokeWidth(sw);
            c.drawRect(l, t, r, b, stroke);
        }

        private void drawCircle(Canvas c, int cx, int cy, int radius, int color) {
            fill.setColor(color);
            c.drawCircle(cx, cy, radius, fill);
        }

        private void drawCircleStroke(Canvas c, int cx, int cy, int radius, int color, float sw) {
            stroke.setColor(color);
            stroke.setStrokeWidth(sw);
            c.drawCircle(cx, cy, radius, stroke);
        }

        private void drawLine(Canvas c, int x1, int y1, int x2, int y2, int color, float sw) {
            stroke.setColor(color);
            stroke.setStrokeWidth(sw);
            c.drawLine(x1, y1, x2, y2, stroke);
        }

        private void drawTriangle(Canvas c, int x1, int y1, int x2, int y2, int x3, int y3, int color) {
            Path p = new Path();
            p.moveTo(x1, y1);
            p.lineTo(x2, y2);
            p.lineTo(x3, y3);
            p.close();
            fill.setColor(color);
            c.drawPath(p, fill);
        }

        private void drawTriangleStroke(Canvas c, int x1, int y1, int x2, int y2, int x3, int y3, int color, float sw) {
            Path p = new Path();
            p.moveTo(x1, y1);
            p.lineTo(x2, y2);
            p.lineTo(x3, y3);
            p.close();
            stroke.setColor(color);
            stroke.setStrokeWidth(sw);
            c.drawPath(p, stroke);
        }

        // ==== ОБЛАКО ====
        private void drawCloud(Canvas c, float x, float y) {
            fill.setColor(Color.WHITE);
            c.drawCircle(x, y, 60, fill);
            c.drawCircle(x + 70, y + 20, 60, fill);
            c.drawCircle(x - 70, y + 20, 60, fill);
        }

        //ДВИЖЕНИЕ ОБЛАКА
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (Math.hypot(x - cloudX, y - cloudY) < 100) {
                        isCloudDragging = true;
                        lastTouchX = x;
                        lastTouchY = y;
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (isCloudDragging) {
                        cloudX += (x - lastTouchX);
                        cloudY += (y - lastTouchY);
                        lastTouchX = x;
                        lastTouchY = y;
                        invalidate();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    isCloudDragging = false;
                    break;
            }
            return true;
        }
    }
}
