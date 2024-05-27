package com.example.draw_with_friends;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {
    private Paint paint;
    private Path path;

    private List<Pair<Path, Integer>> paths = new ArrayList<>();

    private int currentColor = Color.BLACK;
    private float lastX = 0;
    private float lastY = 0;
    private static final float TOUCH_TOLERANCE = 4;





    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void setDrawingColor(int color) {
        this.currentColor = color;
        paint.setColor(color);
    }
    public int getDrawingColor() {
        return currentColor;
    }




    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Pair<Path, Integer> pair : paths) {
            paint.setColor(pair.second);
            canvas.drawPath(pair.first, paint);
        }
        if (path != null) {
            canvas.drawPath(path, paint);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(touchX, touchY);
                lastX = touchX;
                lastY = touchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(touchX - lastX);
                float dy = Math.abs(touchY - lastY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    path.quadTo(lastX, lastY, (touchX + lastX) / 2, (touchY + lastY) / 2);
                    lastX = touchX;
                    lastY = touchY;
                    // Ajoute le chemin avec la couleur actuelle à la liste
                    paths.add(new Pair<>(new Path(path), currentColor));
                }
                break;
            case MotionEvent.ACTION_UP:
                // Réinitialise le chemin pour le prochain tracé
                path.lineTo(lastX, lastY);
                paths.add(new Pair<>(new Path(path), currentColor));
                path = null;
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

}

