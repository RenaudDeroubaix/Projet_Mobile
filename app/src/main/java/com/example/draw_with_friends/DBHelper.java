package com.example.draw_with_friends;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;



public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null, "username = ? AND password = ?", new String[]{username, password}, null, null, null);
        boolean signInSuccess = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return signInSuccess;
    }

    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null, "username = ?", new String[]{username}, null, null, null);
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez votre table d'utilisateurs lors de la création de la base de données
        String createUserTableQuery = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettez à jour la base de données si nécessaire
        // Cette méthode est appelée lorsqu'une mise à jour de la version de la base de données est nécessaire
        // Ici, vous pouvez mettre à jour le schéma de la base de données ou effectuer d'autres actions nécessaires
    }

    public long insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long newRowId = db.insert("users", null, values);
        db.close();
        return newRowId;
    }
}
