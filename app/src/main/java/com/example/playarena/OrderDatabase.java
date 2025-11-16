package com.example.playarena;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDatabase extends SQLiteOpenHelper{
    private static final String DB_NAME = "orders.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_ORDER = "orders";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_DATE = "date";
    public static final String COL_START = "start_time";
    public static final String COL_END = "end_time";
    public static final String COL_NOTES = "notes";

    public OrderDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createOrderTable = "CREATE TABLE " + TABLE_ORDER + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_PHONE + " TEXT, "
                + COL_DATE + " TEXT, "
                + COL_START + " TEXT, "
                + COL_END + " TEXT, "
                + COL_NOTES + " TEXT"
                + ")";
        db.execSQL(createOrderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        onCreate(db);
    }

    public boolean insertOrder(String name, String phone, String date, String start, String end, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        cv.put(COL_DATE, date);
        cv.put(COL_START, start);
        cv.put(COL_END, end);
        cv.put(COL_NOTES, notes);

        long result = db.insert(TABLE_ORDER, null, cv);
        return result != -1;
    }
}
