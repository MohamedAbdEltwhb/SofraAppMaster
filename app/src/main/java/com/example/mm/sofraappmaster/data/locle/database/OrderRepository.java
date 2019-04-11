package com.example.mm.sofraappmaster.data.locle.database;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class OrderRepository {

    private OrderDao orderDao;
    private LiveData<List<OrderEntity>> mAllOrders;

    public OrderRepository(Application application) {
        OrderDataBse dataBse = OrderDataBse.getInstance(application);

        orderDao = dataBse.orderDao();
        mAllOrders = orderDao.getAllOrders();
    }

    public void insert(OrderEntity orderEntity) {
        new InsertOrderAsyncTask(orderDao).execute(orderEntity);
    }

    public void update(OrderEntity orderEntity) {
        new UpdateOrderAsyncTask(orderDao).execute(orderEntity);
    }

    public void delete(OrderEntity orderEntity) {
        new DeleteOrderAsyncTask(orderDao).execute(orderEntity);
    }

    public void deleteAllOrders() {
        new DeleteAllOrderAsyncTask(orderDao).execute();
    }

    public LiveData<List<OrderEntity>> getAllOrders(){
        return mAllOrders;
    }


    private static class InsertOrderAsyncTask extends AsyncTask<OrderEntity, Void, Void> {
        private OrderDao orderDao;

        private InsertOrderAsyncTask(OrderDao orderDao) {
            this.orderDao = orderDao;
        }

        @Override
        protected Void doInBackground(OrderEntity... orderEntities) {
            orderDao.insert(orderEntities[0]);
            return null;
        }
    }

    private static class UpdateOrderAsyncTask extends AsyncTask<OrderEntity, Void, Void> {
        private OrderDao orderDao;

        private UpdateOrderAsyncTask(OrderDao orderDao) {
            this.orderDao = orderDao;
        }

        @Override
        protected Void doInBackground(OrderEntity... orderEntities) {
            orderDao.update(orderEntities[0]);
            return null;
        }
    }

    private static class DeleteOrderAsyncTask extends AsyncTask<OrderEntity, Void, Void> {
        private OrderDao orderDao;

        private DeleteOrderAsyncTask(OrderDao orderDao) {
            this.orderDao = orderDao;
        }

        @Override
        protected Void doInBackground(OrderEntity... orderEntities) {
            orderDao.delete(orderEntities[0]);
            return null;
        }
    }

    private static class DeleteAllOrderAsyncTask extends AsyncTask<Void, Void, Void> {
        private OrderDao orderDao;

        private DeleteAllOrderAsyncTask(OrderDao orderDao) {
            this.orderDao = orderDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            orderDao.deleteAllOrders();
            return null;
        }
    }

}
