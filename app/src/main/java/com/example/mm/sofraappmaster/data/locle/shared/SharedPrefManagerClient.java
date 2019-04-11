package com.example.mm.sofraappmaster.data.locle.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mm.sofraappmaster.data.model.user.clint.client_login.UserClient;


import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_ADDRESS;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_API_TOKEN;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_CITY;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_EMAIL;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_PHONE;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_REGION;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.CLIENT_REGION_ID;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.SHARED_PREF_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.SharedKeys.USER_TYPE;


public class SharedPrefManagerClient {

    private static SharedPrefManagerClient mInstance;
    private Context mContext;

    private SharedPrefManagerClient(Context context) {
        this.mContext = context;
    }

    public static synchronized SharedPrefManagerClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerClient(context);
        }
        return mInstance;
    }

    public void saveClientLoginData(UserClient userClient) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CLIENT_NAME, userClient.getName());
        editor.putString(CLIENT_EMAIL, userClient.getEmail());
        editor.putString(CLIENT_PHONE, userClient.getPhone());
        editor.putString(CLIENT_ADDRESS, userClient.getAddress());
        editor.putString(CLIENT_REGION, userClient.getRegionId());

        editor.apply();
    }

    public UserClient getClientLoginData(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserClient(
                sharedPreferences.getString(CLIENT_NAME, null),
                sharedPreferences.getString(CLIENT_EMAIL, null),
                sharedPreferences.getString(CLIENT_PHONE, null),
                sharedPreferences.getString(CLIENT_REGION, null),
                sharedPreferences.getString(CLIENT_ADDRESS, null)
        );
    }

    public void saveClientLoginCity(int cityId, int regionId) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(CLIENT_CITY, cityId);
        editor.putInt(CLIENT_REGION_ID, regionId);

        editor.apply();
    }

    public int getClientLoginCity(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CLIENT_CITY, 0);
    }

    public int getClientLoginRegionId(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CLIENT_REGION_ID, 0);
    }

    public void setClientApiToken(String apiToken) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLIENT_API_TOKEN, apiToken);
        editor.apply();
    }

    public String getClientApiToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CLIENT_API_TOKEN, null);
    }

    public void setUserType(int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_TYPE, value);
        editor.apply();
    }

    public int getUserType() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_TYPE, 0);
    }

    public void clare() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


//    public void saveRestaurantRegisterData(RegisterData registerData) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString(RESTAURANT_NAME, registerData.getName());
//        editor.putString(RESTAURANT_EMAIL, registerData.getEmail());
//        editor.putString(RESTAURANT_PHONE, registerData.getPhone());
//        editor.putString(RESTAURANT_WHATS_APP, registerData.getWhatsapp());
//        editor.putString(RESTAURANT_REGION_ID, registerData.getRegionId());
//        editor.putString(RESTAURANT_DELIVERY_COAST, registerData.getDeliveryCost());
//        editor.putString(RESTAURANT_MINIMUM_CHARGER, registerData.getMinimumCharger());
//        editor.putString(RESTAURANT_AVAILABILITY, registerData.getAvailability());
//        editor.putInt(RESTAURANT_ID, registerData.getId());
//        editor.putString(RESTAURANT_REGION, registerData.getRegion().getName());
//        editor.putString(RESTAURANT_CITY, registerData.getRegion().getCity().getName());
//        editor.apply();
//    }
//
//    public RegisterData getRestaurantRegisterData(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return new RegisterData(
//                sharedPreferences.getString(RESTAURANT_NAME, null),
//                sharedPreferences.getString(RESTAURANT_EMAIL, null),
//                sharedPreferences.getString(RESTAURANT_PHONE, null),
//                sharedPreferences.getString(RESTAURANT_WHATS_APP, null),
//                sharedPreferences.getString(RESTAURANT_REGION_ID, null),
//                sharedPreferences.getString(RESTAURANT_DELIVERY_COAST, null),
//                sharedPreferences.getString(RESTAURANT_MINIMUM_CHARGER, null),
//                sharedPreferences.getString(RESTAURANT_AVAILABILITY, null),
//                sharedPreferences.getInt(RESTAURANT_ID, -1),
//                sharedPreferences.getString(RESTAURANT_REGION, null),
//                sharedPreferences.getString(RESTAURANT_CITY, null)
//        );
//    }



//    public void saveUser(LoginClient loginClient){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putInt(ID, loginClient.getId());
//        editor.putString(CREATED_AT, loginClient.getCreatedAt());
//        editor.putString(UPDATED_AT, loginClient.getUpdatedAt());
//        editor.putString(NAME, loginClient.getName());
//        editor.putString(EMILE, loginClient.getEmail());
//        editor.putString(BIRTH_DATE, loginClient.getBirthDate());
//        editor.putString(CITY_ID, loginClient.getCityId());
//        editor.putString(PHONE, loginClient.getPhone());
//        editor.putString(DONATION_LAST_DATE, loginClient.getDonationLastDate());
//        editor.putString(BLOOD_TYPE, loginClient.getBloodTypeId());
//        editor.putString(IS_ACTIVE, loginClient.getIsActive());
//        editor.putString(PIN_CODE, loginClient.getPinCode());
//
//
//        editor.apply();
//    }

//    public boolean isLoggedIn(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt(ID, -1) != -1;
//    }
//
//    public LoginClient getUser(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//        return new LoginClient(
//                sharedPreferences.getInt(ID, -1),
//                sharedPreferences.getString(CREATED_AT, null),
//                sharedPreferences.getString(UPDATED_AT, null),
//                sharedPreferences.getString(NAME, null),
//                sharedPreferences.getString(EMILE, null),
//                sharedPreferences.getString(BIRTH_DATE, null),
//                sharedPreferences.getString(CITY_ID, null),
//                sharedPreferences.getString(PHONE, null),
//                sharedPreferences.getString(DONATION_LAST_DATE, null),
//                sharedPreferences.getString(BLOOD_TYPE, null),
//                sharedPreferences.getString(IS_ACTIVE, null),
//                sharedPreferences.getString(PIN_CODE, null));
//    }
//
//    public void setSelectedBloodItemPosition(int position){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("BloodPosition", position);
//        editor.apply();
//    }
//
//    public int getSelectedBloodItemPosition(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt("BloodPosition",0);
//    }
//
//    public void setSelectedGovernmentItemPosition(int position){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("Governments", position);
//        editor.apply();
//    }
//
//    public int getSelectedGovernmentsItemPosition(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt("Governments",0);
//    }
//
//
//    public void setSelectedCityItemPosition(int position){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("CityPosition", position);
//        editor.apply();
//    }
//
//    public int getSelectedCityItemPosition(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt("CityPosition",0);
//    }

}
