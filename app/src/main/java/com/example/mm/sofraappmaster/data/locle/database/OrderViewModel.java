package com.example.mm.sofraappmaster.data.locle.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private LiveData<List<OrderEntity>> mAllOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        orderRepository = new OrderRepository(application);
        mAllOrders = orderRepository.getAllOrders();
    }

    public void insert(OrderEntity orderEntity) {
        orderRepository.insert(orderEntity);
    }

    public void update(OrderEntity orderEntity) {
        orderRepository.update(orderEntity);
    }

    public void delete(OrderEntity orderEntity) {
        orderRepository.delete(orderEntity);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAllOrders();
    }

    public LiveData<List<OrderEntity>> getAllOrders(){
        return mAllOrders;
    }

}