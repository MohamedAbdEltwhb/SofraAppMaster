package com.example.mm.sofraappmaster.ui.fragment.user;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerRestaurant;
import com.example.mm.sofraappmaster.data.model.user.clint.client_login.ClientLogin;
import com.example.mm.sofraappmaster.data.model.user.login_restaurant.LoginRestaurant;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.UserInputValidation;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.order.OrderDetailsFragment;
import com.example.mm.sofraappmaster.ui.fragment.user.client.RegisterClientFragment;
import com.example.mm.sofraappmaster.ui.fragment.user.restaurant.RegisterRestaurantFragment;

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
public class LoginFragment extends Fragment {


    @BindView(R.id.loginUserEditTextEmile)
    EditText loginUserEditTextEmile;
    @BindView(R.id.loginUserEditTextPassword)
    EditText loginUserEditTextPassword;
    Unbinder unbinder;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);

        HelperMethod.showToast(getContext(), ""+ SharedPrefManagerClient.getInstance(getContext()).getUserType());

        return view;
    }

    private void extractUserInput(){

        String email = loginUserEditTextEmile.getText().toString();
        String password = loginUserEditTextPassword.getText().toString();

        if (!UserInputValidation.isValidMail(email)) {
            loginUserEditTextEmile.setError("Please Enter Correct Email..");

        } else if (!UserInputValidation.isValidPassword(password)) {
            loginUserEditTextPassword.setError("Please Enter Strong Password..");

        }else {
            if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1){
                //loginClient("yehia@yahoo.com", "123456");
                loginClient(email, password);
            }
            if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2){
                //loginRestaurant("yehiaa@yehia.com", "123456");
                loginRestaurant(email, password);
            }
        }
    }

    private void loginRestaurant (String email, String password){
        Call<LoginRestaurant> loginRestaurantCall = RetrofitClient.getInstance().getApiServices().restaurantLogin(email, password);
        loginRestaurantCall.enqueue(new Callback<LoginRestaurant>() {
            @Override
            public void onResponse(@NonNull Call<LoginRestaurant> call, @NonNull Response<LoginRestaurant> response) {
                try{
                    if (response.body().getStatus() == 1){
                        HelperMethod.showToast(getContext(), "Restaurant:" + response.body().getMsg());

                        HelperMethod.showToast(getContext(),
                                SharedPrefManagerRestaurant.getInstance(getContext()).getRestaurantApiToken());

                        /* Save Restaurant Login Data */
                        SharedPrefManagerRestaurant.getInstance(getContext()).setRestaurantApiToken(response.body().getData().getApiToken());
                        SharedPrefManagerRestaurant.getInstance(getContext()).saveRestaurantLoginData(response.body().getData().getUser());
                        SharedPrefManagerRestaurant.getInstance(getContext()).saveRestaurantLoginCity(
                                response.body().getData().getUser().getRegion().getCity().getId(),
                                response.body().getData().getUser().getRegion().getId()
                        );

                        HelperMethod.replaceFragments(new RestaurantDetailsFragment(), getActivity().getSupportFragmentManager(),
                                R.id.Request_food_Activity_FL, null, null);

                    }else {
                        HelperMethod.showToast(getContext(), response.body().getMsg());
                    }
                } catch (Exception e){
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginRestaurant> call, @NonNull Throwable t) {

            }
        });
    }

    private void loginClient (String email, String password){
        Call<ClientLogin> clientLoginCall = RetrofitClient.getInstance().getApiServices().clientLogin(email,password);
        clientLoginCall.enqueue(new Callback<ClientLogin>() {
            @Override
            public void onResponse(@NonNull Call<ClientLogin> call, @NonNull Response<ClientLogin> response) {
                try{
                    if (response.body().getStatus() == 1){
                        HelperMethod.showToast(getContext(),"OK:" + response.body().getMsg());

                        /* Save Client Login Data */
                        SharedPrefManagerClient.getInstance(getContext()).saveClientLoginData(response.body().getData().getUser());
                        SharedPrefManagerClient.getInstance(getContext()).setClientApiToken(response.body().getData().getApiToken());
                        SharedPrefManagerClient.getInstance(getContext()).saveClientLoginCity(
                                response.body().getData().getUser().getRegion().getCity().getId(),
                                response.body().getData().getUser().getRegion().getId());

                        HelperMethod.replaceFragments(new OrderDetailsFragment(),
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                                R.id.Request_food_Activity_FL, null, null);
                    }else {
                        HelperMethod.showToast(getContext(),"NO:" + response.body().getMsg());
                    }
                } catch (Exception e){
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClientLogin> call, @NonNull Throwable t) {
                HelperMethod.showToast(getContext(),"NO:" + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ButtonSignIn, R.id.linearLayoutForgotPassword, R.id.ButtonSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ButtonSignIn:
                extractUserInput();
                break;

            case R.id.linearLayoutForgotPassword:
                HelperMethod.replaceFragments(new ForgotPasswordFragment(), Objects.requireNonNull(getActivity())
                                .getSupportFragmentManager(), R.id.Request_food_Activity_FL, null, null);
                break;

            case R.id.ButtonSignUp:
                if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1){
                    HelperMethod.replaceFragments(new RegisterClientFragment(), Objects.requireNonNull(getActivity())
                                    .getSupportFragmentManager(), R.id.Request_food_Activity_FL, null, null);
                }

                if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2){
                    HelperMethod.replaceFragments(new RegisterRestaurantFragment(), getActivity().getSupportFragmentManager(),
                            R.id.Request_food_Activity_FL, null, null);
                }
                break;
        }
    }
}
