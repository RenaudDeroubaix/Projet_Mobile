package com.example.draw_with_friends;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {

    private Paint paint;
    private Path path;
    private List<Path> paths;
    private List<Integer> colors;
    private int currentColor;
    private String drawingName;
    private DBHelper dbHelper;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();
        paths = new ArrayList<>();
        colors = new ArrayList<>();
        currentColor = paint.getColor();

        dbHelper = new DBHelper(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            paint.setColor(colors.get(i));
            canvas.drawPath(paths.get(i), paint);
        }
        paint.setColor(currentColor);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                paths.add(new Path(path));
                colors.add(currentColor);
                path.reset();
                saveCurrentDrawing();
                invalidate();
                break;
            default:
                return false;
        }
        return true;
    }

    public void setDrawingName(String drawingName) {
        this.drawingName = drawingName;
    }

    public void setDrawingColor(int color) {
        currentColor = color;
    }

    private void saveCurrentDrawing() {
        if (drawingName != null && !drawingName.isEmpty()) {
            String drawingData = getDrawingData();
            dbHelper.insertDrawing(drawingName, drawingData);
        }
    }

    public String getDrawingData() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paths.size(); i++) {
            stringBuilder.append(paths.get(i).toString()).append(",");
            stringBuilder.append(colors.get(i)).append(";");
        }
        return stringBuilder.toString();
    }

    public void setDrawingData(String drawingData) {
        paths.clear();
        colors.clear();
        String[] data = drawingData.split(";");
        for (String d : data) {
            String[] parts = d.split(",");
            Path p = new Path();
            // Convert parts[0] back to a Path object (this is just a placeholder)
            paths.add(p);
            colors.add(Integer.parseInt(parts[1]));
        }
        invalidate();
    }
}
