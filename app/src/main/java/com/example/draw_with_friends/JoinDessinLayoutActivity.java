package com.example.draw_with_friends;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
public class JoinDessinLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joindessin);

        Button buttonGallery = findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(v -> {
            Intent intent = new Intent(JoinDessinLayoutActivity.this, GallerieLayoutActivity.class);
            startActivity(intent);
        });

        Button buttonCanva = findViewById(R.id.buttonNouveauDessin);
        buttonCanva.setOnClickListener(v -> {
            EditText editTextDrawingName = findViewById(R.id.editTextDrawingName);
            String drawingName = editTextDrawingName.getText().toString().trim();
            if (!drawingName.isEmpty()) {
                Intent intent = new Intent(JoinDessinLayoutActivity.this, CanvaLayoutActivity.class);
                intent.putExtra("drawingName", drawingName);
                startActivity(intent);
            } else {
                // Optionnel : Afficher un message si le nom est vide
                Toast.makeText(this, "Veuillez entrer un nom pour le dessin", Toast.LENGTH_SHORT).show();
            }
        });
    }

}