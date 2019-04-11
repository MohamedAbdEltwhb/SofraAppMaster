package com.example.mm.sofraappmaster.data.locle.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "order_table")
public class OrderEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mName;
    private String mPrice;
    private String mPhotoUrl;
    private String mQuantity;
    private String mSpecialOrder;
    private int mItemId;

    public OrderEntity(String mName, String mPrice, String mPhotoUrl, String mQuantity, String mSpecialOrder, int mItemId) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mPhotoUrl = mPhotoUrl;
        this.mQuantity = mQuantity;
        this.mSpecialOrder = mSpecialOrder;
        this.mItemId = mItemId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public String getSpecialOrder() {
        return mSpecialOrder;
    }

    public int getItemId() {
        return mItemId;
    }
}

