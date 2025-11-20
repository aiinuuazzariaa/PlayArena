package com.example.playarena;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {
    EditText etName, etPhone, etDate, etStartTime, etEndTime, etNotes;
    TextView tvFieldName, tvFieldPrice, tvTotalPrice;
    Button btnBackOrder, btnConfirmOrder;
    OrderDatabase orderDb;
    String lapanganName;
    int lapanganPrice;
    int totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderDb = new OrderDatabase(this);

        initViews();
        getIntentData();
        setupDatePicker();
        setupTimePicker();
        setupButtons();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDate = findViewById(R.id.etDate);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        etNotes = findViewById(R.id.etNotes);

        tvFieldName = findViewById(R.id.tvFieldName);
        tvFieldPrice = findViewById(R.id.tvFieldPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        btnBackOrder = findViewById(R.id.btnBackOrder);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
    }

    private void getIntentData() {
        lapanganName = getIntent().getStringExtra("name");
        lapanganPrice = getIntent().getIntExtra("price", 0);

        tvFieldName.setText(lapanganName);
        tvFieldPrice.setText("Rp " + lapanganPrice);
    }

    private void setupDatePicker() {
        etDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(
                    OrderActivity.this,
                    (view, y, m, d) -> etDate.setText(d + "/" + (m + 1) + "/" + y),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });
    }

    private void setupTimePicker() {
        etStartTime.setOnClickListener(v -> {
            TimePickerDialog startDialog = new TimePickerDialog(
                    OrderActivity.this,
                    (view, h, m) -> etStartTime.setText(String.format("%02d:%02d", h, m)),
                    8, 0, true
            );
            startDialog.show();
        });

        etEndTime.setOnClickListener(v -> {
            TimePickerDialog endDialog = new TimePickerDialog(
                    OrderActivity.this,
                    (view, h, m) -> {
                        etEndTime.setText(String.format("%02d:%02d", h, m));
                        calculateTotal();
                    },
                    8, 0, true
            );
            endDialog.show();
        });
    }

    private void calculateTotal() {
        totalPrice = calculateTotalPrice();
        if (totalPrice > 0) {
            tvTotalPrice.setText("Rp " + totalPrice);
        } else {
            tvTotalPrice.setText("Invalid Time!");
        }
    }

    private int calculateTotalPrice() {
        try {
            int startHour = Integer.parseInt(etStartTime.getText().toString().substring(0, 2));
            int endHour = Integer.parseInt(etEndTime.getText().toString().substring(0, 2));

            if (endHour <= startHour) {
                return 0;
            }

            int duration = endHour - startHour;
            return duration * lapanganPrice;

        } catch (Exception e) {
            return 0;
        }
    }

    private void setupButtons() {
        btnBackOrder.setOnClickListener(v -> finish());

        btnConfirmOrder.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            String date = etDate.getText().toString();
            String start = etStartTime.getText().toString();
            String end = etEndTime.getText().toString();
            String notes = etNotes.getText().toString();

            if (name.isEmpty() || phone.isEmpty() || date.isEmpty() ||
                    start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            String transactionNumber = orderDb.generateTransactionNumber();

            boolean success = orderDb.insertOrder(
                    transactionNumber,
                    lapanganName,
                    lapanganPrice,
                    name,
                    phone,
                    date,
                    start,
                    end,
                    notes,
                    totalPrice
            );

            Toast.makeText(
                    this,
                    success ? "Order Saved!" : "Failed to save order!",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
