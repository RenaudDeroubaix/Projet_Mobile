package com.example.draw_with_friends;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

public class ColorPickerDialog extends Dialog {

    private OnColorPickedListener colorPickedListener;
    private boolean allowSizeChange;

    public interface OnColorPickedListener {
        void onColorPicked(int color);
        void onSizePicked(int size);
    }

    public ColorPickerDialog(@NonNull Context context, boolean allowSizeChange) {
        super(context);
        this.allowSizeChange = allowSizeChange;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color_picker);

        final View colorView = findViewById(R.id.colorView);
        final SeekBar redSeekBar = findViewById(R.id.redSeekBar);
        final SeekBar greenSeekBar = findViewById(R.id.greenSeekBar);
        final SeekBar blueSeekBar = findViewById(R.id.blueSeekBar);
        final SeekBar sizeSeekBar = findViewById(R.id.sizeSeekBar);
        sizeSeekBar.setVisibility(allowSizeChange ? View.VISIBLE : View.GONE);

        final Button pickColorButton = findViewById(R.id.pickColorButton);
        pickColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = Color.rgb(redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress());
                if (colorPickedListener != null) {
                    colorPickedListener.onColorPicked(color);
                    if (allowSizeChange) {
                        colorPickedListener.onSizePicked(sizeSeekBar.getProgress());
                    }
                }
                dismiss();
            }
        });

        SeekBar.OnSeekBarChangeListener colorChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color = Color.rgb(redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress());
                colorView.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };

        redSeekBar.setOnSeekBarChangeListener(colorChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangeListener);
    }

    public void setColorPickedListener(OnColorPickedListener listener) {
        this.colorPickedListener = listener;
    }
}
