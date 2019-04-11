package com.example.mm.sofraappmaster.ui.fragment.restaurant.nav;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_add_item.RestaurantAddItem;
import com.example.mm.sofraappmaster.data.model.restaurant_food_item.restaurant_update_item.RestaurantUpdateItem;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.UserInputValidation;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_DESCRIPTION;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_ID;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRODUCT_TIME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys._ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewProductsFragment extends Fragment {


    @BindView(R.id.RestaurantProductName)
    EditText RestaurantProductName;
    @BindView(R.id.RestaurantProductDescription)
    EditText RestaurantProductDescription;
    @BindView(R.id.RestaurantProductPrice)
    EditText RestaurantProductPrice;
    @BindView(R.id.RestaurantProductPreparingTime)
    EditText RestaurantProductPreparingTime;
    Unbinder unbinder;
    @BindView(R.id.imageView_Product)
    ImageView imageViewProduct;



    //var
    private String description;
    private String price;
    private String preparingTime;
    private String name;
    private String photo;
    private int itemId;

    private Bundle bundle;

    private boolean mUpdate = false;

    private ArrayList<AlbumFile> mOfferImagesFiles = new ArrayList<>();


    public AddNewProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_products, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Get Food Details */
        bundle = getArguments();

        if (bundle != null) {
            mUpdate = true;
            description = bundle.getString(PRODUCT_DESCRIPTION);
            price = bundle.getString(PRODUCT_PRICE);
            preparingTime = bundle.getString(PRODUCT_TIME);
            name = bundle.getString(PRODUCT_NAME);
            photo = bundle.getString(PRODUCT_PHOTO);
            itemId = bundle.getInt(PRODUCT_ID);

            RestaurantProductDescription.setText(description);
            RestaurantProductPrice.setText(price);
            RestaurantProductPreparingTime.setText(preparingTime);
            RestaurantProductName.setText(name);
            Glide.with(getContext()).load(photo).into(imageViewProduct);
        }


        return view;
    }

    private void extractOfferInput() {

        String name = RestaurantProductName.getText().toString();
        String description = RestaurantProductDescription.getText().toString();
        String price = RestaurantProductPrice.getText().toString();
        String preparingTime = RestaurantProductPreparingTime.getText().toString();
        //String apiToken = SharedPrefManagerRestaurant.getInstance(getContext()).getRestaurantApiToken();
        String apiToken = "7jiWQQbN9afm8LTiO4VrmMObYz2lFig117PPCa1vxcK6VsXWy0pGWeq8MA4j";

        if (!UserInputValidation.isValidName(name)) {
            RestaurantProductName.setError("Please Enter Correct Offer Name..");

        } else if (description.trim().isEmpty()) {
            RestaurantProductDescription.setError("Please Enter your Offer Description..");

        } else if (price.trim().isEmpty()) {
            RestaurantProductPrice.setError("Please Enter your Offer Price..");

        } else {
            MultipartBody.Part bodyImage = HelperMethod.convertFileToMultipart(mOfferImagesFiles.get(0).getPath(), "photo");

            RequestBody nameBody = HelperMethod.convertToRequestBody(name);
            RequestBody descriptionBody = HelperMethod.convertToRequestBody(description);
            RequestBody priceBody = HelperMethod.convertToRequestBody(price);
            RequestBody preparingTimeBody = HelperMethod.convertToRequestBody(preparingTime);
            RequestBody apiTokenBody = HelperMethod.convertToRequestBody(apiToken);
            RequestBody itemIdBody = HelperMethod.convertToRequestBody(String.valueOf(itemId));

            if (!mUpdate) {
                addNewProductCall(descriptionBody, priceBody, preparingTimeBody, nameBody, bodyImage, apiTokenBody);
            }else {
                updateItem(descriptionBody, priceBody, preparingTimeBody, nameBody, bodyImage, itemIdBody, apiTokenBody);
            }


        }
    }

    private void updateItem(RequestBody description, RequestBody price, RequestBody preparingTime,
                                   RequestBody name, MultipartBody.Part photo, RequestBody itemId, RequestBody apiToken){

        Call<RestaurantUpdateItem> restaurantUpdateItemCall = RetrofitClient.getInstance().getApiServices().restaurantUpdateItem(
          description, price, preparingTime, name, photo, itemId, apiToken
        );

        restaurantUpdateItemCall.enqueue(new Callback<RestaurantUpdateItem>() {
            @Override
            public void onResponse(Call<RestaurantUpdateItem> call, Response<RestaurantUpdateItem> response) {
                try {
                    if (response.body().getStatus() == 1){
                        HelperMethod.showToast(getContext(), "OK" + response.body().getMsg());

                    }else {
                        HelperMethod.showToast(getContext(), "NO" + response.body().getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RestaurantUpdateItem> call, Throwable t) {

            }
        });
    }

    private void addNewProductCall(RequestBody description, RequestBody price, RequestBody preparingTime,
                                   RequestBody name, MultipartBody.Part photo, RequestBody apiToken){

        Call<RestaurantAddItem> restaurantAddItemCall = RetrofitClient.getInstance().getApiServices().restaurantAddItem(
                description, price, preparingTime, name, photo, apiToken
        );

        restaurantAddItemCall.enqueue(new Callback<RestaurantAddItem>() {
            @Override
            public void onResponse(Call<RestaurantAddItem> call, Response<RestaurantAddItem> response) {
                try {
                    if (response.body().getStatus() == 1){
                        HelperMethod.showToast(getContext(), "OK" + response.body().getMsg());

                    }else {
                        HelperMethod.showToast(getContext(), "NO" + response.body().getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RestaurantAddItem> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.imageView_Product, R.id.RegisterAddProductButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_Product:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mOfferImagesFiles.clear();
                        mOfferImagesFiles.addAll(result);
                        Glide.with(getContext()).load(mOfferImagesFiles.get(0).getPath()).into(imageViewProduct);
                    }
                };
                HelperMethod.openAlbum(3, getContext(), mOfferImagesFiles, action);
                break;
            case R.id.RegisterAddProductButton:
                    extractOfferInput();

                break;
        }
    }
}
