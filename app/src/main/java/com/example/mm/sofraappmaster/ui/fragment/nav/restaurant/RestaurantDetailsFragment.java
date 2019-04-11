package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.ViewPagerTapsAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantDetails.Category;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantDetails.Data;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantDetails.RestaurantDetails;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.BackPressedListener;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.TapFoodCommentsFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.TapFoodMenuFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.TapRestaurantInformation;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailsFragment extends Fragment {

    @BindView(R.id.Home_Fragment_ViewPager)
    ViewPager HomeFragmentViewPager;
    Unbinder unbinder;
    @BindView(R.id.Home_Fragment_TabLayout_tabs)
    TabLayout HomeFragmentTabLayoutTabs;
    @BindView(R.id.restaurantCardDetailsImage)
    ImageView restaurantCardDetailsImage;
    @BindView(R.id.restaurantCardDetailsTvName)
    TextView restaurantCardDetailsTvName;
    @BindView(R.id.restaurantCardDetailsTvCategory)
    TextView restaurantCardDetailsTvCategory;
    @BindView(R.id.restaurantCardDetailsRatingBar)
    RatingBar restaurantCardDetailsRatingBar;
    @BindView(R.id.restaurantCardDetailsTvSates)
    TextView restaurantCardDetailsTvSates;
    @BindView(R.id.restaurantCardDetailsTvMin)
    TextView restaurantCardDetailsTvMin;
    @BindView(R.id.restaurantCardDetailsTvDeliveryFee)
    TextView restaurantCardDetailsTvDeliveryFee;

    //var
    public static int id;

    private CairoRegularText toolBarTextView;

    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Handel Back Pressed Listener Button */
        configureBackPressedListener();
    }

    /**
     * Handel Back Pressed Listener Button
     */
    public void configureBackPressedListener() {
        ((HomeActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
                    @Override
                    public void doBack() {
                        HelperMethod.replaceFragments(new RestaurantFragment(), getActivity().getSupportFragmentManager(),
                                R.id.Request_food_Activity_FL, toolBarTextView, getResources().getString(R.string.food_request_fragment_tool_bar_title)
                        );
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1){
            /* get Activity ToolBar */
            Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
            toolBarTextView = toolbar.findViewById(R.id.Request_Food_Toolbar_Title);

            /* Get OrderItem ID Clicked */
            Bundle bundle = getArguments();

            id = Objects.requireNonNull(bundle).getInt(ID);
            if (id != 0) {
                HelperMethod.showToast(getContext(), String.valueOf(Objects.requireNonNull(bundle).getInt(ID)));
                getRestaurantDetails(id);
            }


            /* Set ViewPager */
            setupClientViewPager(HomeFragmentViewPager);
            HomeFragmentTabLayoutTabs.setupWithViewPager(HomeFragmentViewPager);

        }

        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2){

            /* Set ViewPager */
            setupClientViewPager(HomeFragmentViewPager);
            HomeFragmentTabLayoutTabs.setupWithViewPager(HomeFragmentViewPager);
        }



        return view;
    }

    /**
     * Get Restaurant Details Use API Call
     *
     * @param id
     */
    private void getRestaurantDetails(int id) {
        Call<RestaurantDetails> restaurantDetailsCall = RetrofitClient
                .getInstance().getApiServices().restaurantDetailsCall(id);

        restaurantDetailsCall.enqueue(new Callback<RestaurantDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<RestaurantDetails> call, @NonNull Response<RestaurantDetails> response) {
                if (response.body().getStatus() == 1) {
                    Data data = response.body().getData();

                    StringBuilder stringCategory = new StringBuilder();
                    List<Category> categoryList = data.getCategories();

                    for (int i = 0; i < categoryList.size(); i++) {
                        stringCategory.append(categoryList.get(i).getName() + " , ");
                    }

                    Glide.with(Objects.requireNonNull(getContext()))
                            .load(data.getPhotoUrl())
                            .into(restaurantCardDetailsImage);

                    restaurantCardDetailsTvName.setText(data.getName());
                    restaurantCardDetailsRatingBar.setRating(data.getRate());
                    restaurantCardDetailsTvMin.setText(
                            getActivity().getResources().getString(R.string.deliveryFee) + data.getMinimumCharger()
                    );
                    restaurantCardDetailsTvDeliveryFee.setText(
                            getActivity().getResources().getString(R.string.deliveryFee) + data.getDeliveryCost()
                    );
                    restaurantCardDetailsTvCategory.setText(stringCategory.toString());

                    if (!data.getAvailability().equals("open")) {

                        restaurantCardDetailsTvSates.setTextColor(getContext().getResources().getColor(R.color.red_error));
                        restaurantCardDetailsTvSates.setText(" مغلق");
                        restaurantCardDetailsTvSates.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null,
                                getContext().getResources().getDrawable(R.drawable.ic_close),
                                null
                        );
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantDetails> call, @NonNull Throwable t) {

            }
        });
    }

    /* Add Fragments to Tabs */
    private void setupClientViewPager(ViewPager viewPager) {
        ViewPagerTapsAdapter adapter = new ViewPagerTapsAdapter(getChildFragmentManager());
        adapter.addFragment(new TapFoodMenuFragment(), getResources().getString(R.string.foods_menu));
        adapter.addFragment(new TapFoodCommentsFragment(), getResources().getString(R.string.comment_tap));
        adapter.addFragment(new TapRestaurantInformation(), getResources().getString(R.string.information_tap));

        viewPager.setAdapter(adapter);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
