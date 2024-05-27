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

    private DBHelper dbHelper;

    private Paint paint;
    private Path path;

    private List<Pair<Path, Integer>> paths = new ArrayList<>();

    private int currentColor = Color.BLACK;
    private float lastX = 0;
    private float lastY = 0;
    private static final float TOUCH_TOLERANCE = 4;





    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dbHelper = new DBHelper(context);
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

                // Convertir le chemin (Path) en une chaîne de données
                String drawingData = pathToString(path);

                // Insérer les données du dessin dans la base de données

                dbHelper.insertDrawing( drawingData);
                path = null;
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
    public void setDrawingData(String drawingData) {
        // Efface les dessins précédents
        paths.clear();

        // Crée un nouveau chemin à partir des données du dessin
        path = stringToPath(drawingData);

        // Ajoute le chemin à la liste de dessins
        if (path != null) {
            paths.add(new Pair<>(new Path(path), currentColor));
        }

        // Force le redessin de la vue
        invalidate();
    }

    private Path stringToPath(String drawingData) {
        Path path = new Path();
        String[] segments = drawingData.split(" "); // Divise la chaîne en segments basés sur les espaces
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            char command = segment.charAt(0); // La première lettre du segment est la commande de tracé
            switch (command) {
                case 'M': // Déplacement absolu
                    String[] coordinates = segment.substring(1).split(","); // Coordonnées x,y sans le M
                    float x = Float.parseFloat(coordinates[0]);
                    float y = Float.parseFloat(coordinates[1]);
                    path.moveTo(x, y);
                    break;
                case 'L': // Ligne absolu
                    String[] lineCoords = segment.substring(1).split(","); // Coordonnées x,y sans le L
                    float endX = Float.parseFloat(lineCoords[0]);
                    float endY = Float.parseFloat(lineCoords[1]);
                    path.lineTo(endX, endY);
                    break;
                // Ajoute d'autres cas pour d'autres commandes de tracé si nécessaire
            }
        }
        return path;
    }



    private String pathToString(Path path) {
        return path.toString();
    }


}

