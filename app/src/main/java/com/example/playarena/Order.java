package com.example.playarena;

public class Order {
    public String transactionNumber, fieldName, date, start, end;
    public float totalPrice;

    public Order(int id, String transactionNumber, String fieldName, String date, String start, String end, float totalPrice) {
        this.transactionNumber = transactionNumber;
        this.fieldName = fieldName;
        this.date = date;
        this.start = start;
        this.end = end;
        this.totalPrice = totalPrice;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
