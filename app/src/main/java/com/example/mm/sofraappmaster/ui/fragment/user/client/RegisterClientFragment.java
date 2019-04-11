package com.example.mm.sofraappmaster.ui.fragment.user.client;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.CitesSpinnerAdapter;
import com.example.mm.sofraappmaster.adapter.spinners_adapter.RegionsSpinnerAdapter;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.Cites;
import com.example.mm.sofraappmaster.data.model.general.spinner.cites.CitesObject;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.Regions;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.RegionsDatum;
import com.example.mm.sofraappmaster.data.model.user.clint.clint_register.ClintRegister;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.UserInputValidation;

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
public class RegisterClientFragment extends Fragment {

    @BindView(R.id.RegisterClient_City)
    Spinner RegisterClientCity;
    @BindView(R.id.RegisterClient_regions)
    Spinner RegisterClientRegions;

    @BindView(R.id.RegisterClient_name)
    EditText RegisterClientName;
    @BindView(R.id.RegisterClient_Email)
    EditText RegisterClientEmail;
    @BindView(R.id.RegisterClient_Phone)
    EditText RegisterClientPhone;
    @BindView(R.id.RegisterClient_Address)
    EditText RegisterClientAddress;
    @BindView(R.id.RegisterClient_Password)
    EditText RegisterClientPassword;
    @BindView(R.id.RegisterClient_RePassword)
    EditText RegisterClientRePassword;
    Unbinder unbinder;

    //var
    private int mCityId;
    private String regionID;

    public RegisterClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_client, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Get Spinner List Cites Use API Call */
        getCitiesSpinner();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.RegisterClient_SaveButton)
    public void onViewClicked() {
        extractClientInput();
    }

    private void extractClientInput(){
        String name = RegisterClientName.getText().toString();
        String email = RegisterClientEmail.getText().toString();
        String phone = RegisterClientPhone.getText().toString();
        String address = RegisterClientAddress.getText().toString();
        String password = RegisterClientPassword.getText().toString();
        String re_password = RegisterClientRePassword.getText().toString();

        if (!UserInputValidation.isValidName(name)){
            RegisterClientName.setError("Please Enter Correct Name..");

        }else if (!UserInputValidation.isValidMail(email)){
            RegisterClientEmail.setError("Please Enter Correct Email..");

        }else if (!UserInputValidation.isValidMobile(phone)){
            RegisterClientPhone.setError("Please Enter Correct mobile number..");

        }else if (address.trim().isEmpty()){
            RegisterClientAddress.setError("Please Enter your Address..");

        }else if (!UserInputValidation.isValidPassword(password)) {
            RegisterClientPassword.setError("Please Enter Strong Password..");

        } else if (!UserInputValidation.isValidRePassword(re_password, password)) {
            RegisterClientRePassword.setError("Re Password Is not equal Password..");

        }else {
            clintRegister(name, email, password, re_password, phone, address, regionID);
        }
    }

    private void clintRegister(String name, String email, String password, String re_password,
                               String phone, String address, String regionID) {

        Call<ClintRegister> clintRegisterCall = RetrofitClient.getInstance().getApiServices().clintRegister(
                name, email, password, re_password, phone, address, regionID
        );

        clintRegisterCall.enqueue(new Callback<ClintRegister>() {
            @Override
            public void onResponse(@NonNull Call<ClintRegister> call, @NonNull Response<ClintRegister> response) {
                try {
                    if (response.body().getStatus() == 1){

                        HelperMethod.showToast(getContext(),"OK:" + response.body().getMsg());

                    }else {
                        HelperMethod.showToast(getContext(),"NO:" + response.body().getMsg());
                    }
                }catch (Exception e) {
                    //HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClintRegister> call, @NonNull Throwable t) {

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

                        RegisterClientCity.setAdapter(citesSpinnerAdapter);
                        RegisterClientCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                        RegisterClientRegions.setAdapter(regionsSpinnerAdapter);
                        RegisterClientRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
