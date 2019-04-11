package com.example.mm.sofraappmaster.helper;

public abstract class Constants {

    public static class SharedKeys {
        public static final String SHARED_PREF_NAME = "My_Shared";
        public static final String RESTAURANT_SHARED_PREF_NAME = "rest_Shared";

        /* UserClient Type Key */
        public static final String USER_TYPE = "user_type";

        /* API Token Key */
        public static final String RESTAURANT_API_TOKEN = "rest_api_token";
        public static final String CLIENT_API_TOKEN = "client_token";

        /* Restaurant Register Data Method Key */
        public static final String RESTAURANT_ID = "r_id";
        public static final String RESTAURANT_REGION_ID = "regionId";
        public static final String RESTAURANT_NAME = "name";
        public static final String RESTAURANT_EMAIL = "email";
        public static final String RESTAURANT_DELIVERY_COAST = "deliveryCost";
        public static final String RESTAURANT_MINIMUM_CHARGER = "minimumCharger";
        public static final String RESTAURANT_PHONE = "phone";
        public static final String RESTAURANT_WHATS_APP = "whatsapp";
        public static final String RESTAURANT_AVAILABILITY = "availability";


        public static final String RESTAURANT_REGION = "region";
        public static final String RESTAURANT_CITY = "city";

        /* Login Client Data Method Key */
        public static final String CLIENT_NAME = "c_name";
        public static final String CLIENT_EMAIL = "c_email";
        public static final String CLIENT_PHONE = "c_phone";
        public static final String CLIENT_REGION_ID = "c_regionId";
        public static final String CLIENT_ADDRESS = "c_address";
        public static final String CLIENT_CITY = "c_city";
        public static final String CLIENT_REGION = "c_region";
    }

    public static class FragmentsKeys {

        /* Communication (RestaurantFragment & RestaurantDetailsFragment) */
        public static final String ID = "id";

        /* Communication (TapFoodMenuFragment & MenuTapItemDetailsFragment) */
        public static final String PHOTO = "photo";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String TIME = "time";
        public static final String _ID = "id";


        /* ConnectWithUsFragment */
        public static final String COMPLAINT = "complaint";
        public static final String SUGGESTION = "suggestion";
        public static final String INQUIRY = "inquiry";

        /* Orders Basket Fragment Data Keys */
        public static final String ORDER_NAME = "o_name";
        public static final String ORDER_PHOTO = "o_photo";
        public static final String ORDER_PRICE = "o_price";
        public static final String ORDER_QUANTITY = "o_quantity";
        public static final String ORDER_ID = "o_id";
        public static final String ORDER_SPECIAL = "o_special";

        /* Update My Product Item */
        public static final String PRODUCT_PHOTO = "p_photo";
        public static final String PRODUCT_NAME = "p_name";
        public static final String PRODUCT_DESCRIPTION = "p_description";
        public static final String PRODUCT_PRICE = "p_price";
        public static final String PRODUCT_TIME = "p_time";
        public static final String PRODUCT_ID = "p_id";

    }

}
