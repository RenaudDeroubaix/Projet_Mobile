package com.example.draw_with_friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;
import android.content.Intent;
public class DrawingAdapter extends RecyclerView.Adapter<DrawingAdapter.ViewHolder> {
    private List<String> drawings;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }

    public DrawingAdapter(List<String> drawings) {
        this.drawings = drawings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawing, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String drawingName = drawings.get(position);
        holder.textView.setText(drawingName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ouvrir une nouvelle activité ou fragment avec le nom du dessin
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, DrawingActivity.class);
                intent.putExtra("drawingName", drawingName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawings.size();
    }
}
