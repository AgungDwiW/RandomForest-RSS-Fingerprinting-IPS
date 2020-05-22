package com.example.ipswalker;

import android.app.Application;

public class GlobalVar extends Application {
    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    private String id_user;


}
