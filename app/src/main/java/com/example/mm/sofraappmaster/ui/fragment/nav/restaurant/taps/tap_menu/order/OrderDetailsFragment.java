package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment {

    private final int PAYMENT_ID_ONE = 1;
    private final int PAYMENT_ID_TWO = 2;


    @BindView(R.id.OrderDetailsEditText_AddNote)
    EditText OrderDetailsEditTextAddNote;
    @BindView(R.id.OrderDetailsDeliveryAddress)
    CairoRegularText OrderDetailsDeliveryAddress;
    @BindView(R.id.OrderDetailsRadioGroup)
    RadioGroup OrderDetailsRadioGroup;
    Unbinder unbinder;

    //var
    private int mPaymentId = 0;
    private int mRestaurantId;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        for (int i = 0; i < OrdersBasketFragment.quantity.size(); i++) {
            HelperMethod.showToast(getContext(), OrdersBasketFragment.quantity.get(i));
        }

        /* Get OrderItem ID Clicked */
        mRestaurantId = RestaurantDetailsFragment.id;
        if (mRestaurantId != 0) {

        }
        String address = SharedPrefManagerClient.getInstance(getContext()).getClientLoginData().getAddress() + " . " +
                SharedPrefManagerClient.getInstance(getContext()).getClientLoginData().getRegionId();
       // HelperMethod.showToast(getContext(), address);
        String Note = OrderDetailsEditTextAddNote.getText().toString();

        String phone = SharedPrefManagerClient.getInstance(getContext()).getClientLoginData().getPhone();
        String name = SharedPrefManagerClient.getInstance(getContext()).getClientLoginData().getName();
        String ApiToken = SharedPrefManagerClient.getInstance(getContext()).getClientApiToken();

        return view;
    }

    private void addNewClientOrder() {
//        Call<ClientNewOrder> clientNewOrderCall = RetrofitClient.getInstance().getApiServices().newClientOrder(
//
//        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.RadioButtonComplaint, R.id.RadioButtonSuggestion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RadioButtonComplaint:
                mPaymentId = PAYMENT_ID_ONE;
                break;

            case R.id.RadioButtonSuggestion:
                mPaymentId = PAYMENT_ID_TWO;
                break;
        }
    }
}
