package com.example.playarena;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {
    EditText etName, etPhone, etDate, etStartTime, etEndTime, etNotes;
    Button btnBackOrder, btnConfirmOrder;
    OrderDatabase orderDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderDb = new OrderDatabase(this);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDate = findViewById(R.id.etDate);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        etNotes = findViewById(R.id.etNotes);

        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    OrderActivity.this,
                    (view, y, m, d) -> {
                        String date = d + "/" + (m + 1) + "/" + y;
                        etDate.setText(date);
                    }, year, month, day
            );
            datePicker.show();
        });

        etStartTime.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(
                    OrderActivity.this,
                    (view, hour, minute) -> {
                        if (hour < 8) hour = 8;
                        if (hour > 21) hour = 21;

                        String time = String.format("%02d:%02d", hour, minute);
                        etStartTime.setText(time);
                    },
                    8, 0, true
            );
            timePicker.show();
        });

        etEndTime.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(
                    OrderActivity.this,
                    (view, hour, minute) -> {
                        if (hour < 8) hour = 8;
                        if (hour > 21) hour = 21;

                        String time = String.format("%02d:%02d", hour, minute);
                        etEndTime.setText(time);
                    },
                    8, 0, true
            );
            timePicker.show();
        });

        Button btnBackOrder = findViewById(R.id.btnBackOrder);
        btnBackOrder.setOnClickListener(v -> finish());

        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        btnConfirmOrder.setOnClickListener(v -> saveOrder());
    }

    private void saveOrder() {

        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String date = etDate.getText().toString();
        String start = etStartTime.getText().toString();
        String end = etEndTime.getText().toString();
        String notes = etNotes.getText().toString();

        boolean success = orderDb.insertOrder(name, phone, date, start, end, notes);

        if (success) {
            Toast.makeText(this, "Order saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save order!", Toast.LENGTH_SHORT).show();
        }
    }
}
