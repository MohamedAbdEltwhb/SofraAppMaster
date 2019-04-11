package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.CitesSpinnerAdapter;
import com.example.mm.sofraappmaster.adapter.restaurant.restaurant_list.OnRestaurantClickListener;
import com.example.mm.sofraappmaster.adapter.restaurant.restaurant_list.RestaurantListAdapter;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.Cites;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.CitesObject;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantList.RestaurantList;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantList.RestaurantListData;
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
public class RestaurantFragment extends Fragment {


    @BindView(R.id.Spinner_Request_food_Activity)
    Spinner SpinnerRequestFoodActivity;
    @BindView(R.id.RecyclerView_Fragment_food_request)
    RecyclerView RecyclerViewFragmentFoodRequest;
    Unbinder unbinder;


    /* Interface to Communication Between {RestaurantFragment & RestaurantDetailsFragment} */
    private RestaurantCommunication mFoodRequestCommunication;

    /* RecyclerView Adapter */
    private RestaurantListAdapter mListAdapter;

    /* member variable for the LinearLayoutManager */
    private LinearLayoutManager mLayoutManager;

    /* member variable for the OnEndless */
    private OnEndless mOnEndless;

    //var
    private int maxPages = 0;
    private ProgressDialog mProgressDialog;
    private List<RestaurantListData> mRestaurantList;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        mFoodRequestCommunication = (RestaurantCommunication) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Handel Back Pressed Listener Button */
        configureBackPressedListener();
    }

    @Override
    public void onStart() {
        super.onStart();

        /* Create and Show Progress Dialog */
        mProgressDialog = new ProgressDialog(getContext(), R.style.MyProgressDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    /**
     * Configure Back Pressed Listener Button
     */
    public void configureBackPressedListener() {
        ((HomeActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
                    @Override
                    public void doBack() {
                        getActivity().finish();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Get Spinner List Cites Use API Call */
        getCitiesSpinner();

        mRestaurantList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        RecyclerViewFragmentFoodRequest.setLayoutManager(mLayoutManager);
        RecyclerViewFragmentFoodRequest.setHasFixedSize(true);
        RecyclerViewFragmentFoodRequest.setItemAnimator(new DefaultItemAnimator());

        /* Create Adapter */
        mListAdapter = new RestaurantListAdapter(getContext(), mRestaurantList, new OnRestaurantClickListener() {
            @Override
            public void setOnRestaurantItemClickListener(int position) {
                int getID = mRestaurantList.get(position).getId();
                mFoodRequestCommunication.onSendItemId(getID);
            }
        });

        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPages) {
                    if (maxPages != 0){

                        HelperMethod.showToast(getContext(), "current page: " + current_page);

                        /* Get Food Restaurant Request Use API Call */
                        getRestaurantRequest(current_page);
                    }
                }
            }
        };
        /* Adds the scroll listener to RecyclerView */
        RecyclerViewFragmentFoodRequest.addOnScrollListener(mOnEndless);

        /* Set Adapter */
        RecyclerViewFragmentFoodRequest.setAdapter(mListAdapter);

        /* Get Food Restaurant Request Use API Call */
        getRestaurantRequest(1);



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Get Food Restaurant Request Use API Call
     * @param page
     * */
    private void getRestaurantRequest(int page){
        Call<RestaurantList> restaurantListCall = RetrofitClient.getInstance().getApiServices().restaurantListCall(page);
        restaurantListCall.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.body().getStatus() == 1){

                    HelperMethod.showToast(getContext(), ""+ response.body().getData().getCurrentPage());

                    /* Finish the ProgressDialog */
                    mProgressDialog.dismiss();

                    maxPages = response.body().getData().getLastPage();
                    mRestaurantList.addAll(response.body().getData().getData());
                    mListAdapter.notifyDataSetChanged();

                }else {
                    /* Finish the ProgressDialog */
                    mProgressDialog.dismiss();

                    HelperMethod.showToast(getContext(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantList> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Get Spinner List Cites Use API Call
     */
    private void getCitiesSpinner() {
        Call<Cites> citesCall = RetrofitClient.getInstance().getApiServices().getCitesList();
        citesCall.enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(@NonNull Call<Cites> call, @NonNull Response<Cites> response) {

                if (response.body().getStatus() == 1) {
                    CitesSpinnerAdapter citesSpinnerAdapter = new CitesSpinnerAdapter(getContext(), response.body().getData().getData());
                    SpinnerRequestFoodActivity.setAdapter(citesSpinnerAdapter);
                    SpinnerRequestFoodActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            CitesObject citesObject = (CitesObject) parent.getSelectedItem();
                            //Toast.makeText(getContext(), citesObject.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    HelperMethod.showToast(getContext(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cites> call, @NonNull Throwable t) {

            }
        });
    }
}
