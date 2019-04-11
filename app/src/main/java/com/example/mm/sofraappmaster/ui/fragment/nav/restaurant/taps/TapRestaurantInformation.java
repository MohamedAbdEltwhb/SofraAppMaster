package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantDetails.Data;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantDetails.RestaurantDetails;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TapRestaurantInformation extends Fragment {


    @BindView(R.id.restaurantInformationFragTvState)
    TextView restaurantInformationFragTvState;
    @BindView(R.id.restaurantInformationFragTvStateSwitch)
    Switch restaurantInformationFragTvStateSwitch;
    @BindView(R.id.restaurantInformationFragTvCity)
    TextView restaurantInformationFragTvCity;
    @BindView(R.id.restaurantInformationFragTvStreet)
    TextView restaurantInformationFragTvStreet;
    @BindView(R.id.restaurantInformationFragTvMinimum)
    TextView restaurantInformationFragTvMinimum;
    @BindView(R.id.restaurantInformationFragTvDelivery)
    TextView restaurantInformationFragTvDelivery;
    @BindView(R.id.restaurantInformationFragBtnSave)
    Button restaurantInformationFragBtnSave;
    Unbinder unbinder;

    //var
    private int id;

    public TapRestaurantInformation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tap_restaurant_information, container, false);
        unbinder = ButterKnife.bind(this, view);

        restaurantInformationFragTvStateSwitch.setVisibility(View.GONE);
        restaurantInformationFragTvState.setVisibility(View.GONE);
        restaurantInformationFragBtnSave.setVisibility(View.GONE);

        /* Get OrderItem ID Clicked */
        id = RestaurantDetailsFragment.id;
        if (id != 0) {
            getRestaurantDetails(id);
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

                    restaurantInformationFragTvState.setText( data.getAvailability());
                    restaurantInformationFragTvCity.setText( data.getRegion().getCity().getName());
                    restaurantInformationFragTvStreet.setText( data.getRegion().getName());
                    restaurantInformationFragTvDelivery.setText( data.getDeliveryCost());
                    restaurantInformationFragTvMinimum.setText( data.getMinimumCharger() + " ريال" );
                    //if (user.equals( "user" )) {
                    restaurantInformationFragTvState.setVisibility( View.VISIBLE );
                    //  }
//                    else {
//                        restaurantInformationFragTvStateSwitch.setVisibility( View.VISIBLE);
//                        restaurantInformationFragBtnSave.setVisibility( View.VISIBLE );
//                        if (!response.body().getData().getAvailability().equals( "open" )) {
//                            restaurantInformationFragTvStateSwitch.setText( response.body().getData().getAvailability() );
//                            restaurantInformationFragTvStateSwitch.setTextColor( getActivity().getResources().getColor( R.color.red_error ) );
//
//                        }
//                        restaurantInformationFragTvStateSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                if (isChecked == true) {
//                                    restaurantInformationFragTvStateSwitch.setText( "open" );
//                                    restaurantInformationFragTvStateSwitch.setTextColor( getActivity().getResources().getColor( R.color.colorGreenAccent3 ) );
//
//                                    stateRest = "open";
//                                } else {
//                                    restaurantInformationFragTvStateSwitch.setText( "close" );
//                                    restaurantInformationFragTvStateSwitch.setTextColor( getActivity().getResources().getColor( R.color.red_error ) );
//                                    stateRest = "closed";
//
//                                }
//                            }
//                        } );
//                        if (data.getAvailability().equals( "open" )) {
//                            restaurantInformationFragTvStateSwitch.setChecked( true );
//                            restaurantInformationFragTvStateSwitch.setText( data.getAvailability() );
//                        } else {
//                            restaurantInformationFragTvStateSwitch.setChecked( false );
//                            restaurantInformationFragTvStateSwitch.setText( data.getAvailability() );
//                        }
//                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantDetails> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
