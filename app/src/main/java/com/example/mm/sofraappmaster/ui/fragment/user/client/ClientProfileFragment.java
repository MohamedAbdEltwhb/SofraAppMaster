package com.example.mm.sofraappmaster.ui.fragment.user.client;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.CitesSpinnerAdapter;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.RegionsSpinnerAdapter;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.Cites;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.CitesObject;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.Regions;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.RegionsDatum;
import com.example.mm.sofraappmaster.data.model.user.clint.client_edit_profile.ClientEditProfile;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.UserInputValidation;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.BackPressedListener;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientProfileFragment extends Fragment {


    @BindView(R.id.ClientProfileLogo)
    ImageView ClientProfileLogo;
    @BindView(R.id.ClientProfile_name)
    EditText ClientProfileName;
    @BindView(R.id.ClientProfile_Email)
    EditText ClientProfileEmail;
    @BindView(R.id.ClientProfile_Phone)
    EditText ClientProfilePhone;
    @BindView(R.id.ClientProfile_City)
    Spinner ClientProfileCity;
    @BindView(R.id.ClientProfile_regions)
    Spinner ClientProfileRegions;
    @BindView(R.id.ClientProfile_Address)
    EditText ClientProfileAddress;
    @BindView(R.id.ClientProfile_Password)
    EditText ClientProfilePassword;
    @BindView(R.id.ClientProfile_RePassword)
    EditText ClientProfileRePassword;
    Unbinder unbinder;

    //var
    private int mCityId;
    private String regionID;

    public ClientProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Handel Back Pressed Listener Button */
        configureBackPressedListener();

        getCitiesSpinner();
    }

    /**
     * Configure Back Pressed Listener Button
     */
    public void configureBackPressedListener() {
        ((HomeActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
                    @Override
                    public void doBack() {
                        super.doBack();
                    }
                });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_profile, container, false);
        unbinder = ButterKnife.bind(this, view);


        getClientProfileDataCall(SharedPrefManagerClient.getInstance(getContext()).getClientApiToken());



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ClientAddNewProfileLogo, R.id.ClientProfile_UpdateButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ClientAddNewProfileLogo:
                break;
            case R.id.ClientProfile_UpdateButton:
                extractUserInput();
                break;
        }
    }

    private void extractUserInput() {
        String name = ClientProfileName.getText().toString();
        String email = ClientProfileEmail.getText().toString();
        String phone = ClientProfilePhone.getText().toString();
        String address = ClientProfileAddress.getText().toString();
        String password = ClientProfilePassword.getText().toString();
        String re_password = ClientProfileRePassword.getText().toString();

        if (!UserInputValidation.isValidName(name)) {
            ClientProfileName.setError("Please Enter Correct Name..");

        } else if (!UserInputValidation.isValidMail(email)) {
            ClientProfileEmail.setError("Please Enter Correct Email..");

        } else if (!UserInputValidation.isValidMobile(phone)) {
            ClientProfilePhone.setError("Please Enter Correct mobile number..");

        } else if (address.trim().isEmpty()) {
            ClientProfileAddress.setError("Please Enter your Address..");

        } else if (!UserInputValidation.isValidPassword(password)) {
            ClientProfilePassword.setError("Please Enter Strong Password..");

        } else if (!UserInputValidation.isValidRePassword(re_password, password)) {
            ClientProfileRePassword.setError("Re Password Is not equal Password..");

        } else {

            editClintProfileCall(
                    SharedPrefManagerClient.getInstance(getContext()).getClientApiToken(),
                    name, email, password, re_password, phone, address, regionID
            );
        }
    }

    private void editClintProfileCall(String apiToken, String name, String phone, String email, String password,
                                      String rePassword, String address, String region_id) {
        Call<ClientEditProfile> clientEditProfileCall = RetrofitClient.getInstance().getApiServices().editClientProfileData(
                apiToken, name, phone, email, password, rePassword, address, region_id
        );
        clientEditProfileCall.enqueue(new Callback<ClientEditProfile>() {
            @Override
            public void onResponse(@NonNull Call<ClientEditProfile> call, @NonNull Response<ClientEditProfile> response) {
                try {
                    if (response.body().getStatus() == 1){
                        HelperMethod.showToast(getContext(), response.body().getMsg());

                    }else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());
                    }
                }catch (Exception e){
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClientEditProfile> call, @NonNull Throwable t) {

            }
        });

    }

    private void getClientProfileDataCall(String apiToken) {
        Call<ClientEditProfile> clientEditProfileCall = RetrofitClient.getInstance().getApiServices().getClientProfileData(apiToken);
        clientEditProfileCall.enqueue(new Callback<ClientEditProfile>() {
            @Override
            public void onResponse(@NonNull Call<ClientEditProfile> call, @NonNull Response<ClientEditProfile> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.showToast(getContext(), response.body().getMsg());

                        ClientProfileName.setText(response.body().getData().getUser().getName());
                        ClientProfileEmail.setText(response.body().getData().getUser().getEmail());
                        ClientProfilePhone.setText(response.body().getData().getUser().getPhone());
                        ClientProfileAddress.setText(response.body().getData().getUser().getAddress());

                        ClientProfileRegions.setSelection(SharedPrefManagerClient.getInstance(getContext()).getClientLoginRegionId());
                        ClientProfileCity.setSelection(SharedPrefManagerClient.getInstance(getContext()).getClientLoginCity());
                    } else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());
                    }
                } catch (Exception e) {
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClientEditProfile> call, @NonNull Throwable t) {
                HelperMethod.showToast(getContext(), "onFailure : " + t.getMessage());
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

                        ClientProfileCity.setAdapter(citesSpinnerAdapter);
                        ClientProfileCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                CitesObject citesObject = (CitesObject) parent.getSelectedItem();
                                //mCityName = citesObject.getName();
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

                        ClientProfileRegions.setAdapter(regionsSpinnerAdapter);
                        ClientProfileRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
}
