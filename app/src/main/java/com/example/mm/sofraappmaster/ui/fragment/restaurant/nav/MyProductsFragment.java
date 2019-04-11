package com.example.mm.sofraappmaster.ui.fragment.restaurant.nav;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.resraurant.RestaurantButtonClick;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.resraurant.TapRestaurantFoodMenuAdapter;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_delete_item.RestaurantDeleteItem;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.FoodDatum;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_my_items.RestaurantMyItems;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.OnEndless;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;

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
public class MyProductsFragment extends Fragment {


    @BindView(R.id.RecyclerViewRestaurantMyProducts)
    RecyclerView RecyclerViewRestaurantMyProducts;
    Unbinder unbinder;
    /* member variable for the OnEndless */
    private OnEndless mOnEndless;

    /* member variable for the LinearLayoutManager */
    private LinearLayoutManager mLayoutManager;


    /* RecyclerView Restaurant Adapter */
    private TapRestaurantFoodMenuAdapter mRestaurantFoodMenuAdapter;

    private UpdateItem mUpdateItem;

    //var
    private int maxPages = 0;
    private List<FoodDatum> mRestaurantFoodDataList;
    private ProgressDialog mProgressDialog;


    public MyProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_products, container, false);
        unbinder = ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getContext());
        RecyclerViewRestaurantMyProducts.setLayoutManager(mLayoutManager);
        RecyclerViewRestaurantMyProducts.setHasFixedSize(true);
        RecyclerViewRestaurantMyProducts.setItemAnimator(new DefaultItemAnimator());

        getFoodRestaurantItems();

        return view;
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

                        maxPages = response.body().getData().getLastPage();
                        mRestaurantFoodDataList.addAll(response.body().getData().getData());

                        mRestaurantFoodMenuAdapter.notifyDataSetChanged();

                    } else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());


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

    private void onEditButtonClickedCall(int position){
        String description = mRestaurantFoodDataList.get(position).getDescription();
        String price = mRestaurantFoodDataList.get(position).getPrice();
        String preparingTime = mRestaurantFoodDataList.get(position).getPreparingTime();
        String name = mRestaurantFoodDataList.get(position).getName();
        String photo = mRestaurantFoodDataList.get(position).getPhotoUrl();
        int itemId = mRestaurantFoodDataList.get(position).getId();

        mUpdateItem.onSendItemData(description, price, preparingTime, name, photo, itemId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = (FragmentActivity) context;
        mUpdateItem = (HomeActivity) activity;
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

                onEditButtonClickedCall(position);
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
        RecyclerViewRestaurantMyProducts.setAdapter(mRestaurantFoodMenuAdapter);

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
     * RecyclerView Pagination
     */
    private void setOnEndless(final int maxPage) {
        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPage) {
                    if (maxPage != 0) {

                        /* Get Restaurant Food List Use API Call */
                        getRestaurantFoodListCall("7jiWQQbN9afm8LTiO4VrmMObYz2lFig117PPCa1vxcK6VsXWy0pGWeq8MA4j", current_page);
                    }
                }
            }
        };

        /* Adds the scroll listener to RecyclerView */
        RecyclerViewRestaurantMyProducts.addOnScrollListener(mOnEndless);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.OffersFragmentAdd)
    public void onViewClicked() {
        HelperMethod.replaceFragments(new AddNewProductsFragment(), getActivity().getSupportFragmentManager(),
                R.id.Request_food_Activity_FL, null, null
        );
    }
}
