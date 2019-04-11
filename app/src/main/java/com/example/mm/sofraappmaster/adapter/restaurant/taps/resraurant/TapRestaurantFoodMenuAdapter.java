package com.example.mm.sofraappmaster.adapter.restaurant.taps.resraurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.FoodDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TapRestaurantFoodMenuAdapter extends RecyclerView.Adapter<TapRestaurantFoodMenuAdapter.TapRestaurantFoodMenuHolder> {

    private Context mContext;
    private List<FoodDatum> mFoodDataList;
    private RestaurantButtonClick buttonClick;

    public TapRestaurantFoodMenuAdapter(Context mContext, List<FoodDatum> mFoodDataList) {
        this.mContext = mContext;
        this.mFoodDataList = mFoodDataList;
    }

    @NonNull
    @Override
    public TapRestaurantFoodMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TapRestaurantFoodMenuHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_restaurant_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TapRestaurantFoodMenuHolder holder, int position) {
        FoodDatum foodDatum = mFoodDataList.get(position);
        Glide.with(mContext).load(foodDatum.getPhotoUrl()).into(holder.itemFoodImage);

        holder.itemFoodName.setText(foodDatum.getName());
        holder.itemFoodDes.setText(foodDatum.getDescription());
        holder.itemFoodPrice.setText(foodDatum.getPrice());

    }

    @Override
    public int getItemCount() {
        return mFoodDataList.size();
    }

    public class TapRestaurantFoodMenuHolder extends RecyclerView.ViewHolder {
        private final View view;

        @BindView(R.id.Edit_Button)
        Button EditButton;
        @BindView(R.id.Delete_Button)
        Button DeleteButton;
        @BindView(R.id.itemFoodImage)
        ImageView itemFoodImage;
        @BindView(R.id.itemFoodName)
        TextView itemFoodName;
        @BindView(R.id.itemFoodDes)
        TextView itemFoodDes;
        @BindView(R.id.itemFoodPrice)
        TextView itemFoodPrice;

        public TapRestaurantFoodMenuHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);

            EditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    buttonClick.onEditButtonClicked(position);
                }
            });

            DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    buttonClick.onDeleteButtonClicked(position);
                }
            });
        }
    }

    public void setOnRestaurantButtonClickListener(RestaurantButtonClick restaurantButtonClick) {
        this.buttonClick = restaurantButtonClick;
    }
}
