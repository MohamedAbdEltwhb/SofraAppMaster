package com.example.mm.sofraappmaster.data.rest;


import com.example.mm.sofraappmaster.data.model.client_new_order.ClientNewOrder;
import com.example.mm.sofraappmaster.data.model.general.offers.restaurant_my_offers.RestaurantMyOffersList;
import com.example.mm.sofraappmaster.data.model.general.spinner.categories.Categories;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.Cites;
import com.example.mm.sofraappmaster.data.model.general.connect_us.ConnectUs;
import com.example.mm.sofraappmaster.data.model.my_orders.MyOrders;
import com.example.mm.sofraappmaster.data.model.general.offers.Offers;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.Regions;
import com.example.mm.sofraappmaster.data.model.restaurant.addReview.AddReview;
import com.example.mm.sofraappmaster.data.model.restaurant.listOfRestaurantItems.ListOfRestaurantItems;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantDetails.RestaurantDetails;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantList.RestaurantList;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantReviews.RestaurantReviews;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_add_item.RestaurantAddItem;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_delete_item.RestaurantDeleteItem;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_my_items.RestaurantMyItems;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_update_item.RestaurantUpdateItem;
import com.example.mm.sofraappmaster.data.model.restaurant_offer.restaurant_new_offer.RestaurantNewOffer;
import com.example.mm.sofraappmaster.data.model.user.clint.client_edit_profile.ClientEditProfile;
import com.example.mm.sofraappmaster.data.model.user.clint.client_login.ClientLogin;
import com.example.mm.sofraappmaster.data.model.user.clint.client_reset_password.ClientResetPassword;
import com.example.mm.sofraappmaster.data.model.user.clint.clint_register.ClintRegister;
import com.example.mm.sofraappmaster.data.model.user.login_restaurant.LoginRestaurant;
import com.example.mm.sofraappmaster.data.model.user.restaurant_register.RestaurantRegister;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("cities")
    Call<Cites> getCitesList();

    @GET("regions")
    Call<Regions> getRegions(@Query("city_id") int city_id);

    @GET("categories")
    Call<Categories> getCategoriesList();

    @GET("restaurants")
    Call<RestaurantList> restaurantListCall(@Query("page") int page);

    @GET("restaurant")
    Call<RestaurantDetails> restaurantDetailsCall(@Query("restaurant_id") int restaurantId);

    @GET("items")
    Call<ListOfRestaurantItems> listOfRestaurantItems(@Query("restaurant_id") int restaurantId,
                                                      @Query("page") int page);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> restaurantReviewsList(@Query("api_token") String apiToken,
                                                  @Query("restaurant_id") int restaurantId,
                                                  @Query("page") int page);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddReview> addReviewCall(@Field("rate") int rate,
                                  @Field("comment") String comment,
                                  @Field("restaurant_id") int restaurant_id,
                                  @Field("api_token") String api_token);

    @GET("client/my-orders")
    Call<MyOrders> clientOrders(@Query("api_token") String apiToken,
                                @Query("state") String state,
                                @Query("page") int page);

    @POST("contact")
    @FormUrlEncoded
    Call<ConnectUs> connectUsCall(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("phone") String phone,
                                  @Field("type") String type,
                                  @Field("content") String content);

    @GET("offers")
    Call<Offers> getOffers(@Query("page") int page);

    @POST("restaurant/register")
    @Multipart
    Call<RestaurantRegister> restaurantRegisteration(@Part("name") RequestBody name,
                                                     @Part("email") RequestBody email,
                                                     @Part("password") RequestBody password,
                                                     @Part("password_confirmation") RequestBody password_confirmation,
                                                     @Part("phone") RequestBody phone,
                                                     @Part("address") RequestBody address,
                                                     @Part("whatsapp") RequestBody whatsApp,
                                                     @Part("region_id") RequestBody region_id,
                                                     @Part("categories[0]") List<RequestBody> categories,
                                                     @Part("delivery_period") RequestBody delivery_period,
                                                     @Part("delivery_cost") RequestBody delivery_cost,
                                                     @Part("minimum_charger") RequestBody minimum_charger,
                                                     @Part MultipartBody.Part photo,
                                                     @Part("availability") RequestBody availability);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<LoginRestaurant> restaurantLogin(@Field("email") String email,
                                          @Field("password") String password
    );

    @POST("client/login")
    @FormUrlEncoded
    Call<ClientLogin> clientLogin(@Field("email") String email,
                                  @Field("password") String password
    );

    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ClientResetPassword> clientResetPassword(@Field("email") String email);

    @POST("client/register")
    @FormUrlEncoded
    Call<ClintRegister> clintRegister(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("password_confirmation") String password_confirmation,
                                      @Field("phone") String phone,
                                      @Field("address") String address,
                                      @Field("region_id") String region_id);

    @POST("client/profile")
    @FormUrlEncoded
    Call<ClientEditProfile> getClientProfileData(@Field("api_token") String apiToken);


    @POST("client/profile")
    @FormUrlEncoded
    Call<ClientEditProfile> editClientProfileData(@Field("api_token") String apiToken,
                                                  @Field("name") String name,
                                                  @Field("phone") String phone,
                                                  @Field("email") String email,
                                                  @Field("password") String password,
                                                  @Field("password_confirmation") String password_confirmation,
                                                  @Field("address") String address,
                                                  @Field("region_id") String region_id);

    @POST("client/new-order")
    @FormUrlEncoded
    Call<ClientNewOrder> newClientOrder(@Field("restaurant_id") String restaurant_id,
                                        @Field("note") String note,
                                        @Field("address") String address,
                                        @Field("payment_method_id") String payment_method_id,
                                        @Field("phone") String phone,
                                        @Field("name") String name,
                                        @Field("api_token") String api_token,
                                        @Field("items[0]") List<String> items,
                                        @Field("quantities[0]") List<Integer> quantities,
                                        @Field("notes[0]") List<String> notes);

    @GET("restaurant/my-items")
    Call<RestaurantMyItems> restaurantItems(@Query("api_token") String api_token,
                                            @Query("page") int page);

    @POST("restaurant/new-item")
    @Multipart
    Call<RestaurantAddItem> restaurantAddItem(@Part("description") RequestBody description,
                                              @Part("price") RequestBody price,
                                              @Part("preparing_time") RequestBody preparing_time,
                                              @Part("name") RequestBody name,
                                              @Part MultipartBody.Part photo,
                                              @Part("api_token") RequestBody api_token);

    @POST("restaurant/update-item")
    @Multipart
    Call<RestaurantUpdateItem> restaurantUpdateItem(@Part("description") RequestBody description,
                                                    @Part("price") RequestBody price,
                                                    @Part("preparing_time") RequestBody preparing_time,
                                                    @Part("name") RequestBody name,
                                                    @Part MultipartBody.Part photo,
                                                    @Part("item_id")RequestBody item_id ,
                                                    @Part("api_token") RequestBody api_token);

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<RestaurantDeleteItem> restaurantDeleteItem(@Field("item_id") int item_id,
                                                    @Field("api_token") String api_token);

    @GET("restaurant/my-offers")
    Call<RestaurantMyOffersList> restaurantMyOffers(@Query("api_token") String api_token,
                                                    @Query("page") int page);

    @GET("restaurant/my-offers")
    Call<RestaurantMyOffersList> restaurantOffers(@Query("api_token") String api_token,
                                                  @Query("page") int page);

    @POST("restaurant/new-offer")
    @Multipart
    Call<RestaurantNewOffer> restaurantAddNewOffer(@Part("description") RequestBody description,
                                                   @Part("price") RequestBody price,
                                                   @Part("starting_at") RequestBody starting_at,
                                                   @Part("name") RequestBody name,
                                                   @Part MultipartBody.Part photo,
                                                   @Part("ending_at") RequestBody ending_at,
                                                   @Part("api_token") RequestBody api_token);


//    @GET("restaurants")
//    Call<RestaurantList> restaurantListCall(@Query("page") int page);
//
//    @GET("restaurant")
//    Call<RestaurantDetails> restaurantDetailsCall(@Query("restaurant_id") int restaurant_id);
//
//    @GET("items")
//    Call<ItemsFood> foodListTapCall(@Query("restaurant_id") int restaurant_id,
//                                    @Query("page") int page);
//
//    @GET("restaurant/reviews")
//    Call<RestaurantReviews> restaurantAllReviewsCALL(@Query("restaurant_id") int restaurant_id,
//                                                     @Query("page") int page);
//
//    @POST("client/restaurant/review")
//    @FormUrlEncoded
//    Call<AddReview> addReviewCall(@Field("rate") int rate,
//                                  @Field("comment") String comment,
//                                  @Field("restaurant_id") int restaurant_id,
//                                  @Field("api_token") String api_token);
//
//    @GET("offers")
//    Call<OffersList> lestOfOffers(@Query("page") int page);

}
