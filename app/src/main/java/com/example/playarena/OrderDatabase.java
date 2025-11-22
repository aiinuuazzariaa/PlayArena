package com.example.playarena;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class OrderDatabase extends SQLiteOpenHelper{
    private static final String DB_NAME = "orders.db";
    private static final int DB_VERSION = 6;
    public static final String TABLE_ORDER = "orders";
    public static final String COL_ID = "id";
    public static final String COL_TRANSACTION = "transaction_number";
    public static final String COL_FIELD_NAME = "field_name";
    public static final String COL_PRICE = "price";
    public static final String COL_EMAIL = "email";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_DATE = "date";
    public static final String COL_START = "start_time";
    public static final String COL_END = "end_time";
    public static final String COL_NOTES = "notes";
    public static final String COL_TOTAL = "total_price";

    public OrderDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createOrderTable = "CREATE TABLE " + TABLE_ORDER + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TRANSACTION + " TEXT, "
                + COL_FIELD_NAME + " TEXT, "
                + COL_PRICE + " REAL, "
                + COL_EMAIL + " TEXT, "
                + COL_NAME + " TEXT, "
                + COL_PHONE + " TEXT, "
                + COL_DATE + " TEXT, "
                + COL_START + " TEXT, "
                + COL_END + " TEXT, "
                + COL_NOTES + " TEXT, "
                + COL_TOTAL + " REAL "
                + ")";
        db.execSQL(createOrderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        onCreate(db);
    }

    public boolean insertOrder(String transactionNumber, String fieldName, float price, String email, String name, String phone, String date, String start, String end, String notes, float totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_TRANSACTION, transactionNumber);
        cv.put(COL_FIELD_NAME, fieldName);
        cv.put(COL_PRICE, price);
        cv.put(COL_EMAIL, email);
        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        cv.put(COL_DATE, date);
        cv.put(COL_START, start);
        cv.put(COL_END, end);
        cv.put(COL_NOTES, notes);
        cv.put(COL_TOTAL, totalPrice);

        long result = db.insert(TABLE_ORDER, null, cv);
        return result != -1;
    }

    public String generateTransactionNumber() {
        return "TRX-" + System.currentTimeMillis();
    }

    public ArrayList<Order> getOrdersByEmail(String email) {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM orders WHERE email=?",
                new String[]{email}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String fieldName = cursor.getString(cursor.getColumnIndexOrThrow("field_name"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String start = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
                String end = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
                float totalPrice = cursor.getFloat(cursor.getColumnIndexOrThrow("total_price"));

                orders.add(new Order(id, name, fieldName, date, start, end, totalPrice));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return orders;
    }
}
