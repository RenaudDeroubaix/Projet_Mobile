package com.example.draw_with_friends;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class GallerieLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DrawingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerie);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHelper dbHelper = new DBHelper(this);
        List<String> drawingNames = dbHelper.getAllDrawingNames();

        adapter = new DrawingAdapter(drawingNames);
        recyclerView.setAdapter(adapter);
    }
}


