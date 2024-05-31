package com.example.draw_with_friends;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
        System.out.println("String to Path: " + x);
        System.out.println("String to Path: " + y);

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
            Path path = paths.get(i);
            int color = colors.get(i);

            PathMeasure pm = new PathMeasure(path, false);
            float[] pos = new float[2];
            boolean firstPoint = true;

            while (pm.getLength() > 0) {
                for (float distance = 0; distance < pm.getLength(); distance += 10) {
                    pm.getPosTan(distance, pos, null);
                    if (firstPoint) {
                        stringBuilder.append(pos[0]).append(",").append(pos[1]);
                        firstPoint = false;
                    } else {
                        stringBuilder.append(" ").append(pos[0]).append(",").append(pos[1]);
                    }
                }
                if (!pm.nextContour()) {
                    break;
                }
            }
            stringBuilder.append(":").append(color).append(";");
        }

        String drawingData = stringBuilder.toString();
        System.out.println("Path to String: " + drawingData);
        return drawingData;
    }


    public void setDrawingData(String drawingData) {
        System.out.println("String to Path: " + drawingData); // Log the string to path conversion
        paths.clear();
        colors.clear();

        String[] pathSegments = drawingData.split(";");
        for (String segment : pathSegments) {
            if (segment.trim().isEmpty()) {
                continue;
            }

            String[] pathAndColor = segment.split(":");
            String[] points = pathAndColor[0].trim().split(" ");
            int color = Integer.parseInt(pathAndColor[1]);

            Path path = new Path();
            for (int i = 0; i < points.length; i++) {
                String[] coords = points[i].split(",");
                float x = Float.parseFloat(coords[0]);
                float y = Float.parseFloat(coords[1]);
                System.out.println("String to Path: " + x + "," + y);
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }

            paths.add(path);
            colors.add(color);
        }

        // Force redraw the view
        invalidate();
    }




    private Path stringToPath(String drawingData) {
        Path path = new Path();
        String[] segments = drawingData.split(" ");
        for (int i = 0; i < segments.length; i++) {
            String[] coords = segments[i].split(",");
            float x = Float.parseFloat(coords[0]);
            float y = Float.parseFloat(coords[1]);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        return path;
    }
}
