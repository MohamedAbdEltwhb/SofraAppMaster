package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.order;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap.OnOrderItemClick;
import com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap.OrderBasketAdapter;
import com.example.mm.sofraappmaster.data.locle.database.OrderEntity;
import com.example.mm.sofraappmaster.data.locle.database.OrderViewModel;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;
import com.example.mm.sofraappmaster.ui.fragment.user.LoginFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_ID;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_QUANTITY;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.ORDER_SPECIAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersBasketFragment extends Fragment implements OnOrderItemClick {

    @BindView(R.id.OrdersBasketRecyclerView)
    RecyclerView OrdersBasketRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.OrderTotalPrice)
    CairoRegularText OrderTotalPrice;

    private OrderViewModel orderViewModel;

    /* RecyclerView Adapter */
    private OrderBasketAdapter orderBasketAdapter;

    //var
    private String mOrderPhoto;
    private String mOrderName;
    private String mOrderPrice;
    private String mOrderQuantity;
    private String mOrderSpecial;
    private int mOrderId;

    private int mAddOne;
    public static List<String> quantity;
    //public static List<String> itemId;

    double total = 0.0;

    List<String> categoryType;

    public OrdersBasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_basket, container, false);
        unbinder = ButterKnife.bind(this, view);

        quantity = new ArrayList<>();

        /* Get Order Basket Data */
        Bundle bundle = getArguments();

        mOrderPhoto = bundle.getString(ORDER_PHOTO);
        mOrderName = bundle.getString(ORDER_NAME);
        mOrderPrice = bundle.getString(ORDER_PRICE);
        mOrderQuantity = bundle.getString(ORDER_QUANTITY);
        mOrderSpecial = bundle.getString(ORDER_SPECIAL);
        categoryType = new ArrayList<>();
        mOrderId = bundle.getInt(ORDER_ID);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        OrdersBasketRecyclerView.setLayoutManager(linearLayoutManager);
        OrdersBasketRecyclerView.setHasFixedSize(true);
        orderBasketAdapter = new OrderBasketAdapter(OrderTotalPrice);
        OrdersBasketRecyclerView.setAdapter(orderBasketAdapter);

        orderViewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
        orderViewModel.getAllOrders().observe(this, new Observer<List<OrderEntity>>() {
            @Override
            public void onChanged(@Nullable List<OrderEntity> orderEntities) {
                orderBasketAdapter.setOrders(orderEntities);



//                total = 0;
//                for (int childCount = OrdersBasketRecyclerView.getChildCount(), i = 0; i < childCount; ++i) {
//                    RecyclerView.ViewHolder holder = OrdersBasketRecyclerView.getChildViewHolder(OrdersBasketRecyclerView.getChildAt(i));
//                    CairoRegularText text = holder.itemView.findViewById(R.id.OrderTotal);
//                    HelperMethod.showToast(getContext(), text.getText().toString());
//
//                    CairoRegularText catepe = holder.itemView.findViewById(R.id.OrderQuantity);
//                    categoryType.add(catepe.getText().toString());
//
//                    total = total + Double.parseDouble(text.getText().toString());
//                }
//                OrderTotalPrice.setText(String.valueOf(total));
            }
        });

        OrderEntity orderEntity = new OrderEntity(mOrderName, mOrderPrice, mOrderPhoto, mOrderQuantity, mOrderSpecial, mOrderId);
        orderViewModel.insert(orderEntity);

        recyclerViewTouchHelper();

        orderBasketAdapter.setOnOrderItemClickListener(this);

        return view;
    }

    private void recyclerViewTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                orderViewModel.delete(orderBasketAdapter.getOrders(viewHolder.getAdapterPosition()));
                total = 0;
                HelperMethod.showToast(getContext(), "Order Deleted");
            }
        }).attachToRecyclerView(OrdersBasketRecyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAddClick(OrderEntity orderEntity) {
        int orderId = orderEntity.getId();

        total = 0;

        String Quantity = orderEntity.getQuantity();
        mAddOne = Integer.valueOf(Quantity);
        if (mAddOne == 50) {
            HelperMethod.showToast(getContext(), "You Cannot have more than 50 Order");
            return;
        }
        mAddOne++;

        OrderEntity order = new OrderEntity(mOrderName, mOrderPrice, mOrderPhoto, String.valueOf(mAddOne), mOrderSpecial, mOrderId);
        order.setId(orderId);
        orderViewModel.update(order);
    }

    @Override
    public void onSubClick(OrderEntity orderEntity) {

        int orderId = orderEntity.getId();

        total = 0;

        String Quantity = orderEntity.getQuantity();
        mAddOne = Integer.valueOf(Quantity);
        if (mAddOne == 1) {
            HelperMethod.showToast(getContext(), "You Cannot have Less than 1 Order");
            return;
        }
        mAddOne--;

        OrderEntity order = new OrderEntity(mOrderName, mOrderPrice, mOrderPhoto, String.valueOf(mAddOne), mOrderSpecial, mOrderId);
        order.setId(orderId);
        orderViewModel.update(order);
    }

    @OnClick({R.id.addMore, R.id.requestOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addMore:

                break;

            case R.id.requestOrder:
                if (SharedPrefManagerClient.getInstance(getContext()).getClientApiToken() == null) {
                    HelperMethod.replaceFragments(new LoginFragment(), Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                            R.id.Request_food_Activity_FL, null, null);

                } else {

                    for (int childCount = OrdersBasketRecyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                        RecyclerView.ViewHolder holder = OrdersBasketRecyclerView.getChildViewHolder(OrdersBasketRecyclerView.getChildAt(i));
                        CairoRegularText orderQuantity = holder.itemView.findViewById(R.id.OrderQuantity);
                        quantity.add(orderQuantity.getText().toString());

                    }

                    HelperMethod.replaceFragments(new OrderDetailsFragment(), Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                            R.id.Request_food_Activity_FL, null, null);
                }
                break;
        }
    }
}
