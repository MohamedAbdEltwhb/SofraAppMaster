package com.example.mm.sofraappmaster.ui.fragment.user.restaurant;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.CitesSpinnerAdapter;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.RegionsSpinnerAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerRestaurant;
import com.example.mm.sofraappmaster.data.model.general.spinner.categories.Categories;
import com.example.mm.sofraappmaster.data.model.general.spinner.categories.CategoriesDatum;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.Cites;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.CitesObject;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.Regions;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.RegionsDatum;
import com.example.mm.sofraappmaster.data.model.user.restaurant_register.RestaurantRegister;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.multi_select_spinner.MultiSelectSpinnerAdapter;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.multi_select_spinner.MultipleSelectCheckBoxChangeListener;
import com.example.mm.sofraappmaster.helper.UserInputValidation;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.BackPressedListener;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;
import com.example.mm.sofraappmaster.ui.activity.SplashActivity;
import com.example.mm.sofraappmaster.ui.fragment.user.LoginFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
public class RegisterRestaurantFragment extends Fragment {

    Unbinder unbinder;


    @BindView(R.id.Register_restaurant_Spinner_City)
    Spinner RegisterRestaurantSpinnerCity;
    @BindView(R.id.Register_restaurant_Spinner_regions)
    Spinner RegisterRestaurantSpinnerRegions;
    @BindView(R.id.Register_restaurant_Spinner_Categories)
    Spinner RegisterRestaurantSpinnerCategories;

    @BindView(R.id.Register_restaurant_MinimumOrder)
    EditText RegisterRestaurantMinimumOrder;
    @BindView(R.id.Register_restaurant_DeliveryCost)
    EditText RegisterRestaurantDeliveryCost;
    @BindView(R.id.Register_restaurant_Phone)
    EditText RegisterRestaurantPhone;
    @BindView(R.id.Register_restaurant_WatsApp)
    EditText RegisterRestaurantWatsApp;
    @BindView(R.id.Register_restaurant_name)
    EditText RegisterRestaurantName;
    @BindView(R.id.Register_restaurant_Email)
    EditText RegisterRestaurantEmail;
    @BindView(R.id.Register_restaurant_Password)
    EditText RegisterRestaurantPassword;
    @BindView(R.id.Register_restaurant_RePassword)
    EditText RegisterRestaurantRePassword;

    @BindView(R.id.imageView_Register)
    ImageView imageViewRegister;

    @BindView(R.id.RegisterRestaurantNestedScrollView)
    NestedScrollView RegisterRestaurantNestedScrollView;
    @BindView(R.id.RegisterRestaurantInclude)
    FrameLayout RegisterRestaurantInclude;

    //var
    private boolean mPackStatus = true;
    private int mCityId;

    private List<String> mCategoriesList;
    private ArrayList<AlbumFile> mImagesFiles = new ArrayList<>();

    private String regionID;
    private String mCityName;

    private MultiSelectSpinnerAdapter mMultiSelectSpinnerAdapter;

    public RegisterRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Configure Back Pressed Listener Button
     */
    public void configureBackPressedListener() {
        ((HomeActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
                    @Override
                    public void doBack() {

                        if (mPackStatus) {

                            /* Back to Splash Activity */
                            HelperMethod.intentTo(getContext(), SplashActivity.class);

                        } else {
                            RegisterRestaurantNestedScrollView.setVisibility(View.VISIBLE);
                            RegisterRestaurantInclude.setVisibility(View.GONE);
                            mPackStatus = true;
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Handel Back Pressed Listener Button */
        configureBackPressedListener();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);

        mCategoriesList = new ArrayList<>();

        HelperMethod.showToast(getContext(), SharedPrefManagerRestaurant.getInstance(getContext()).getRestaurantApiToken());

        /* Get Spinner List Cites Use API Call */
        getCitiesSpinner();

        /* Get Spinner List Categories Use API Call */
        getCategoriesSpinner();

        return view;
    }


    /**
     * Extract Input ...
     */
    private void extractRegistrationInput() {
        String name = RegisterRestaurantName.getText().toString();
        String email = RegisterRestaurantEmail.getText().toString();
        String password = RegisterRestaurantPassword.getText().toString();
        String rePassword = RegisterRestaurantRePassword.getText().toString();

        String minimumOrder = RegisterRestaurantMinimumOrder.getText().toString();
        String deliveryCost = RegisterRestaurantDeliveryCost.getText().toString();
        String phone = RegisterRestaurantPhone.getText().toString();
        String watsApp = RegisterRestaurantWatsApp.getText().toString();

        if (!UserInputValidation.isValidName(name)) {
            RegisterRestaurantName.setError("Please Enter Correct Name..");

        } else if (!UserInputValidation.isValidMail(email)) {
            RegisterRestaurantEmail.setError("Please Enter Correct Email..");

        } else if (!UserInputValidation.isValidPassword(password)) {
            RegisterRestaurantPassword.setError("Please Enter Strong Password..");

        } else if (!UserInputValidation.isValidRePassword(rePassword, password)) {
            RegisterRestaurantRePassword.setError("Re Password Is not equal Password..");

        } else if (!UserInputValidation.isValidMobile(phone)) {
            RegisterRestaurantPhone.setError("Please Enter Correct mobile number..");

        } else if (!UserInputValidation.isValidMobile(watsApp)) {
            RegisterRestaurantWatsApp.setError("Please Enter Correct mobile number..");

        } else if (minimumOrder.isEmpty() && Integer.valueOf(minimumOrder) <= 0) {
            RegisterRestaurantMinimumOrder.setError("Please Enter Correct Minimum Order..");

        } else if (deliveryCost.isEmpty()) {
            RegisterRestaurantDeliveryCost.setError("Please Enter Correct Delivery Cost..");

        } else {
            MultipartBody.Part bodyImage = HelperMethod.convertFileToMultipart(mImagesFiles.get(0).getPath(), "photo");

            RequestBody nameBody = HelperMethod.convertToRequestBody(name);
            RequestBody emailBody = HelperMethod.convertToRequestBody(email);
            RequestBody passwordBody = HelperMethod.convertToRequestBody(password);
            RequestBody rePasswordBody = HelperMethod.convertToRequestBody(rePassword);
            RequestBody minimumOrderBody = HelperMethod.convertToRequestBody(minimumOrder);
            RequestBody deliveryCostBody = HelperMethod.convertToRequestBody(deliveryCost);
            RequestBody phoneBody = HelperMethod.convertToRequestBody(phone);
            RequestBody watsAppBody = HelperMethod.convertToRequestBody(watsApp);
            RequestBody periodBody = HelperMethod.convertToRequestBody("30");
            RequestBody bodyAvailability = HelperMethod.convertToRequestBody("open");
            RequestBody bodyRegion = HelperMethod.convertToRequestBody(regionID);
            RequestBody bodyCityName = HelperMethod.convertToRequestBody(mCityName);
            if (mCategoriesList != null) {
                register(nameBody, emailBody, passwordBody, rePasswordBody, phoneBody, bodyCityName, watsAppBody,
                        bodyRegion, getCategories(), periodBody, deliveryCostBody, minimumOrderBody, bodyImage, bodyAvailability);
            }
        }
    }

    private void register(RequestBody Name, RequestBody email, RequestBody password, RequestBody password_confirmation,
                          RequestBody phone, RequestBody address, RequestBody whatsApp, RequestBody region_id,
                          List<RequestBody> categories, RequestBody delivery_period, RequestBody delivery_cost,
                          RequestBody minimum_charger, MultipartBody.Part photo, RequestBody availability) {

        Call<RestaurantRegister> restaurantRegisterCall = RetrofitClient.getInstance().getApiServices().restaurantRegisteration(
                Name, email, password, password_confirmation, phone, address, whatsApp, region_id, categories, delivery_period,
                delivery_cost, minimum_charger, photo, availability
        );

        restaurantRegisterCall.enqueue(new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantRegister> call, @NonNull Response<RestaurantRegister> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.showToast(getContext(), "OK:" + response.body().getMsg());

                        HelperMethod.replaceFragments(new LoginFragment(), Objects.requireNonNull(getActivity())
                                        .getSupportFragmentManager(), R.id.Request_food_Activity_FL, null, null);

                    } else {
                        HelperMethod.showToast(getContext(), "NO Response:" + response.body().getMsg());
                    }

                } catch (Exception e) {
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantRegister> call, @NonNull Throwable t) {
                HelperMethod.showToast(getContext(), "NO:" + t.getMessage());
            }
        });
    }

    /**
     * Get Categories Checked and Covert to RequestBody
     */
    private List<RequestBody> getCategories() {
        List<RequestBody> bodyCategory = new ArrayList<>();
//        for (int i = 0; i < mCategoriesList.size(); i++) {
//            HelperMethod.showToast(getContext(), mCategoriesList.get(i));
//        }

        for (int i = 0; i < mCategoriesList.size(); i++) {
            bodyCategory.add(HelperMethod.convertToRequestBody(mCategoriesList.get(i)));
            //HelperMethod.showToast(getContext(), mCategoriesList.get(i));
        }
        return bodyCategory;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Register_restaurant_CheckBox_DeliveryData, R.id.imageView_Register, R.id.Register_restaurant_SignUp_Button,
            R.id.Register_restaurant_Continue_Button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Register_restaurant_CheckBox_DeliveryData:
                break;

            case R.id.imageView_Register:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mImagesFiles.clear();
                        mImagesFiles.addAll(result);
                        Glide.with(getContext()).load(mImagesFiles.get(0).getPath()).into(imageViewRegister);
                    }
                };
                HelperMethod.openAlbum(3, getContext(), mImagesFiles, action);
                break;

            case R.id.Register_restaurant_SignUp_Button:
                extractRegistrationInput();
                break;

            case R.id.Register_restaurant_Continue_Button:
                mPackStatus = false;
                RegisterRestaurantNestedScrollView.setVisibility(View.GONE);
                RegisterRestaurantInclude.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Get Spinner List Categories Use API Call
     */
    private void getCategoriesSpinner() {
        Call<Categories> categoriesCall = RetrofitClient.getInstance().getApiServices().getCategoriesList();

        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        List<String> categoryType = new ArrayList<>();
                        final List<Integer> categoryId = new ArrayList<>();
                        List<CategoriesDatum> categoriesData = response.body().getData();

                        for (int i = 0; i < categoriesData.size(); i++) {
                            String categoryName = categoriesData.get(i).getName();
                            categoryType.add(categoryName);

                            int category_Id = categoriesData.get(i).getId();
                            categoryId.add(category_Id);
                        }

                        mMultiSelectSpinnerAdapter = new MultiSelectSpinnerAdapter(
                                categoriesData, categoryType, categoryId, getContext(),
                                new MultipleSelectCheckBoxChangeListener() {
                                    @Override
                                    public void onMultiSelectOptionSelected(int itemsId, boolean isAdded) {
                                        if (isAdded) {
                                            mCategoriesList.addAll(Collections.singletonList(String.valueOf(itemsId)));
                                            //HelperMethod.showToast(getContext(), "add:" + itemsId);

                                        } else {
                                            mCategoriesList.removeAll(Collections.singletonList(String.valueOf(itemsId)));
                                            //HelperMethod.showToast(getContext(), "remove" + itemsId);
                                        }
                                    }
                                });

                        RegisterRestaurantSpinnerCategories.setAdapter(mMultiSelectSpinnerAdapter);
                    } else {
                        HelperMethod.showToast(getContext(), response.message());
                    }
                } catch (Exception e) {
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {

            }
        });

    }

    /**
     * Get Spinner List Regions Use API Call
     */
    private void getRegionsSpinner(int cityId) {
        final Call<Regions> regionsCall = RetrofitClient.getInstance().getApiServices().getRegions(cityId);

        regionsCall.enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(@NonNull Call<Regions> call, @NonNull final Response<Regions> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        //HelperMethod.showToast(getContext(), response.message());
                        RegionsSpinnerAdapter regionsSpinnerAdapter = new
                                RegionsSpinnerAdapter(Objects.requireNonNull(getContext()), response.body().getData().getData());

                        RegisterRestaurantSpinnerRegions.setAdapter(regionsSpinnerAdapter);
                        RegisterRestaurantSpinnerRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                RegionsDatum regionsDatum = (RegionsDatum) parent.getSelectedItem();
                                int rId = regionsDatum.getId();
                                regionID = String.valueOf(rId);
                                //HelperMethod.showToast(getContext(), String.valueOf(rId));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        HelperMethod.showToast(getContext(), response.message());
                    }
                } catch (Exception e) {
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Regions> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Get Spinner List Cites Use API Call
     */
    private void getCitiesSpinner() {
        Call<Cites> citesCall = RetrofitClient.getInstance().getApiServices().getCitesList();

        citesCall.enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(@NonNull Call<Cites> call, @NonNull Response<Cites> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        CitesSpinnerAdapter citesSpinnerAdapter = new CitesSpinnerAdapter(
                                Objects.requireNonNull(getContext()), response.body().getData().getData());

                        RegisterRestaurantSpinnerCity.setAdapter(citesSpinnerAdapter);
                        RegisterRestaurantSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                CitesObject citesObject = (CitesObject) parent.getSelectedItem();
                                mCityName = citesObject.getName();
                                //HelperMethod.showToast(getContext(), mCityName);
                                mCityId = citesObject.getId();
                                /* Get Spinner List Regions Use API Call */
                                getRegionsSpinner(mCityId);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        HelperMethod.showToast(getContext(), response.message());
                    }
                } catch (Exception e) {
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cites> call, @NonNull Throwable t) {

            }
        });
    }


}
