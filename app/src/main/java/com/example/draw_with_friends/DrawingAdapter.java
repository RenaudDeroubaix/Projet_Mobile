package com.example.draw_with_friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrawingAdapter extends RecyclerView.Adapter<DrawingAdapter.ViewHolder> {

    private List<String> drawingNames;
    private int[] rainbowColors = {
            0xFFFF0000, // Red
            0xFFFF7F00, // Orange
            0xFFFFFF00, // Yellow
            0xFF00FF00, // Green
            0xFF0000FF, // Blue
            0xFF4B0082, // Indigo
            0xFF8B00FF  // Violet
    };

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String drawingName);
    }

    public DrawingAdapter(List<String> drawingNames, OnItemClickListener onItemClickListener) {
        this.drawingNames = drawingNames;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String drawingName = drawingNames.get(position);
        holder.drawingNameTextView.setText(drawingName);
        int colorIndex = position % rainbowColors.length;
        holder.itemView.setBackgroundColor(rainbowColors[colorIndex]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(drawingName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawingNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView drawingNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            drawingNameTextView = itemView.findViewById(R.id.drawingNameTextView);
        }
    }
}
