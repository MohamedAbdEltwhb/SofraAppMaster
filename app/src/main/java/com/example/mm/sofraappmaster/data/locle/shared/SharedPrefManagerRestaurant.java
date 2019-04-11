package com.example.mm.sofraappmaster.data.locle.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mm.sofraappmaster.data.model.user.login_restaurant.RestaurantLoginUser;

import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_API_TOKEN;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_AVAILABILITY;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_CITY;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_DELIVERY_COAST;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_EMAIL;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_ID;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_MINIMUM_CHARGER;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_PHONE;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_REGION;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_REGION_ID;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_SHARED_PREF_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.RESTAURANT_WHATS_APP;

public class SharedPrefManagerRestaurant {

    private static SharedPrefManagerRestaurant mInstance;
    private Context mContext;

    private SharedPrefManagerRestaurant(Context context) {
        this.mContext = context;
    }

    public static synchronized SharedPrefManagerRestaurant getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerRestaurant(context);
        }
        return mInstance;
    }

    public void setRestaurantApiToken(String apiToken) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RESTAURANT_API_TOKEN, apiToken);
        editor.apply();
    }

    public String getRestaurantApiToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(RESTAURANT_API_TOKEN, null);
    }

    public void saveRestaurantLoginData(RestaurantLoginUser restaurantLoginUser) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(RESTAURANT_ID, restaurantLoginUser.getId());
        editor.putString(RESTAURANT_REGION_ID, restaurantLoginUser.getRegionId());
        editor.putString(RESTAURANT_NAME, restaurantLoginUser.getName());
        editor.putString(RESTAURANT_EMAIL, restaurantLoginUser.getEmail());
        editor.putString(RESTAURANT_DELIVERY_COAST, restaurantLoginUser.getDeliveryCost());
        editor.putString(RESTAURANT_MINIMUM_CHARGER, restaurantLoginUser.getMinimumCharger());
        editor.putString(RESTAURANT_PHONE, restaurantLoginUser.getPhone());
        editor.putString(RESTAURANT_WHATS_APP, restaurantLoginUser.getWhatsapp());
        editor.putString(RESTAURANT_AVAILABILITY, restaurantLoginUser.getAvailability());

        editor.apply();
    }

    public RestaurantLoginUser getRestaurantLoginData(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new RestaurantLoginUser(
                sharedPreferences.getInt(RESTAURANT_ID, 0),
                sharedPreferences.getString(RESTAURANT_REGION_ID, null),
                sharedPreferences.getString(RESTAURANT_NAME, null),
                sharedPreferences.getString(RESTAURANT_EMAIL, null),
                sharedPreferences.getString(RESTAURANT_DELIVERY_COAST, null),
                sharedPreferences.getString(RESTAURANT_MINIMUM_CHARGER, null),
                sharedPreferences.getString(RESTAURANT_PHONE, null),
                sharedPreferences.getString(RESTAURANT_WHATS_APP, null),
                sharedPreferences.getString(RESTAURANT_AVAILABILITY, null)
        );
    }

    public void saveRestaurantLoginCity(int cityId, int regionId) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(RESTAURANT_CITY, cityId);
        editor.putInt(RESTAURANT_REGION, regionId);

        editor.apply();
    }

    public int getRestaurantLoginCity(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(RESTAURANT_CITY, 0);
    }

    public int getRestaurantLoginRegionId(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(RESTAURANT_REGION, 0);
    }


    public void clare() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(RESTAURANT_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
