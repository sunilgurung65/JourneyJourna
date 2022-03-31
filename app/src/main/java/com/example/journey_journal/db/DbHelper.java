package com.example.journey_journal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.journey_journal.ModelClass;

import java.util.ArrayList;
import java.util.Arrays;

public class DbHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "id";
    public static final String TITLE = "title";
    public static final String DESC = "distance";
    public static final String LOCATION = "location";
    public static final String IMAGE = "image";

    public static final String[] COLUMNS = {COLUMN_ID, TITLE, DESC, LOCATION, IMAGE};

    public static final String TABLE_NAME = "Journey";
    public static final String DB_NAME = "journal.sqlite";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT NOT NULL, " +
                DESC + " TEXT NOT NULL, " +
                LOCATION + " TEXT NOT NULL, " +
                IMAGE + " TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sqlQuery);
        onCreate(sqLiteDatabase);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Boolean insert(
            String title,
            String desc,
            String location,
            byte[] image
    ) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, desc);
        statement.bindString(3, location);
        statement.bindBlob(4, image);

        long result = statement.executeInsert();
        return result != -1;
    }

    public ModelClass getElementById(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        Cursor cursor = database.rawQuery(
                sqlQuery,
                new String[]{String.valueOf(id)}
        );
        ModelClass model = null;
        if (cursor != null) {
            cursor.moveToFirst();
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String location = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            model = new ModelClass(id, title, desc, location, image);
            cursor.close();
        }
        database.close();
        return model;
    }

    public Cursor getAll(String sqlQuery) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sqlQuery, null);
    }

    public Boolean update(
            String _id,
            String title,
            String desc,
            String location,
            byte[] image
    ) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, _id);
        contentValues.put(TITLE, title);
        contentValues.put(DESC, desc);
        contentValues.put(LOCATION, location);
        contentValues.put(IMAGE, Arrays.toString(image));
        int result = database.update(
                TABLE_NAME,
                contentValues,
                COLUMN_ID + "=?",
                new String[]{_id});
        return result != -1;
    }

    public void delete(long _id) {
        SQLiteDatabase database = getWritableDatabase();
        int result = database.delete(
                TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(_id)});
    }
}
