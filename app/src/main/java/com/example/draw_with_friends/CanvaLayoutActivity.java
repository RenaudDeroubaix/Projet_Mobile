package com.example.draw_with_friends;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CanvaLayoutActivity extends AppCompatActivity {

    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva);

        canvasView = findViewById(R.id.canvasView);

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
                // Set the picked color to the canvas view's drawing color
                canvasView.setDrawingColor(color);
            }
        });
        colorPickerDialog.show();
    }
}


