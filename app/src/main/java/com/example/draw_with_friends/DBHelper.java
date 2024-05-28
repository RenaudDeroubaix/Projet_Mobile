package com.example.draw_with_friends;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

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
        String createUserTableQuery = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
        db.execSQL(createUserTableQuery);

        // Ajoutez une colonne pour le nom du dessin
        String createDrawingTableQuery = "CREATE TABLE drawings (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, drawing_data TEXT)";
        db.execSQL(createDrawingTableQuery);
    }


    public long insertDrawing(String name, String drawingData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);  // Ajoutez le nom du dessin
        values.put("drawing_data", drawingData);
        long newRowId = db.insert("drawings", null, values);
        db.close();
        return newRowId;
    }



    public List<String> getAllDrawings() {
        List<String> drawings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT drawing_data FROM drawings", null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex("drawing_data");
            if (columnIndex != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        String drawingData = cursor.getString(columnIndex);
                        drawings.add(drawingData);
                    } while (cursor.moveToNext());
                }
            } else {
                System.out.println("La colonne 'drawing_data' n'existe pas dans le curseur.");
                System.err.println("La colonne 'drawing_data' n'existe pas dans le curseur.");
            }
            cursor.close();
        }
        db.close();
        return drawings;
    }
    public String getDrawingDataByName(String drawingName) {
        String drawingData = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("drawings", null, "name = ?", new String[]{drawingName}, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex("drawing_data");
            if (columnIndex != -1) {
                drawingData = cursor.getString(cursor.getColumnIndex("drawing_data"));
            }cursor.close();
        }

        db.close();
        return drawingData;
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

    public List<String> getAllDrawingNames() {
        List<String> drawingNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM drawings", null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex("name");
            if (columnIndex != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(columnIndex);
                        drawingNames.add(name);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        }
        db.close();
        return drawingNames;
    }



}
