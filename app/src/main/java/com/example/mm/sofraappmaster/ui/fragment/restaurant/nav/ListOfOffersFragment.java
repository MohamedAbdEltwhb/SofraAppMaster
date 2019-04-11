package com.example.mm.sofraappmaster.ui.fragment.restaurant.nav;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.nav.RestaurantOffersAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerRestaurant;
import com.example.mm.sofraappmaster.data.model.general.offers.restaurant_my_offers.RestaurantMyOffersList;
import com.example.mm.sofraappmaster.data.model.general.offers.restaurant_my_offers.RestaurantOfferDatum;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.OnEndless;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfOffersFragment extends Fragment {

    @BindView(R.id.RecyclerViewListOfOffers)
    RecyclerView RecyclerViewListOfOffers;
    Unbinder unbinder;

    /* member variable for the OnEndless */
    private OnEndless mOnEndless;

    private List<RestaurantOfferDatum> mRestaurantOfferList;
    private RestaurantOffersAdapter mOffersAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mMaxPage = 0;


    public ListOfOffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_offers, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRestaurantOfferList = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getContext());
        RecyclerViewListOfOffers.setLayoutManager(mLayoutManager);
        RecyclerViewListOfOffers.setHasFixedSize(true);
        RecyclerViewListOfOffers.setItemAnimator(new DefaultItemAnimator());
        mOffersAdapter = new RestaurantOffersAdapter(getContext(), mRestaurantOfferList);
        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < mMaxPage) {
                    if (mMaxPage != 0) {
                        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {

                            /* Get Food List Use API Call */
                        }

                        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {

                            /* Get getRestaurant List Of Offers Use API Call */
                            getRestaurantListOfOffers(
                                    "OGqjF8iGLccLQqdOJ11gHDTzdwG6980twebZRnN66mOFWh2P0Qwb3UCFHboc",
                                    current_page
                            );
                        }

                    }
                }
            }
        };
        /* Adds the scroll listener to RecyclerView */
        RecyclerViewListOfOffers.addOnScrollListener(mOnEndless);

        RecyclerViewListOfOffers.setAdapter(mOffersAdapter);


        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {

            /* Get Food List Use API Call */
        }

        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {

            /* Get getRestaurant List Of Offers Use API Call */
            getRestaurantListOfOffers(
                    "OGqjF8iGLccLQqdOJ11gHDTzdwG6980twebZRnN66mOFWh2P0Qwb3UCFHboc",
                    1
            );
        }
        return view;
    }

    private void getRestaurantListOfOffers(String apiToken, int page) {
        Call<RestaurantMyOffersList> restaurantMyOffersListCall =
                RetrofitClient.getInstance().getApiServices().restaurantOffers(apiToken, page);

        restaurantMyOffersListCall.enqueue(new Callback<RestaurantMyOffersList>() {
            @Override
            public void onResponse(Call<RestaurantMyOffersList> call, Response<RestaurantMyOffersList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.showToast(getContext(), "OOOK" + response.body().getMsg());

                        mMaxPage = response.body().getData().getLastPage();
                        mRestaurantOfferList = response.body().getData().getData();
                        mRestaurantOfferList.addAll(response.body().getData().getData());
                        mOffersAdapter.notifyDataSetChanged();

//                        mRestaurantOfferList = new ArrayList<>();
//                        mLayoutManager = new LinearLayoutManager(getContext());
//                        RecyclerViewListOfOffers.setLayoutManager(mLayoutManager);
//                        RecyclerViewListOfOffers.setHasFixedSize(true);
//                        RecyclerViewListOfOffers.setItemAnimator(new DefaultItemAnimator());
//                        mOffersAdapter = new RestaurantOffersAdapter(getContext(), mRestaurantOfferList);
//                        RecyclerViewListOfOffers.setAdapter(mOffersAdapter);

                    } else {
                        HelperMethod.showToast(getContext(), "NO" + response.body().getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyOffersList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.OffersFragmentAdd)
    public void onViewClicked() {
        HelperMethod.replaceFragments(new AddNewOfferFragment(), getActivity().getSupportFragmentManager(),
                R.id.Request_food_Activity_FL, null, null);

    }
}
