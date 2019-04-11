package com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.database.OrderEntity;
import com.example.mm.sofraappmaster.data.locle.database.OrderViewModel;
import com.example.mm.sofraappmaster.helper.HelperMethod;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.BackPressedListener;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;
import com.example.mm.sofraappmaster.ui.custom.custom_fonts.CairoRegularText;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.RestaurantDetailsFragment;
import com.example.mm.sofraappmaster.ui.fragment.nav.restaurant.taps.tap_menu.order.OrderBasketData;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.DESCRIPTION;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.NAME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PHOTO;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.PRICE;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys.TIME;
import static com.example.mm.sofraappmaster.helper.Constants.FragmentsKeys._ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuTapItemDetailsFragment extends Fragment {

    @BindView(R.id.itemFoodFgImage)
    ImageView itemFoodFgImage;
    @BindView(R.id.itemFoodFgTvTittle)
    TextView itemFoodFgTvTittle;
    @BindView(R.id.itemFoodFgTvDec)
    TextView itemFoodFgTvDec;
    @BindView(R.id.itemFoodFgTvPrice)
    TextView itemFoodFgTvPrice;
    @BindView(R.id.itemFoodFgTvPreparingTime)
    TextView itemFoodFgTvPreparingTime;
    @BindView(R.id.itemFoodFgEtSpecial)
    EditText itemFoodFgEtSpecial;
    @BindView(R.id.itemFoodFgEtQuantity)
    TextView itemFoodFgEtQuantity;
    Unbinder unbinder;



    /* Interface to Communication Between {MenuTapItemDetailsFragment & OrdersBasketFragment} */
    private OrderBasketData mOrderBasketData;


    // var
    private int mQuantity = 0;

    private String mPhoto;
    private String mName;
    private String mPrice;
    private int mId;

   // private CairoRegularText mToolBarTitle;

    public MenuTapItemDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Configure Back Pressed Listener Button
//     */
//    public void configureBackPressedListener() {
//        ((HomeActivity) Objects.requireNonNull(getActivity()))
//                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
//                    @Override
//                    public void doBack() {
//                        HelperMethod.replaceFragments(new RestaurantDetailsFragment(), getActivity().getSupportFragmentManager(),
//                                R.id.Request_food_Activity_FL, mToolBarTitle, getString(R.string.foods_menu)
//                        );
//                    }
//                });
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        mOrderBasketData = (OrderBasketData) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        /* Handel Back Pressed Listener Button */
//        configureBackPressedListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_tap_item_details, container, false);
        unbinder = ButterKnife.bind(this, view);

//        /* get Activity ToolBar */
//        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
//        mToolBarTitle = toolbar.findViewById(R.id.Request_Food_Toolbar_Title);

        itemFoodFgEtQuantity.setText(String.valueOf(mQuantity));

        /* Get Food Details */
        Bundle bundle = getArguments();

        mPhoto = bundle.getString(PHOTO);
        mName = bundle.getString(NAME);
        mPrice = bundle.getString(PRICE);
        mId = bundle.getInt(_ID);


        setFoodDetails(
                mPhoto, mName, bundle.getString(DESCRIPTION), mPrice, bundle.getString(TIME)
        );

        return view;
    }

    /**
     * Set Food Details
     *
     * @param photo
     * @param name
     * @param description
     * @param price
     * @param time
     */
    private void setFoodDetails(String photo, String name, String description, String price, String time) {
        Glide.with(getContext())
                .load(photo).into(itemFoodFgImage);
        itemFoodFgTvTittle.setText(name);
        itemFoodFgTvDec.setText(description);
        itemFoodFgTvPrice.setText("السعر \n" + price + "ريال ");
        itemFoodFgTvPreparingTime.setText(getActivity().getResources().getString(R.string.preparingTime) + " " + time + " دقيقه ");
    }

    private void getQuantity(){
        String quantity = itemFoodFgEtQuantity.getText().toString();
        if (Integer.valueOf(quantity) != 0 && !quantity.equals(" ")){

            mOrderBasketData.setBasketData(mPhoto, mName, mPrice, quantity, itemFoodFgEtSpecial.getText().toString(),mId);

        }else {
            HelperMethod.showToast(getContext(), "You Cannot have Less than 1 Order");
        }
    }

    /**
     * Food Decrease Quantity
     */
    private void decrease() {


        if (mQuantity == 0) {
            HelperMethod.showToast(getContext(), "You Cannot have Less than 1 Order");
            return;
        }
        mQuantity --;
        itemFoodFgEtQuantity.setText(String.valueOf(mQuantity));
    }

    /**
     * Food Increase Quantity
     */
    private void increase() {
        if (mQuantity == 50) {
            HelperMethod.showToast(getContext(), "You Cannot have more than 50 Order");
            return;
        }
        mQuantity ++;
        itemFoodFgEtQuantity.setText(String.valueOf(mQuantity));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.itemFoodFgEtAddOne, R.id.itemFoodFgSubOne, R.id.itemFoodFgBtnAddToCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemFoodFgEtAddOne:
                increase();
                break;

            case R.id.itemFoodFgSubOne:
                decrease();
                break;

            case R.id.itemFoodFgBtnAddToCart:
                getQuantity();
                break;
        }
    }
}
