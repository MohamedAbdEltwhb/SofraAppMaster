package com.example.mm.sofraappmaster.ui.fragment.nav;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.nav.OffersAdapter;
import com.example.mm.sofraappmaster.adapter.nav.OffesAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.model.general.offers.Offers;
import com.example.mm.sofraappmaster.data.model.general.offers.OffersDatum;
import com.example.mm.sofraappmaster.data.model.general.offers.restaurant_my_offers.RestaurantOfferDatum;
import com.example.mm.sofraappmaster.data.model.general.offers.restaurant_my_offers.RestaurantMyOffersList;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.OnEndless;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.BackPressedListener;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends Fragment {


    @BindView(R.id.OfferRecyclerView)
    RecyclerView OfferRecyclerView;
    @BindView(R.id.Offer_ProgressBar)
    ProgressBar OfferProgressBar;
    Unbinder unbinder;

    /* RecyclerView Adapter */
    private OffersAdapter mOffersAdapter;
    private OffesAdapter mOfferAdapter;

    /* member variable for the LinearLayoutManager */
    private LinearLayoutManager mLayoutManager;

    /* member variable for the OnEndless */
    private OnEndless mOnEndless;

    //var
    private List<OffersDatum> mOffersList;
    private int maxPages = 0;

    private List<RestaurantOfferDatum> mOfferList;

    public OffersFragment() {
        // Required empty public constructor
    }

    /**
     * Configure Back Pressed Listener Button
     */
    public void configureBackPressedListener() {
        ((HomeActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
                    @Override
                    public void doBack() {
                        super.doBack();
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Handel Back Pressed Listener Button */
        configureBackPressedListener();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        unbinder = ButterKnife.bind(this, view);

        mOffersList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        OfferRecyclerView.setLayoutManager(mLayoutManager);
        OfferRecyclerView.setHasFixedSize(true);
        OfferRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /* Create Adapter */
        mOffersAdapter = new OffersAdapter(getContext(), mOffersList);

        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPages) {
                    if (maxPages != 0) {

                        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {
                            /* Set ProgressBar Visible */
                            OfferProgressBar.setVisibility(View.VISIBLE);

                            /* Get List Of Offers Use API Call */
                            getListOfOffers(current_page);
                        }

                        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {
                            /* Set ProgressBar Visible */
                            OfferProgressBar.setVisibility(View.VISIBLE);

                            /* Get List Of Offers Use API Call */
                            getRestaurantMyOffers("OGqjF8iGLccLQqdOJ11gHDTzdwG6980twebZRnN66mOFWh2P0Qwb3UCFHboc", current_page);
                        }
                    }
                }
            }
        };
        /* Adds the scroll listener to RecyclerView */
        OfferRecyclerView.addOnScrollListener(mOnEndless);

        /* Set Adapter */
        OfferRecyclerView.setAdapter(mOffersAdapter);

        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {
            /* Get List Of Offers Use API Call */
            getListOfOffers(1);
        }

        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {
            mOfferList = new ArrayList<>();
            mLayoutManager = new LinearLayoutManager(getContext());
            OfferRecyclerView.setLayoutManager(mLayoutManager);
            OfferRecyclerView.setHasFixedSize(true);
            OfferRecyclerView.setItemAnimator(new DefaultItemAnimator());

            /* Create Adapter */
            mOfferAdapter = new OffesAdapter(getContext(), mOfferList);

            mOnEndless = new OnEndless(mLayoutManager, 1) {
                @Override
                public void onLoadMore(int current_page) {
                    if (current_page < maxPages) {
                        if (maxPages != 0) {

                            if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {
                                /* Set ProgressBar Visible */
                                OfferProgressBar.setVisibility(View.VISIBLE);

                                /* Get List Of Offers Use API Call */
                                getListOfOffers(current_page);
                            }

                            if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {
                                /* Set ProgressBar Visible */
                                OfferProgressBar.setVisibility(View.VISIBLE);

                                /* Get List Of Offers Use API Call */
                                getRestaurantMyOffers("OGqjF8iGLccLQqdOJ11gHDTzdwG6980twebZRnN66mOFWh2P0Qwb3UCFHboc", current_page);
                            }
                        }
                    }
                }
            };
            /* Adds the scroll listener to RecyclerView */
            OfferRecyclerView.addOnScrollListener(mOnEndless);

            /* Set Adapter */
            OfferRecyclerView.setAdapter(mOfferAdapter);
            /* Get List Of Offers Use API Call */
            getRestaurantMyOffers("OGqjF8iGLccLQqdOJ11gHDTzdwG6980twebZRnN66mOFWh2P0Qwb3UCFHboc", 1);
        }

        return view;
    }

    private void getRestaurantMyOffers(String apiToken, int page) {
        Call<RestaurantMyOffersList> restaurantMyOffersCall = RetrofitClient.getInstance().getApiServices().restaurantMyOffers(apiToken, page);
        restaurantMyOffersCall.enqueue(new Callback<RestaurantMyOffersList>() {
            @Override
            public void onResponse(Call<RestaurantMyOffersList> call, Response<RestaurantMyOffersList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.showToast(getContext(), response.body().getMsg());

                        /* Set ProgressBar In Visible */
                        OfferProgressBar.setVisibility(View.INVISIBLE);

                        maxPages = response.body().getData().getLastPage();
                        mOfferList.addAll(response.body().getData().getData());
                        mOffersAdapter.notifyDataSetChanged();

                    } else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());

                        /* Set ProgressBar In Visible */
                        OfferProgressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    /* Set ProgressBar In Visible */
                    OfferProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyOffersList> call, Throwable t) {

                /* Set ProgressBar In Visible */
                OfferProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    /**
     * Get List Of Offers Use API Call
     */
    private void getListOfOffers(int page) {

        Call<Offers> offersCall = RetrofitClient.getInstance()
                .getApiServices().getOffers(page);

        offersCall.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(@NonNull Call<Offers> call, @NonNull Response<Offers> response) {
                if (response.body().getStatus() == 1) {

//                    HelperMethod.showToast(getContext(), response.message());
//
//                    maxPages = response.body().getData().getLastPage();
//                    mOffersList.addAll(response.body().getData().getData());
//
//                    /* Set ProgressBar In Visible */
//                    OfferProgressBar.setVisibility(View.INVISIBLE);
//
//                    mOffersAdapter.notifyDataSetChanged();
                    mOffersList = response.body().getData().getData();
                    mLayoutManager = new LinearLayoutManager(getContext());
                    OfferRecyclerView.setLayoutManager(mLayoutManager);
                    OfferRecyclerView.setHasFixedSize(true);
                    OfferRecyclerView.setItemAnimator(new DefaultItemAnimator());

                    /* Create Adapter */
                    mOffersAdapter = new OffersAdapter(getContext(), mOffersList);

                    /* Set Adapter */
                    OfferRecyclerView.setAdapter(mOffersAdapter);

                } else {
                    HelperMethod.showToast(getContext(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Offers> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
