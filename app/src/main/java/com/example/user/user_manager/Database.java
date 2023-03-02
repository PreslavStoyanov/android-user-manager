package com.example.user.user_manager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "maps_app";
    private static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + ID + " INTEGER PRIMARY KEY,"
                        + USERNAME + " TEXT,"
                        + PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDeleteTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void addUser(User contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, contact.getUsername());
        values.put(PASSWORD, contact.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public User getUser(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID, USERNAME, PASSWORD},
                ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        return new User(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
    }

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    public int updateUser(User contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, contact.getUsername());
        values.put(PASSWORD, contact.getPassword());
        return db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteUser(User contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int getUsersCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}