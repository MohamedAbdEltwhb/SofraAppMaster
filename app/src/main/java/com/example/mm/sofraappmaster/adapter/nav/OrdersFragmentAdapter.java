package com.example.mm.sofraappmaster.adapter.nav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.my_orders.OrderDatum;
import com.example.mm.sofraappmaster.data.model.my_orders.OrderItem;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersFragmentAdapter extends RecyclerView.Adapter<OrdersFragmentAdapter.OrdersFragmentHolder> {

    private Context mContext;
    private List<OrderDatum> mOrderList;
    private boolean mStatus;

    public OrdersFragmentAdapter(Context mContext, List<OrderDatum> mOrderList, boolean mStatus) {
        this.mContext = mContext;
        this.mOrderList = mOrderList;
        this.mStatus = mStatus;
    }

    @NonNull
    @Override
    public OrdersFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersFragmentHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_clint_order, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrdersFragmentHolder holder, int position) {
        OrderDatum orderDatum = mOrderList.get(position);

        holder.orderCardTVName.setText(orderDatum.getRestaurant().getName());
        holder.orderCardTVDelivery.setText(orderDatum.getDeliveryCost());

        List<OrderItem> orderItems = orderDatum.getItems();

        for (OrderItem item : orderItems) {
            Glide.with(mContext).load(item.getPhotoUrl()).into(holder.orderCardImageView);
            holder.orderCardTVPrice.setText(item.getPrice());
            holder.orderCardTVTotalPrice.setText(item.getPrice());

            if (mStatus) {
                holder.LinerCardItemButton.setVisibility(View.INVISIBLE);
                holder.textViewOrderNumberWithBtn.setVisibility(View.INVISIBLE);
                holder.orderCardTVOrderNumber.setVisibility(View.VISIBLE);

                holder.orderCardTVOrderNumber.setText(mContext.getResources().getString(R.string.order_number) + item.getPivot().getOrderId());

            } else {
                holder.LinerCardItemButton.setVisibility(View.VISIBLE);
                holder.textViewOrderNumberWithBtn.setVisibility(View.VISIBLE);
                holder.orderCardTVOrderNumber.setVisibility(View.INVISIBLE);

                holder.textViewOrderNumberWithBtn.setText(mContext.getResources().getString(R.string.order_number) + item.getPivot().getOrderId());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class OrdersFragmentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.orderCardImageView)
        ImageView orderCardImageView;
        @BindView(R.id.orderCardTVName)
        CairoRegularText orderCardTVName;
        @BindView(R.id.orderCardTVPrice)
        CairoRegularText orderCardTVPrice;
        @BindView(R.id.orderCardTVDelivery)
        CairoRegularText orderCardTVDelivery;
        @BindView(R.id.orderCardTVTotalPrice)
        CairoRegularText orderCardTVTotalPrice;
        @BindView(R.id.orderCardTVOrderNumber)
        CairoRegularText orderCardTVOrderNumber;
        @BindView(R.id.textView_OrderNumber_with_btn)
        CairoRegularText textViewOrderNumberWithBtn;
        @BindView(R.id.frameLayoutForText)
        FrameLayout frameLayoutForText;
        @BindView(R.id.LinerCardItemButton)
        LinearLayout LinerCardItemButton;

        @BindView(R.id.LikeButtonCairoRegularText)
        CairoRegularText LikeButtonCairoRegularText;
        @BindView(R.id.UnLikeButtonCairoRegularText)
        CairoRegularText UnLikeButtonCairoRegularText;

        private final View view;

        public OrdersFragmentHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
