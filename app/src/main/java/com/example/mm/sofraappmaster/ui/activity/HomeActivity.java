package com.example.mm.sofraappmaster.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerRestaurant;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.OnBackPressedListener;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;
import com.example.mm.sofraappmaster.ui.fragment.nav.AboutAppFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.ConnectWithUsFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.OffersFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.clint_order_view_pager.ClintOrderViewPagerFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantCommunication;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.MenuTapCommunication;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.MenuTapItemDetailsFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.order.OrderBasketData;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.order.OrdersBasketFragment;
import com.example.mm.sofraappmaster.ui.fragment.restaurant.nav.AddNewProductsFragment;
import com.example.mm.sofraappmaster.ui.fragment.restaurant.nav.ListOfOffersFragment;
import com.example.mm.sofraappmaster.ui.fragment.restaurant.nav.MyProductsFragment;
import com.example.mm.sofraappmaster.ui.fragment.restaurant.nav.UpdateItem;
import com.example.mm.sofraappmaster.ui.fragment.user.LoginFragment;
import com.example.mm.sofraappmaster.ui.fragment.user.client.ClientProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.DESCRIPTION;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ID;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_ID;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_QUANTITY;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_SPECIAL;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_DESCRIPTION;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_ID;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_TIME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.TIME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys._ID;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RestaurantCommunication,
        MenuTapCommunication,
        OrderBasketData,
        UpdateItem {

    @BindView(R.id.Pack_ImageView_toolBar)
    ImageView PackImageViewToolBar;
    @BindView(R.id.Request_Food_Toolbar_Title)
    CairoRegularText RequestFoodToolbarTitle;

    private ImageView mProfileImageView;

    protected OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_nav_logo);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mProfileImageView = headerView.findViewById(R.id.imageView_Profile);
        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPrefManagerClient.getInstance(HomeActivity.this).getClientApiToken() != null) {
                    HelperMethod.replaceFragments(new ClientProfileFragment(), getSupportFragmentManager(), R.id.Request_food_Activity_FL,
                            RequestFoodToolbarTitle, getResources().getString(R.string.my_profile)
                    );
                    drawer.closeDrawers();

                }else {
                    HelperMethod.showToast(HomeActivity.this, "Bless LogIn First..");
                }
            }
        });

        int userType = SharedPrefManagerClient.getInstance(this).getUserType();
        switch (userType) {
            case 1:
                navigationView.inflateMenu(R.menu.activity_home_buy_food_drawer);

                /* Add Restaurant Fragment */
                HelperMethod.replaceFragments(new RestaurantFragment(), getSupportFragmentManager(), R.id.Request_food_Activity_FL,
                        RequestFoodToolbarTitle, getResources().getString(R.string.request_fragment_tool_bar_title)
                );
                break;

            case 2:
                navigationView.inflateMenu(R.menu.activity_home_sell_food_drawer);

                if (SharedPrefManagerRestaurant.getInstance(this).getRestaurantApiToken() != null) {

                    HelperMethod.replaceFragments(new RestaurantDetailsFragment(), getSupportFragmentManager(),
                            R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getString(R.string.foods_menu)
                    );

                }else {
                    HelperMethod.replaceFragments(new LoginFragment(), getSupportFragmentManager(), R.id.Request_food_Activity_FL,
                            RequestFoodToolbarTitle, getResources().getString(R.string.create_new_account)
                    );
                }

                break;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (onBackPressedListener != null) {
            onBackPressedListener.doBack();

        } else {
            super.onBackPressed();
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            /* Menu Items for Navigation Buy Food */
            case R.id.buy_nav_home:
                HelperMethod.replaceFragments(new RestaurantFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.request_fragment_tool_bar_title));
                break;
            case R.id.buy_nav_order:
                HelperMethod.replaceFragments(new ClintOrderViewPagerFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.order));
                break;
            case R.id.buy_nav_notification:
                break;
            case R.id.buy_nav_offer:
                HelperMethod.replaceFragments(new OffersFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.new_offers));
                break;
            case R.id.buy_nav_about:
                HelperMethod.replaceFragments(new AboutAppFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.about));
                break;
            case R.id.buy_nav_conditions:
                break;
            case R.id.buy_nav_share:
                break;
            case R.id.buy_nav_connect:
                HelperMethod.replaceFragments(new ConnectWithUsFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.connectWithUs));
                break;
            case R.id.buy_nav_logout:
                SharedPrefManagerClient.getInstance(this).clare();
                break;

            /* Menu Items for Navigation Sell Food */
            case R.id.sell_nav_home:
                HelperMethod.replaceFragments(new RestaurantDetailsFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getString(R.string.foods_menu)
                );
                break;
            case R.id.sell_nav_My_products:
                HelperMethod.replaceFragments(new MyProductsFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, "منتجاتي");
                break;
            case R.id.sell_nav_Applications:
                break;
            case R.id.sell_nav_Commission:
                break;
            case R.id.sell_nav_offer:
                HelperMethod.replaceFragments(new ListOfOffersFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.new_offers));
                break;
            case R.id.sell_nav_about:
//                if (SharedPrefManagerRestaurant.getInstance(this).getRestaurantApiToken() != null){
                    HelperMethod.replaceFragments(new AboutAppFragment(), getSupportFragmentManager(),
                            R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.about));
//                }else {
//                    HelperMethod.showToast(this, "Sign In And Try Again");
//                }
                break;

            case R.id.sel_nav_conditions:
                break;

            case R.id.sell_nav_shareApp:
                HelperMethod.sharingApp(this);
                break;

            case R.id.sel_nav_connectWithUs:
                HelperMethod.replaceFragments(new ConnectWithUsFragment(), getSupportFragmentManager(),
                        R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getResources().getString(R.string.connectWithUs));
                break;

            case R.id.sell_nav_logout:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSendItemId(int itemId) {
        RestaurantDetailsFragment foodRequestDetailsFragment = new RestaurantDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, itemId);

        foodRequestDetailsFragment.setArguments(bundle);

        HelperMethod.replaceFragments(foodRequestDetailsFragment, getSupportFragmentManager(),
                R.id.Request_food_Activity_FL, RequestFoodToolbarTitle, getString(R.string.foods_menu)
        );
    }

    @Override
    public void sentItemDetails(String photo, String name, String description, String price, String time, int id) {
        MenuTapItemDetailsFragment detailsFragment = new MenuTapItemDetailsFragment();

        Bundle bundle = new Bundle();

        bundle.putString(PHOTO, photo);
        bundle.putString(NAME, name);
        bundle.putString(DESCRIPTION, description);
        bundle.putString(PRICE, price);
        bundle.putString(TIME, time);
        bundle.putInt(_ID, id);
        detailsFragment.setArguments(bundle);

        HelperMethod.replaceFragments(detailsFragment, getSupportFragmentManager(), R.id.Request_food_Activity_FL,
                RequestFoodToolbarTitle, name);
    }


    @Override
    public void setBasketData(String photo, String name, String price, String quantity, String special, int id) {
        OrdersBasketFragment ordersBasketFragment = new OrdersBasketFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ORDER_PHOTO, photo);
        bundle.putString(ORDER_NAME, name);
        bundle.putString(ORDER_PRICE, price);
        bundle.putString(ORDER_QUANTITY, quantity);
        bundle.putString(ORDER_SPECIAL, special);
        bundle.putInt(ORDER_ID, id);

        ordersBasketFragment.setArguments(bundle);

        HelperMethod.replaceFragments(ordersBasketFragment, getSupportFragmentManager(), R.id.Request_food_Activity_FL,
                RequestFoodToolbarTitle, getResources().getString(R.string.OrdersBasket));
    }


    @Override
    public void onSendItemData(String description, String price, String preparingTime, String name, String photo, int itemId) {
        AddNewProductsFragment addNewProductsFragment = new AddNewProductsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(PRODUCT_DESCRIPTION, description);
        bundle.putString(PRODUCT_PRICE, price);
        bundle.putString(PRODUCT_TIME, preparingTime);
        bundle.putString(PRODUCT_NAME, name);
        bundle.putString(PRODUCT_PHOTO, photo);
        bundle.putInt(PRODUCT_ID, itemId);

        addNewProductsFragment.setArguments(bundle);

        HelperMethod.replaceFragments(addNewProductsFragment, getSupportFragmentManager(),
                R.id.Request_food_Activity_FL, null, null
        );
    }
}
