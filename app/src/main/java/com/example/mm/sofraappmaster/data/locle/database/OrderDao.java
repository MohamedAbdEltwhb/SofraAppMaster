package com.example.mm.sofraappmaster.data.locle.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insert (OrderEntity orderEntity);

    @Update
    void update(OrderEntity orderEntity);

    @Delete
    void delete(OrderEntity orderEntity);

    @Query("DELETE FROM order_table")
    void deleteAllOrders();

    @Query("SELECT * FROM order_table")
    LiveData<List<OrderEntity>> getAllOrders();

}
