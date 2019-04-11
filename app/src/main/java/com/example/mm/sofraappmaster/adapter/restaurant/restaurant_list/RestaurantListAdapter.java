package com.example.mm.sofraappmaster.adapter.restaurant.restaurant_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantList.Category;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantList.RestaurantListData;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {


    private Context mContext;
    private List<RestaurantListData> restaurantsList;
    private OnRestaurantClickListener mOnRestaurantClickListener;

    public RestaurantListAdapter(Context mContext, List<RestaurantListData> restaurantsList,
                                 OnRestaurantClickListener mOnRestaurantClickListener) {
        this.mContext = mContext;
        this.restaurantsList = restaurantsList;
        this.mOnRestaurantClickListener = mOnRestaurantClickListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_food_restaunt, parent, false),
                mOnRestaurantClickListener
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantListData restaurantsData = restaurantsList.get(position);

        Glide.with(mContext)
                .load(restaurantsData.getPhotoUrl())
                .into(holder.restaurantCardImage);

        holder.restaurantCardTvName.setText(restaurantsData.getName());
        holder.restaurantCardRatingBar.setRating(restaurantsData.getRate());
        holder.restaurantCardTvDeliveryFee.setText(
                mContext.getResources().getString( R.string.deliveryFee ) + restaurantsData.getDeliveryCost()
        );

        holder.restaurantCardTvMin.setText(
                mContext.getResources().getString(R.string.minimum) + restaurantsData.getMinimumCharger()
        );

        if (!restaurantsData.getAvailability().equals("open")) {
            holder.restaurantCardTvSates.setTextColor(mContext.getResources().getColor(R.color.red_error));
            holder.restaurantCardTvSates.setText(" مغلق");
            holder.restaurantCardTvSates.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    mContext.getResources().getDrawable(R.drawable.ic_close), null);
        }

//        holder.restaurantCardTvCategory.setText(categoryString.toString());
        holder.restaurantCardTvCategory.setText(getCategoryString(restaurantsData).toString());

    }
    private StringBuilder getCategoryString(RestaurantListData restaurantsData){

        StringBuilder categoryString = new StringBuilder();
        List<Category> categories = restaurantsData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            categoryString.append(categories.get(i).getName() + ".");
        }
        return categoryString;
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final View view;
        @BindView(R.id.restaurantCardImage)
        ImageView restaurantCardImage;
        @BindView(R.id.restaurantCardTvName)
        TextView restaurantCardTvName;
        @BindView(R.id.restaurantCardTvCategory)
        TextView restaurantCardTvCategory;
        @BindView(R.id.restaurantCardRatingBar)
        RatingBar restaurantCardRatingBar;
        @BindView(R.id.restaurantCardTvSates)
        TextView restaurantCardTvSates;
        @BindView(R.id.restaurantCardTvMin)
        TextView restaurantCardTvMin;
        @BindView(R.id.restaurantCardTvDeliveryFee)
        TextView restaurantCardTvDeliveryFee;

        private OnRestaurantClickListener mListener;

        public RestaurantViewHolder(View itemView, OnRestaurantClickListener listener) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind( this, view );
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.setOnRestaurantItemClickListener(getAdapterPosition());
        }
    }
}
