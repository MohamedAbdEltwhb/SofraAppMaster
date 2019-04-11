
package com.example.mm.sofraappmaster.data.model.user.clint;

import com.example.mm.sofraappmaster.data.model.user.clint.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
