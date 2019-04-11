
package com.example.mm.sofraappmaster.data.model.user.login_restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private RestaurantLoginUser user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public RestaurantLoginUser getUser() {
        return user;
    }

    public void setUser(RestaurantLoginUser user) {
        this.user = user;
    }

}
