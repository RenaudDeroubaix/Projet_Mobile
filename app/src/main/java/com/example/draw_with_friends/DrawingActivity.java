package com.example.draw_with_friends;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DrawingActivity extends AppCompatActivity {

    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva);

        canvasView = findViewById(R.id.canvasView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("drawingName")) {
            String drawingName = intent.getStringExtra("drawingName");

            DBHelper dbHelper = new DBHelper(this);
            List<String> drawingDataList = dbHelper.getDrawingDataByName(drawingName);

            if (drawingDataList != null && !drawingDataList.isEmpty()) {
                // Assuming you want to show all drawings with the same name, append them to canvasView
                for (String drawingData : drawingDataList) {
                    canvasView.setDrawingData(drawingData);
                }
            }
        }
    }
}


