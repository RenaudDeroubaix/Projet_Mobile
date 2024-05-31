package com.example.draw_with_friends;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReadOnlyGalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DrawingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readonly_gallery);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHelper dbHelper = new DBHelper(this);
        List<String> drawingNames = dbHelper.getAllDrawingNames();

        adapter = new DrawingAdapter(drawingNames, new DrawingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String drawingName) {
                Intent intent = new Intent(ReadOnlyGalleryActivity.this, DrawingActivity.class);
                intent.putExtra("drawingName", drawingName);
                intent.putExtra("isReadOnly", true);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
