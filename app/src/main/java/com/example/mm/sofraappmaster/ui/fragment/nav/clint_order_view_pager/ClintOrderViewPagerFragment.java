package com.example.mm.sofraappmaster.ui.fragment.nav.clint_order_view_pager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.adapter.ViewPagerTapsAdapter;
import com.example.mm.sofraappmaster.helper.fragment_back_pressed.BackPressedListener;
import com.example.mm.sofraappmaster.ui.activity.HomeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClintOrderViewPagerFragment extends Fragment {


    @BindView(R.id.Order_Fragment_TabLayout_tabs)
    TabLayout OrderFragmentTabLayoutTabs;
    @BindView(R.id.Order_Fragment_ViewPager)
    ViewPager OrderFragmentViewPager;
    Unbinder unbinder;

    public ClintOrderViewPagerFragment() {
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
                        super.doBack();
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
        View view = inflater.inflate(R.layout.fragment_clint_order_view_pager, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Set ViewPager */
        setupViewPager(OrderFragmentViewPager);
        OrderFragmentTabLayoutTabs.setupWithViewPager(OrderFragmentViewPager);

        return view;
    }

    /* Add Fragments to Tabs */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerTapsAdapter adapter = new ViewPagerTapsAdapter(getChildFragmentManager());
        adapter.addFragment(new CurrentOrdersFragment(), getResources().getString(R.string.tap_current_orders));
        adapter.addFragment(new PreviousOrdersFragment(), getResources().getString(R.string.tap_completed_orders));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
