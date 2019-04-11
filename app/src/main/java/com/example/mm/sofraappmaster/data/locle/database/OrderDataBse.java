package com.example.mm.sofraappmaster.data.locle.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {OrderEntity.class}, version = 1, exportSchema = false)
public abstract class OrderDataBse extends RoomDatabase {

    private static OrderDataBse instance;

    public abstract OrderDao orderDao();

    public static synchronized OrderDataBse getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OrderDataBse.class, "order_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDBAsyncTask(instance).execute();
        }
    };

    public static class PopulateDBAsyncTask extends AsyncTask <Void, Void, Void>{
        private OrderDao orderDao;

        private PopulateDBAsyncTask(OrderDataBse db) {
            this.orderDao = db.orderDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            orderDao.insert(new OrderEntity("Name1", "50", "mmmmm", "1", "any", 1));
            orderDao.insert(new OrderEntity("Name2", "50", "mmmmm", "2", "any", 2));
            orderDao.insert(new OrderEntity("Name3", "50", "mmmmm", "3", "any", 3));
            return null;
        }
    }
}
