package com.example.draw_with_friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ConnectionLayoutActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    private String getUsernameFromEditText() {
        EditText emailEditText = findViewById(R.id.emailEditText);
        return emailEditText.getText().toString();
    }

    private String getPasswordFromEditText() {
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        return passwordEditText.getText().toString();
    }

    private String getUsernameFromEditTextConnection() {
        EditText emailEditTextConnection = findViewById(R.id.emailEditTextConnection);
        return emailEditTextConnection.getText().toString();
    }

    private String getPasswordFromEditTextConnection() {
        EditText passwordEditTextConnection = findViewById(R.id.passwordEditTextConnection);
        return passwordEditTextConnection.getText().toString();
    }

    private Integer getPlanFromRadioGroup() {
        RadioGroup planRadioGroup = findViewById(R.id.radiogroup);
        int buttonCheckedId = planRadioGroup.getCheckedRadioButtonId();
        if (buttonCheckedId == R.id.radioButton2) {
            return 0; // BasicPlan
        } else if (buttonCheckedId == R.id.radioButton3) {
            return 1; // PremiumPlan
        }
        return 0; // Default to BasicPlan if none is selected
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_layout);

        dbHelper = new DBHelper(this);

        Button buttonSignIn = findViewById(R.id.connectionBox);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameFromEditTextConnection();
                String password = getPasswordFromEditTextConnection();

                boolean signInSuccess = dbHelper.checkUserCredentials(username, password);

                if (signInSuccess) {
                    // Si la connexion réussit, passer à l'activité AcceuilLayoutActivity
                    Intent intent = new Intent(ConnectionLayoutActivity.this, AcceuilLayoutActivity.class);
                    intent.putExtra("email", username); // Passer l'e-mail à l'activité AcceuilLayoutActivity
                    intent.putExtra("plan", dbHelper.getPlanFromExistingUser(username)); // Passer le plan de l'utilisateur
                    startActivity(intent);
                } else {
                    // Afficher un message d'erreur si la connexion échoue
                    Toast.makeText(ConnectionLayoutActivity.this, "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonSignUp = findViewById(R.id.inscriptionBox);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameFromEditText();
                String password = getPasswordFromEditText();
                Integer plan = getPlanFromRadioGroup();

                if (dbHelper.isUserExists(username)) {
                    Toast.makeText(ConnectionLayoutActivity.this, "Cet e-mail est déjà utilisé", Toast.LENGTH_SHORT).show();
                } else {
                    long newRowId = dbHelper.insertUser(username, password, plan);

                    if (newRowId != -1) {
                        Intent intent = new Intent(ConnectionLayoutActivity.this, AcceuilLayoutActivity.class);
                        intent.putExtra("email", username);
                        intent.putExtra("plan", plan);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ConnectionLayoutActivity.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
