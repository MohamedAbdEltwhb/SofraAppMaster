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
import com.example.mm.sofraappmaster.data.model.user.clint.client_reset_password.ClientResetPassword;
import com.example.mm.sofraappmaster.data.rest.RetrofitClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.UserInputValidation;

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
public class ForgotPasswordFragment extends Fragment {


    @BindView(R.id.ForgotPasswordEmile)
    EditText ForgotPasswordEmile;
    Unbinder unbinder;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void extractInput(){
        String email = ForgotPasswordEmile.getText().toString();
        if(!UserInputValidation.isValidMail(email)){
            ForgotPasswordEmile.setError("Please Enter Correct Email..");

        }else {
            if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 1){
                clintResetPassword(email);
            }

            if (SharedPrefManagerClient.getInstance(getContext()).getUserType() == 2){
                //restaurantResetPassword(email);
            }
        }
    }

    private void clintResetPassword(String email){
        Call<ClientResetPassword> clientResetPasswordCall = RetrofitClient.getInstance().getApiServices().clientResetPassword(email);
        clientResetPasswordCall.enqueue(new Callback<ClientResetPassword>() {
            @Override
            public void onResponse(@NonNull Call<ClientResetPassword> call, @NonNull Response<ClientResetPassword> response) {
                try {
                    if (response.body().getStatus() == 1){
                        HelperMethod.showToast(getContext(), response.message());
                    }else {
                        HelperMethod.showToast(getContext(), response.message());
                    }
                }catch (Exception e){
                    HelperMethod.showToast(getContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClientResetPassword> call, @NonNull Throwable t) {

            }
        });
    }

    private void restaurantResetPassword(String email){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgotPasswordSend)
    public void onViewClicked() {
        extractInput();
    }
}
