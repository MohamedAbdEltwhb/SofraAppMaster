package com.example.mm.sofraappmaster.adapter.nav;

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
import com.example.mm.sofraappmaster.data.model.general.offers.restaurant_my_offers.RestaurantOfferDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantOffersAdapter extends RecyclerView.Adapter<RestaurantOffersAdapter.RestaurantOffersHolder> {

    private Context mContext;
    private List<RestaurantOfferDatum> mRestaurantOfferDatumList;

    public RestaurantOffersAdapter(Context mContext, List<RestaurantOfferDatum> restaurantOfferDatumList) {
        this.mContext = mContext;
        this.mRestaurantOfferDatumList = restaurantOfferDatumList;
    }

    @NonNull
    @Override
    public RestaurantOffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantOffersHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantOffersHolder holder, int position) {
        RestaurantOfferDatum restaurantOfferDatum = mRestaurantOfferDatumList.get(position);

        Glide.with(mContext).load(restaurantOfferDatum.getPhoto()).into(holder.offerImageView);
        holder.OfferTextViewOfferTitle.setText(restaurantOfferDatum.getName());
        holder.OfferTextViewNameRest.setText(restaurantOfferDatum.getRestaurant().getName());
        holder.OfferTextViewDate.setText(restaurantOfferDatum.getDescription());
        holder.OfferTextViewPrice.setText(restaurantOfferDatum.getPrice());
    }

    @Override
    public int getItemCount() {
        return mRestaurantOfferDatumList.size();
    }

    public class RestaurantOffersHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offerImageView)
        ImageView offerImageView;
        @BindView(R.id.OfferTextViewOfferTitle)
        TextView OfferTextViewOfferTitle;
        @BindView(R.id.OfferTextViewNameRest)
        TextView OfferTextViewNameRest;
        @BindView(R.id.OfferTextViewDate)
        TextView OfferTextViewDate;
        @BindView(R.id.OfferTextViewPrice)
        TextView OfferTextViewPrice;

        private final View view ;
        public RestaurantOffersHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
