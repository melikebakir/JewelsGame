package com.example.jewelsgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;  // Versiyonu artırdık

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, personName TEXT, email TEXT, password TEXT)";
        db.execSQL(createUserTableQuery);
        String createScoreTableQuery = "CREATE TABLE scores (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, score INTEGER, " +
                "FOREIGN KEY(userId) REFERENCES users(id))";
        db.execSQL(createScoreTableQuery);
        Log.d("DatabaseHelper", "users ve scores tablosu başarıyla oluşturuldu, yol: " + db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
        Log.d("DatabaseHelper", "Veritabanı " + oldVersion + " sürümünden " + newVersion + " sürümüne yükseltildi.");
    }

    public long addScore(int userId, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("score", score);
        long result = -1;
        try {
            result = db.insert("scores", null, values);
            Log.d("DatabaseHelper", "Skor eklendi, sonuç: " + result);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Skor eklenirken hata oluştu", e);
        } finally {
             db.close(); // Kapatma işlemi buradan kaldırıldı
        }
        return result;
    }
    @SuppressLint("Range")
    public int getUserId(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = null;
        int userId = -1; // Varsayılan olarak -1 döndürülecek
        try {
            cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("id"));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Kullanıcı kimliği alınırken hata oluştu", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return userId;
    }


    public Cursor getScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT scores.id AS _id, users.personName, scores.score " +
                "FROM scores " +
                "INNER JOIN users ON scores.userId = users.id " +
                "ORDER BY scores.score DESC";
        return db.rawQuery(query, null);
    }

    public long addUser(String personName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("personName", personName);  // Doğru sütun adı
        values.put("email", email);
        values.put("password", password);
        long result = -1;
        try {
            result = db.insert("users", null, values);
            Log.d("DatabaseHelper", "Kullanıcı eklendi, sonuç: " + result);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Kullanıcı eklenirken hata oluştu", e);
        } finally {
             db.close(); // Kapatma işlemi buradan kaldırıldı
        }
        return result;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"email"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = null;
        boolean isValid = false;
        try {
            cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
            isValid = cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Kullanıcı kontrol edilirken hata oluştu", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close(); // Kapatma işlemi buradan kaldırıldı
        }
        Log.d("DatabaseHelper", "Kullanıcı kontrol sonucu: " + isValid);
        return isValid;
    }
}
