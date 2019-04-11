package com.example.mm.sofraappmaster.ui.fragment.nav;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.general.connect_us.ConnectUs;
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

import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.COMPLAINT;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.INQUIRY;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.SUGGESTION;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectWithUsFragment extends Fragment {


    @BindView(R.id.ConnectUsEditTextName)
    EditText ConnectUsEditTextName;
    @BindView(R.id.ConnectUsEditTextEmail)
    EditText ConnectUsEditTextEmail;
    @BindView(R.id.ConnectUsEditTextPhone)
    EditText ConnectUsEditTextPhone;
    @BindView(R.id.ConnectUsEditTextContent)
    EditText ConnectUsEditTextContent;
    Unbinder unbinder;

    //var
    public String mConnectUsType;

    public ConnectWithUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect_with_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Extract Input ...
     * Name & Email & phone & content
     */
    private void extractInput(String type) {
        String name = ConnectUsEditTextName.getText().toString();
        String email = ConnectUsEditTextEmail.getText().toString();
        String phone = ConnectUsEditTextPhone.getText().toString();
        String content = ConnectUsEditTextContent.getText().toString();

        if (!UserInputValidation.isValidName(name)) {
            ConnectUsEditTextName.setError("Please Enter Correct Name..");

        } else if (!UserInputValidation.isValidMail(email)) {
            ConnectUsEditTextEmail.setError("Please Enter Correct Email..");

        } else if (!UserInputValidation.isValidMobile(phone)) {
            ConnectUsEditTextPhone.setError("Please Enter Correct Phone Number..");

        } else if (content.length() < 500) {
            ConnectUsEditTextContent.setError("Maximum 500 Characters");

        } else {
            connectWithUsCall(name, email, phone, type, content);
        }
    }


    /**
     * Connect With Us Use API Call
     */
    private void connectWithUsCall(String name, String email, String phone, String type, String content) {
        Call<ConnectUs> connectUsCall = RetrofitClient.getInstance().getApiServices()
                .connectUsCall(name, email, phone, type, content);

        connectUsCall.enqueue(new Callback<ConnectUs>() {
            @Override
            public void onResponse(@NonNull Call<ConnectUs> call, @NonNull Response<ConnectUs> response) {
                if (response.body().getStatus() == 1) {
                    HelperMethod.showToast(getContext(), response.body().getMsg());

                } else {
                    HelperMethod.showToast(getContext(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConnectUs> call, @NonNull Throwable t) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.RadioButtonComplaint, R.id.RadioButtonSuggestion, R.id.RadioButtonInquiry, R.id.ButtonSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RadioButtonComplaint:
                mConnectUsType = COMPLAINT;
                break;

            case R.id.RadioButtonSuggestion:
                mConnectUsType = SUGGESTION;
                break;

            case R.id.RadioButtonInquiry:
                mConnectUsType = INQUIRY;
                break;

            case R.id.ButtonSend:
                if (mConnectUsType != null)
                    extractInput(mConnectUsType);
                break;
        }
    }

}
