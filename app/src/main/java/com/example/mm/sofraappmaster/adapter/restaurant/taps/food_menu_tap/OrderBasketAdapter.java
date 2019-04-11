package com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.database.OrderEntity;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderBasketAdapter extends RecyclerView.Adapter<OrderBasketAdapter.OrderBasketHelper> {

    private List<OrderEntity> mOrderEntityList = new ArrayList<>();

    private OnOrderItemClick onOrderItemClick;
    private CairoRegularText cairoRegularText;

    public OrderBasketAdapter(CairoRegularText cairoRegularText) {
        this.cairoRegularText = cairoRegularText;
    }

    @NonNull
    @Override
    public OrderBasketHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderBasketHelper(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_basket, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderBasketHelper holder, int position) {
        OrderEntity orderEntity = mOrderEntityList.get(position);

        //HelperMethod.glideLode(p, holder.OrderImage, orderEntity.getPhotoUrl());
        holder.OrderTitle.setText(orderEntity.getName());
        holder.OrderQuantity.setText(String.valueOf(orderEntity.getQuantity()));
        holder.OrderPrice.setText(String.valueOf(orderEntity.getPrice()));
        holder.OrderTotal.setText(String.valueOf(calculationTotalPrice(orderEntity.getQuantity(), orderEntity.getPrice())));


    }

    /**
     * Calculation of Quantity and Price
     */
    private String calculationTotalPrice(String orderQuantity, String orderPrice) {
        int quantity = Integer.valueOf(orderQuantity);
        double price = Double.valueOf(orderPrice);
        double totalPrice = price * quantity;

        return String.valueOf(totalPrice);
    }

    public void setOrders(List<OrderEntity> orderEntities) {
        this.mOrderEntityList = orderEntities;
        notifyDataSetChanged();
    }

    public OrderEntity getOrders(int position) {
        return mOrderEntityList.get(position);
    }

    @Override
    public int getItemCount() {
        return mOrderEntityList.size();
    }

    public class OrderBasketHelper extends RecyclerView.ViewHolder {


        @BindView(R.id.OrderImage)
        ImageView OrderImage;
        @BindView(R.id.OrderTitle)
        CairoRegularText OrderTitle;
        @BindView(R.id.OrderAddOne)
        ImageButton OrderAddOne;
        @BindView(R.id.OrderQuantity)
        CairoRegularText OrderQuantity;
        @BindView(R.id.OrderSubOne)
        ImageButton OrderSubOne;
        @BindView(R.id.OrderPrice)
        CairoRegularText OrderPrice;
        @BindView(R.id.OrderTotal)
        CairoRegularText OrderTotal;

        public final View view;

        public OrderBasketHelper(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);

            OrderAddOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onOrderItemClick != null && position != RecyclerView.NO_POSITION) {
                        onOrderItemClick.onAddClick(mOrderEntityList.get(position));
                    }
                }
            });

            OrderSubOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onOrderItemClick != null && position != RecyclerView.NO_POSITION) {
                        onOrderItemClick.onSubClick(mOrderEntityList.get(position));
                    }
                }
            });
        }

    }

    public void setOnOrderItemClickListener(OnOrderItemClick onOrderClick) {
        this.onOrderItemClick = onOrderClick;
    }
}
