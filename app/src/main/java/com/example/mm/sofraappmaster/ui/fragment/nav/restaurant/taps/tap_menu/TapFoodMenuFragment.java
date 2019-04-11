package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap.OnFoodClickListener;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap.TapFoodMenuAdapter;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.resraurant.RestaurantButtonClick;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.resraurant.TapRestaurantFoodMenuAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.model.restaurant.listOfRestaurantItems.ItemsFoodData;
import com.example.mm.sofraappmaster.data.model.restaurant.listOfRestaurantItems.ListOfRestaurantItems;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_delete_item.RestaurantDeleteItem;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.FoodDatum;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_my_items.RestaurantMyItems;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.OnEndless;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TapFoodMenuFragment extends Fragment {


    @BindView(R.id.FoodMenuFragmentRecyclerView)
    RecyclerView FoodMenuFragmentRecyclerView;
    Unbinder unbinder;

    /* member variable for the OnEndless */
    private OnEndless mOnEndless;

    /* member variable for the LinearLayoutManager */
    private LinearLayoutManager mLayoutManager;

    /* RecyclerView Client Adapter */
    private TapFoodMenuAdapter mTapFoodMenuAdapter;

    /* RecyclerView Restaurant Adapter */
    private TapRestaurantFoodMenuAdapter mRestaurantFoodMenuAdapter;

    private MenuTapCommunication mMenuTapCommunication;

    //var
    private int maxPages = 0;
    private List<ItemsFoodData> mFoodDataList;
    private List<FoodDatum> mRestaurantFoodDataList;
    private ProgressDialog mProgressDialog;

    public TapFoodMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Create and Show Progress Dialog */
        mProgressDialog = new ProgressDialog(getContext(), R.style.MyProgressDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        FragmentActivity activity = (FragmentActivity) context;
        mMenuTapCommunication = (HomeActivity) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tap_food_menu, container, false);
        unbinder = ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getContext());
        FoodMenuFragmentRecyclerView.setLayoutManager(mLayoutManager);
        FoodMenuFragmentRecyclerView.setHasFixedSize(true);
        FoodMenuFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {
            getFoodClientItems();
        }
        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {
            getFoodRestaurantItems();
        }

        return view;
    }

    /**
     * RecyclerView Pagination
     */
    private void setOnEndless(final int maxPage) {
        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPage) {
                    if (maxPage != 0) {

                        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1) {

                            /* Get Food List Use API Call */
                            getClientFoodListCall(RestaurantDetailsFragment.id, current_page);
                        }

                        if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2) {

                            /* Get Restaurant Food List Use API Call */
                            getRestaurantFoodListCall("7jiWQQbN9afm8LTiO4VrmMObYz2lFig117PPCa1vxcK6VsXWy0pGWeq8MA4j", current_page);
                        }

                    }
                }
            }
        };

        /* Adds the scroll listener to RecyclerView */
        FoodMenuFragmentRecyclerView.addOnScrollListener(mOnEndless);
    }

    /**
     * Get Food Client Items
     */
    private void getFoodClientItems() {
        mFoodDataList = new ArrayList<>();

        /* Create Adapter */
        mTapFoodMenuAdapter = new TapFoodMenuAdapter(getContext(), mFoodDataList, new OnFoodClickListener() {
            @Override
            public void setOnFoodItemClickListener(int position) {
                String photo = mFoodDataList.get(position).getPhotoUrl();
                String name = mFoodDataList.get(position).getName();
                String description = mFoodDataList.get(position).getDescription();
                String price = mFoodDataList.get(position).getPrice();
                String time = mFoodDataList.get(position).getPreparingTime();
                int id = mFoodDataList.get(position).getId();

                mMenuTapCommunication.sentItemDetails(photo, name, description, price, time, id);
            }
        });

        /* RecyclerView Pagination */
        setOnEndless(maxPages);

        /* Set Adapter */
        FoodMenuFragmentRecyclerView.setAdapter(mTapFoodMenuAdapter);

        /* Get Food List Use API Call */
        getClientFoodListCall(RestaurantDetailsFragment.id, 1);

    }

    /**
     * Get Food List Use API Call
     *
     * @param id
     * @param page
     */
    private void getClientFoodListCall(int id, int page) {
        Call<ListOfRestaurantItems> itemsFoodCall = RetrofitClient
                .getInstance().getApiServices().listOfRestaurantItems(id, page);

        itemsFoodCall.enqueue(new Callback<ListOfRestaurantItems>() {
            @Override
            public void onResponse(@NonNull Call<ListOfRestaurantItems> call, @NonNull Response<ListOfRestaurantItems> response) {
                if (response.body().getStatus() == 1) {

                    /* Finish the ProgressDialog */
                    mProgressDialog.dismiss();

                    maxPages = response.body().getData().getLastPage();
                    mFoodDataList.addAll(response.body().getData().getData());

                    mTapFoodMenuAdapter.notifyDataSetChanged();

                } else {
                    /* Finish the ProgressDialog */
                    mProgressDialog.dismiss();

                    HelperMethod.showToast(getContext(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListOfRestaurantItems> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Get Food Restaurant Items
     */
    private void getFoodRestaurantItems() {
        mRestaurantFoodDataList = new ArrayList<>();
        mRestaurantFoodMenuAdapter = new TapRestaurantFoodMenuAdapter(getContext(), mRestaurantFoodDataList);
        mRestaurantFoodMenuAdapter.setOnRestaurantButtonClickListener(new RestaurantButtonClick() {
            @Override
            public void onEditButtonClicked(int position) {
                //onEditButtonClickedCall();
            }

            @Override
            public void onDeleteButtonClicked(int position) {
                mRestaurantFoodDataList.get(position).getId();

                onDeleteButtonClickedCall(
                        mRestaurantFoodDataList.get(position).getId(),
                        "7jiWQQbN9afm8LTiO4VrmMObYz2lFig117PPCa1vxcK6VsXWy0pGWeq8MA4j"
                );
            }
        });

        /* RecyclerView Pagination */
        setOnEndless(maxPages);

        /* Set Adapter */
        FoodMenuFragmentRecyclerView.setAdapter(mRestaurantFoodMenuAdapter);

        /* Get Food List Use API Call */
        getRestaurantFoodListCall("7jiWQQbN9afm8LTiO4VrmMObYz2lFig117PPCa1vxcK6VsXWy0pGWeq8MA4j", 1);
    }

    /**
     * Delete Restaurant Food Item Use API Call
     */
    private void onDeleteButtonClickedCall(int itemId, String apiToken) {

        Call<RestaurantDeleteItem> restaurantDeleteItemCall =
                RetrofitClient.getInstance().getApiServices().restaurantDeleteItem(itemId, apiToken);
        restaurantDeleteItemCall.enqueue(new Callback<RestaurantDeleteItem>() {
            @Override
            public void onResponse(Call<RestaurantDeleteItem> call, Response<RestaurantDeleteItem> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.showToast(getContext(), response.body().getMsg());


                    } else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RestaurantDeleteItem> call, Throwable t) {

            }
        });
    }

    /**
     * Get Restaurant Food List Use API Call
     *
     * @param apiToken
     * @param page
     */
    private void getRestaurantFoodListCall(String apiToken, int page) {
        Call<RestaurantMyItems> restaurantMyItemsCall =
                RetrofitClient.getInstance().getApiServices().restaurantItems(apiToken, page);

        restaurantMyItemsCall.enqueue(new Callback<RestaurantMyItems>() {
            @Override
            public void onResponse(Call<RestaurantMyItems> call, Response<RestaurantMyItems> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.showToast(getContext(), response.body().getMsg());

                        /* Finish the ProgressDialog */
                        mProgressDialog.dismiss();

                        maxPages = response.body().getData().getLastPage();
                        mRestaurantFoodDataList.addAll(response.body().getData().getData());

                        mRestaurantFoodMenuAdapter.notifyDataSetChanged();

                    } else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());

                        /* Finish the ProgressDialog */
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyItems> call, Throwable t) {
                HelperMethod.showToast(getContext(), t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
