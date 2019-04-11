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
import com.example.mm.sofraappmaster.data.model.general.offers.OffersDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersHolder> {

    private Context mContext;
    private List<OffersDatum> mOffersDatumList;

    public OffersAdapter(Context mContext, List<OffersDatum> offersDatumList) {
        this.mContext = mContext;
        this.mOffersDatumList = offersDatumList;
    }

    @NonNull
    @Override
    public OffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OffersHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OffersHolder holder, int position) {
        OffersDatum offersDatum = mOffersDatumList.get(position);

        Glide.with(mContext).load(offersDatum.getPhoto()).into(holder.offerImageView);

        holder.OfferTextViewNameRest.setText(offersDatum.getRestaurant().getName());
        holder.OfferTextViewPrice.setText(offersDatum.getPrice());
        holder.OfferTextViewOfferTitle.setText(offersDatum.getName());
        holder.OfferTextViewDate.setText(offersDatum.getDescription());
    }

    @Override
    public int getItemCount() {
        return mOffersDatumList.size();
    }

    public class OffersHolder extends RecyclerView.ViewHolder {

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

        private final View view;

        public OffersHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
