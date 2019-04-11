package com.example.mm.sofraappmaster.ui.custom.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.model.restaurant.addReview.AddReview;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomAddCommentDialog {

    private Dialog dialog;
    private Context mContext;

    public CustomAddCommentDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void showCustomDialog() {

        dialog = new Dialog(mContext);

        // before
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final RatingBar ratingBar = dialog.findViewById(R.id.dialogCommentRatingBar);
        final EditText commentEditText = dialog.findViewById(R.id.dialogCommentEditText);
        final Button addCommentBtn = dialog.findViewById(R.id.dialogAddCommentButton);

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rating = (int) ratingBar.getRating();
                String comment = commentEditText.getText().toString();
                int restaurantId = RestaurantDetailsFragment.id;
                String apiToken = SharedPrefManagerClient.getInstance(mContext).getClientApiToken();

                if (apiToken == null || apiToken.equals("")){
                    Toast.makeText(mContext, "Pleas Sign in With Your Email And try Again", Toast.LENGTH_SHORT).show();

                }else if (rating == 0 || comment == null){

                }else {
                    /* Add New Comment Use API call */
                    mackAddReviewCall(rating, comment, restaurantId, apiToken);
                }
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    /**
     * Add New Comment Use API call
     *
     * @param rating
     * @param comment
     * @param restaurantId
     * @param apiToken
     */
    private void mackAddReviewCall(int rating, String comment, int restaurantId, String apiToken) {
        Call<AddReview> addReviewCall = RetrofitClient
                .getInstance().getApiServices().addReviewCall(rating, comment, restaurantId, apiToken);

        addReviewCall.enqueue(new Callback<AddReview>() {
            @Override
            public void onResponse(@NonNull Call<AddReview> call, @NonNull Response<AddReview> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddReview> call, @NonNull Throwable t) {

            }
        });
    }
}
