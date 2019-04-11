package com.example.mm.sofraappmaster.adapter.restaurant.taps.food_menu_tap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.restaurant.listOfRestaurantItems.ItemsFoodData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TapFoodMenuAdapter extends RecyclerView.Adapter<TapFoodMenuAdapter.TapFoodMenuHolder> {

    private Context mContext;
    private List<ItemsFoodData> mFoodDataList;
    private OnFoodClickListener mOnFoodClickListener;

    public TapFoodMenuAdapter(Context mContext, List<ItemsFoodData> mFoodDataList, OnFoodClickListener mOnFoodClickListener) {
        this.mContext = mContext;
        this.mFoodDataList = mFoodDataList;
        this.mOnFoodClickListener = mOnFoodClickListener;
    }

    @NonNull
    @Override
    public TapFoodMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TapFoodMenuHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_food_men_tap, parent, false),
                mOnFoodClickListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TapFoodMenuHolder holder, int position) {
        ItemsFoodData foodData = mFoodDataList.get(position);

        Glide.with(mContext)
                .load(foodData.getPhotoUrl())
                .into( holder.itemFoodCardImage);

        holder.itemFoodCardName.setText(foodData.getName());
        holder.itemFoodCardDes.setText(foodData.getDescription());
        holder.itemFoodCardPrice.setText(foodData.getPrice());
    }

    @Override
    public int getItemCount() {
        return mFoodDataList.size();
    }

    public class TapFoodMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnFoodClickListener mListener;
        private final View view;

        @BindView(R.id.itemFoodCardImage)
        ImageView itemFoodCardImage;
        @BindView(R.id.itemFoodCardName)
        TextView itemFoodCardName;
        @BindView(R.id.itemFoodCardDes)
        TextView itemFoodCardDes;
        @BindView(R.id.itemFoodCardPrice)
        TextView itemFoodCardPrice;

        public TapFoodMenuHolder(View itemView, OnFoodClickListener listener) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
            this.mListener = listener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.setOnFoodItemClickListener(getAdapterPosition());
        }
    }
}
