package com.example.draw_with_friends;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorPickerDialog extends Dialog implements View.OnClickListener {

    private OnColorPickedListener colorPickedListener;
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private View colorPreview;

    public ColorPickerDialog(Context context) {
        super(context);
    }

    public void setColorPickedListener(OnColorPickedListener listener) {
        colorPickedListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color_picker);

        setTitle("Pick a Color");

        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);
        colorPreview = findViewById(R.id.color_preview);

        Button btnOK = findViewById(R.id.btn_ok);
        Button btnCancel = findViewById(R.id.btn_cancel);

        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        // Set initial color preview
        updateColorPreview();

        // Set up SeekBar listeners
        seekBarRed.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void updateColorPreview() {
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();
        int color = Color.rgb(red, green, blue);
        colorPreview.setBackgroundColor(color);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateColorPreview();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            if (colorPickedListener != null) {
                int color = Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress());
                colorPickedListener.onColorPicked(color);
            }
        }
        dismiss();
    }

    public interface OnColorPickedListener {
        void onColorPicked(int color);
    }
}
