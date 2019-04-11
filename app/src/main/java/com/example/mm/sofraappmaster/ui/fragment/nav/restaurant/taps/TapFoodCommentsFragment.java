package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.TapFoodCommentsFragmentAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantReviews.RestaurantReviewData;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantReviews.RestaurantReviews;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.OnEndless;
import com.example.mm.sofraappmaster.ui.custom.custom_dialog.CustomAddCommentDialog;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;

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
public class TapFoodCommentsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.commentFragmentRv)
    RecyclerView commentFragmentRv;
    @BindView(R.id.TapComments_fragment_ProgressBar)
    ProgressBar TapCommentsFragmentProgressBar;

    //var
    private int maxPages = 0;
    private List<RestaurantReviewData> mRestaurantReviewData;

    /* RecyclerView Adapter */
    private TapFoodCommentsFragmentAdapter mTapFoodCommentsFragmentAdapter;

    /* member variable for the LinearLayoutManager */
    private LinearLayoutManager mLayoutManager;

    /* member variable for the OnEndless */
    private OnEndless mOnEndless;


    public TapFoodCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tap_food_comments, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRestaurantReviewData = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        commentFragmentRv.setLayoutManager(mLayoutManager);
        commentFragmentRv.setHasFixedSize(true);
        commentFragmentRv.setItemAnimator(new DefaultItemAnimator());

        /* Create Adapter */
        mTapFoodCommentsFragmentAdapter = new TapFoodCommentsFragmentAdapter(getContext(), mRestaurantReviewData);
        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPages) {
                    if (maxPages != 0) {

                        /* Set ProgressBar Visible */
                        TapCommentsFragmentProgressBar.setVisibility(View.VISIBLE);

                        /* Get All Restaurant Reviews Use API Call */
                        getRestaurantReviewsCall(SharedPrefManagerClient.getInstance(getContext()).getClientApiToken(),
                                RestaurantDetailsFragment.id, current_page);
                    }
                }
            }
        };
        /* Adds the scroll listener to RecyclerView */
        commentFragmentRv.addOnScrollListener(mOnEndless);

        /* Set Adapter */
        commentFragmentRv.setAdapter(mTapFoodCommentsFragmentAdapter);

        /* Get All Restaurant Reviews Use API Call */
        getRestaurantReviewsCall(SharedPrefManagerClient.getInstance(getContext()).getClientApiToken(),
                RestaurantDetailsFragment.id, 1);

        return view;
    }

    /**
     * Get All Restaurant Reviews Use API Call
     */
    private void getRestaurantReviewsCall(String apiToken, int id, int page) {
        Call<RestaurantReviews> restaurantReviewsCall = RetrofitClient
                .getInstance().getApiServices().restaurantReviewsList(apiToken, id, page);

        restaurantReviewsCall.enqueue(new Callback<RestaurantReviews>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantReviews> call, @NonNull Response<RestaurantReviews> response) {
                if (response.body().getStatus() == 1) {

                    maxPages = response.body().getData().getLastPage();
                    mRestaurantReviewData.addAll(response.body().getData().getData());

                    /* Set ProgressBar In Visible */
                    TapCommentsFragmentProgressBar
                            .setVisibility(View.INVISIBLE);

                    mTapFoodCommentsFragmentAdapter.notifyDataSetChanged();
                } else {
                    HelperMethod.showToast(getContext(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantReviews> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.commentFragmentBtnAddReviews)
    public void onViewClicked() {

        /* Show Dialog */
        new CustomAddCommentDialog(getContext()).showCustomDialog();
    }
}
