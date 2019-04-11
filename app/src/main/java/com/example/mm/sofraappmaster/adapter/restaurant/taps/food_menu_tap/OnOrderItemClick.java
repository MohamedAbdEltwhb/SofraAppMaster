package com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap;

import com.example.mm.sofraappmaster.data.locle.database.OrderEntity;

public interface OnOrderItemClick {
    void onAddClick(OrderEntity orderEntity);
    void onSubClick(OrderEntity orderEntity);
}
