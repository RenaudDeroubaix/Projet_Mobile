package com.example.draw_with_friends;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
public class JoinDessinLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joindessin);
        Button buttonGallery = findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(v -> {
            // Naviguer vers l'activité GallerieLayoutActivity
            Intent intent = new Intent(JoinDessinLayoutActivity.this, GallerieLayoutActivity.class);
            startActivity(intent);
        });

        Button buttonCanva = findViewById(R.id.buttonNouveauDessin);
        buttonCanva.setOnClickListener(v -> {
            // Naviguer vers la nouvelle activité ConnectionLayoutActivity
            Intent intent = new Intent(JoinDessinLayoutActivity.this, CanvaLayoutActivity.class);
            startActivity(intent);
        });

    }

}