package com.example.mm.sofraappmaster.ui.fragment.nav.clint_order_view_pager;


import android.app.ProgressDialog;
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
import com.example.mm.sofraappmaster.adapter.nav.OrdersFragmentAdapter;
import com.example.mm.sofraappmaster.data.model.my_orders.MyOrders;
import com.example.mm.sofraappmaster.data.model.my_orders.OrderDatum;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.OnEndless;

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
public class CurrentOrdersFragment extends Fragment {


    @BindView(R.id.CurrentOrdersFragmentRecyclerView)
    RecyclerView CurrentOrdersFragmentRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.TapCurrentOrder_fragment_ProgressBar)
    ProgressBar TapCurrentOrderFragmentProgressBar;

    /* RecyclerView Adapter */
    private OrdersFragmentAdapter mOrdersAdapter;

    /* member variable for the LinearLayoutManager */
    private LinearLayoutManager mLayoutManager;

    /* member variable for the OnEndless */
    private OnEndless mOnEndless;

    //var
    private final boolean mOrderStatus = true;
    private List<OrderDatum> mOrderDataList;
    private int maxPages = 0;
    private ProgressDialog mProgressDialog;

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        /* Handel Back Pressed Listener Button */
//        configureBackPressedListener();

        /* Create and Show Progress Dialog */
        mProgressDialog = new ProgressDialog(getContext(), R.style.MyProgressDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);
        unbinder = ButterKnife.bind(this, view);

        mOrderDataList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        CurrentOrdersFragmentRecyclerView.setLayoutManager(mLayoutManager);
        CurrentOrdersFragmentRecyclerView.setHasFixedSize(true);
        CurrentOrdersFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /* Create Adapter */
        mOrdersAdapter = new OrdersFragmentAdapter(getContext(), mOrderDataList, mOrderStatus);

        mOnEndless = new OnEndless(mLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPages) {
                    if (maxPages != 0) {

                        /* Set ProgressBar Visible */
                        TapCurrentOrderFragmentProgressBar.setVisibility(View.VISIBLE);

                        /* Get Current Orders Use API Call */
                        getCurrentOrdersCall("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB",
                                "current", current_page);
                    }
                }
            }
        };
        /* Adds the scroll listener to RecyclerView */
        CurrentOrdersFragmentRecyclerView.addOnScrollListener(mOnEndless);

        /* Set Adapter */
        CurrentOrdersFragmentRecyclerView.setAdapter(mOrdersAdapter);

        /* Get Current Orders Use API Call */
        getCurrentOrdersCall("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", "current", 1);

        return view;
    }

    /**
     * Get CurrentOrders Use API Call
     *
     * @param apiToken
     * @param status
     * @param page
     */
    private void getCurrentOrdersCall(String apiToken, String status, int page) {
        Call<MyOrders> myOrdersCall = RetrofitClient.getInstance()
                .getApiServices().clientOrders(apiToken, status, page);

        myOrdersCall.enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(@NonNull Call<MyOrders> call, @NonNull Response<MyOrders> response) {
                if (response.body().getStatus() == 1) {

                    HelperMethod.showToast(getContext(), response.body().getMsg());

                    /* Finish the ProgressDialog */
                    mProgressDialog.dismiss();

                    maxPages = response.body().getData().getLastPage();

                    mOrderDataList.addAll(response.body().getData().getData());

                    /* Set ProgressBar In Visible */
                    TapCurrentOrderFragmentProgressBar.setVisibility(View.INVISIBLE);

                    mOrdersAdapter.notifyDataSetChanged();

                } else {

                    /* Finish the ProgressDialog */
                    mProgressDialog.dismiss();

                    HelperMethod.showToast(getContext(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyOrders> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
