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
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerRestaurant;
import com.example.mm.sofraappmaster.data.model.restaurant_offer.restaurant_new_offer.RestaurantNewOffer;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewOfferFragment extends Fragment {


    @BindView(R.id.RestaurantOfferName)
    EditText RestaurantOfferName;
    @BindView(R.id.RestaurantOfferDescription)
    EditText RestaurantOfferDescription;
    @BindView(R.id.RestaurantOfferPrice)
    EditText RestaurantOfferPrice;
    @BindView(R.id.RestaurantOfferDateFrom)
    EditText RestaurantOfferDateFrom;
    @BindView(R.id.RestaurantOfferDateTo)
    EditText RestaurantOfferDateTo;
    @BindView(R.id.imageView_Register)
    ImageView imageViewRegister;
    Unbinder unbinder;

    //var
    private ArrayList<AlbumFile> mOfferImagesFiles = new ArrayList<>();

    public AddNewOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void extractOfferInput() {

        String name = RestaurantOfferName.getText().toString();
        String description = RestaurantOfferDescription.getText().toString();
        String price = RestaurantOfferPrice.getText().toString();
        String dateFrom = RestaurantOfferDateFrom.getText().toString();
        String dateTo = RestaurantOfferDateTo.getText().toString();
        //String apiToken = SharedPrefManagerRestaurant.getInstance(getContext()).getRestaurantApiToken();
        String apiToken = "OGqjF8iGLccLQqdOJ11gHDTzdwG6980twebZRnN66mOFWh2P0Qwb3UCFHboc";

        if (!UserInputValidation.isValidName(name)) {
            RestaurantOfferName.setError("Please Enter Correct Offer Name..");

        }else if (description.trim().isEmpty()){
            RestaurantOfferDescription.setError("Please Enter your Offer Description..");

        }else if (price.trim().isEmpty()){
            RestaurantOfferPrice.setError("Please Enter your Offer Price..");

        }else {
            MultipartBody.Part bodyImage = HelperMethod.convertFileToMultipart(mOfferImagesFiles.get(0).getPath(), "photo");

            RequestBody nameBody = HelperMethod.convertToRequestBody(name);
            RequestBody descriptionBody = HelperMethod.convertToRequestBody(description);
            RequestBody priceBody = HelperMethod.convertToRequestBody(price);
            RequestBody dateFromBody = HelperMethod.convertToRequestBody(dateFrom);
            RequestBody dateToBody = HelperMethod.convertToRequestBody(dateTo);
            RequestBody apiTokenBody = HelperMethod.convertToRequestBody(apiToken);

            addNewOfferCall(descriptionBody, priceBody, dateFromBody, nameBody, bodyImage, dateToBody, apiTokenBody);
        }
    }

    private void addNewOfferCall(RequestBody descriptionBody, RequestBody priceBody,
                                 RequestBody dateFromBody, RequestBody nameBody, MultipartBody.Part bodyImage,
                                 RequestBody dateToBody, RequestBody apiTokenBody) {

        Call<RestaurantNewOffer> restaurantNewOfferCall = RetrofitClient.getInstance().getApiServices().restaurantAddNewOffer(
                descriptionBody, priceBody, dateFromBody, nameBody, bodyImage, dateToBody, apiTokenBody
        );

        restaurantNewOfferCall.enqueue(new Callback<RestaurantNewOffer>() {
            @Override
            public void onResponse(Call<RestaurantNewOffer> call, Response<RestaurantNewOffer> response) {
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
            public void onFailure(Call<RestaurantNewOffer> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imageView_Register, R.id.Register_restaurant_SignUp_Button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_Register:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mOfferImagesFiles.clear();
                        mOfferImagesFiles.addAll(result);
                        Glide.with(getContext()).load(mOfferImagesFiles.get(0).getPath()).into(imageViewRegister);
                    }
                };
                HelperMethod.openAlbum(3, getContext(), mOfferImagesFiles, action);
                break;
            case R.id.Register_restaurant_SignUp_Button:
                extractOfferInput();
                break;
        }
    }
}
