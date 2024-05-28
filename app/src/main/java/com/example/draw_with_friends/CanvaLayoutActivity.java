package com.example.draw_with_friends;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class CanvaLayoutActivity extends AppCompatActivity {

    private CanvasView canvasView;
    private String drawingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva);

        canvasView = findViewById(R.id.canvasView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("drawingName")) {
            drawingName = intent.getStringExtra("drawingName");
            canvasView.setDrawingName(drawingName);
        }

        Button colorPickerButton = findViewById(R.id.button_color_picker);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog();
            }
        });
    }

    private void showColorPickerDialog() {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this);
        colorPickerDialog.setColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color) {
                canvasView.setDrawingColor(color);
            }
        });
        colorPickerDialog.show();
    }
}
