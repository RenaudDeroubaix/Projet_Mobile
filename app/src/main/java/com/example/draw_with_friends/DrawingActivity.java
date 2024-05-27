package com.example.draw_with_friends;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.draw_with_friends.CanvasView;
import com.example.draw_with_friends.DBHelper;

public class DrawingActivity extends AppCompatActivity {

    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva);

        canvasView = findViewById(R.id.canvasView);

        // Récupérer le nom du dessin sélectionné depuis l'intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("drawingName")) {
            String drawingName = intent.getStringExtra("drawingName");

            // Récupérer les données du dessin depuis la base de données en fonction du nom du dessin
            DBHelper dbHelper = new DBHelper(this);
            String drawingData = dbHelper.getDrawingDataByName(drawingName);

            // Afficher le dessin dans CanvasView
            canvasView.setDrawingData(drawingData);
        }
    }
}
