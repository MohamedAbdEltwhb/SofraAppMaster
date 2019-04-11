package com.example.mm.sofraappmaster.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.locle.shared.SharedPrefManagerClient;
import com.example.mm.sofraappmaster.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splashImageLogo)
    ImageView splashImageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.splashBtnBuyFood, R.id.splashBtnSellFood, R.id.splashBtnTwitter, R.id.splashBtnInstagrame})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.splashBtnBuyFood:
                HelperMethod.intentTo(this, HomeActivity.class);
                SharedPrefManagerClient.getInstance(this).setUserType(1);
                break;

            case R.id.splashBtnSellFood:
                HelperMethod.intentTo(this, HomeActivity.class);
                SharedPrefManagerClient.getInstance(this).setUserType(2);
                break;

            case R.id.splashBtnTwitter:

                break;

            case R.id.splashBtnInstagrame:
                break;
        }
    }
}
