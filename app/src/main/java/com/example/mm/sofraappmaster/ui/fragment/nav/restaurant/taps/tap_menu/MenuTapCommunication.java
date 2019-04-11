package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu;

public interface MenuTapCommunication {

    void sentItemDetails(String photo,
                         String name,
                         String description,
                         String price,
                         String time,
                         int id);
}
