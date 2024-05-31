package com.example.draw_with_friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CanvaLayoutActivity extends AppCompatActivity {

    private CanvasView canvasView;
    private String drawingName;
    private boolean isReadOnly;
    private int plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva);

        canvasView = findViewById(R.id.canvasView);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("drawingName")) {
                drawingName = intent.getStringExtra("drawingName");
                canvasView.setDrawingName(drawingName);
            }

            isReadOnly = intent.getBooleanExtra("isReadOnly", false);
            canvasView.setReadOnly(isReadOnly);

            plan = intent.getIntExtra("plan", 0);

            Button colorPickerButton = findViewById(R.id.button_color_picker);
            if (isReadOnly) {
                colorPickerButton.setVisibility(View.GONE);
            } else {
                colorPickerButton.setVisibility(View.VISIBLE);
                colorPickerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showColorPickerDialog();
                    }
                });
            }
        }
    }

    private void showColorPickerDialog() {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, plan == 1);
        colorPickerDialog.setColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color) {
                canvasView.setDrawingColor(color);
            }

            @Override
            public void onSizePicked(int size) {
                canvasView.setDrawingSize(size);
            }
        });
        colorPickerDialog.show();
    }
}
