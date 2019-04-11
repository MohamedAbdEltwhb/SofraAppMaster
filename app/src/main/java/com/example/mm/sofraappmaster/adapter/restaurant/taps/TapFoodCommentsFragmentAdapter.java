package com.example.mm.sofraappmaster.adapter.restaurant.taps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.restaurant.restaurantReviews.RestaurantReviewData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TapFoodCommentsFragmentAdapter extends RecyclerView.Adapter<TapFoodCommentsFragmentAdapter.TapFoodCommentsFragmentHolder> {

    private Context mContext;
    private List<RestaurantReviewData> mReviewDataList;

    public TapFoodCommentsFragmentAdapter(Context mContext, List<RestaurantReviewData> mReviewDataList) {
        this.mContext = mContext;
        this.mReviewDataList = mReviewDataList;
    }

    @NonNull
    @Override
    public TapFoodCommentsFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TapFoodCommentsFragmentHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TapFoodCommentsFragmentHolder holder, int position) {
        RestaurantReviewData restaurantReviewData = mReviewDataList.get(position);

        holder.commentCardName.setText(restaurantReviewData.getClient().getName());
        holder.commentCardRatingBar.setRating(restaurantReviewData.getRate());
        holder.commentCardDate.setText(restaurantReviewData.getCreatedAt());
        holder.commentCardTvComment.setText(restaurantReviewData.getComment());
    }

    @Override
    public int getItemCount() {
        return mReviewDataList.size();
    }

    public class TapFoodCommentsFragmentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentCardName)
        TextView commentCardName;
        @BindView(R.id.commentCardRatingBar)
        RatingBar commentCardRatingBar;
        @BindView(R.id.commentCardDate)
        TextView commentCardDate;
        @BindView(R.id.commentCardTvComment)
        TextView commentCardTvComment;

        private final View view;

        public TapFoodCommentsFragmentHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
