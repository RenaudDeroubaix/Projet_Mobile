package com.example.draw_with_friends;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;


public class AcceuilLayoutActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        int plan = getIntent().getIntExtra("plan", 0);
        System.out.println("Plan VALUE: " + plan);
        Button buttonDessins= findViewById(R.id.buttonJoinDessins);
        buttonDessins.setOnClickListener(v -> {
            // Naviguer vers la nouvelle activité ConnectionLayoutActivity
            Intent intent = new Intent(AcceuilLayoutActivity.this, JoinDessinLayoutActivity.class);
            intent.putExtra("plan", plan);
            startActivity(intent);
        });
        Button buttonGallerie = findViewById(R.id.buttonGallerie);
        buttonGallerie.setOnClickListener(v -> {

            // Naviguer vers la nouvelle activité ConnectionLayoutActivity
            Intent intent = new Intent(AcceuilLayoutActivity.this, ReadOnlyGalleryActivity.class);
            startActivity(intent);
        });
        // Récupérer l'adresse e-mail passée via l'intent
        String email = getIntent().getStringExtra("email");
        // Afficher l'adresse e-mail dans un TextView
        TextView emailTextView = findViewById(R.id.emailTextView);
        emailTextView.setText(email);

        // Ajouter un OnClickListener au bouton Déconnexion
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Effectuer les actions de déconnexion ici (par exemple, supprimer les informations d'identification stockées)
                // Puis, lancer MainActivity
                Intent intent = new Intent(AcceuilLayoutActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Terminer l'activité actuelle pour éviter de revenir à AcceuilLayoutActivity en appuyant sur Retour
            }
        });

    }

}
