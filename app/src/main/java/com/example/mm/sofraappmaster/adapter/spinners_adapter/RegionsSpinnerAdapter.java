package com.example.mm.sofraappmaster.adapter.spinners_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.general.spinner.regions.RegionsDatum;

import java.util.List;

public class RegionsSpinnerAdapter extends ArrayAdapter<RegionsDatum> {


    public RegionsSpinnerAdapter(@NonNull Context context, List<RegionsDatum> customList) {
        super(context, 0, customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cusstom_spinner_layout, parent, false);
        }
        RegionsDatum item = getItem(position);

        TextView spinnerTV = convertView.findViewById(R.id.tvSpinnerLayout);
        if (item != null) {
            spinnerTV.setText(item.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout, parent, false);
        }
        RegionsDatum item = getItem(position);

        TextView dropDownTV = convertView.findViewById(R.id.tvDropDownLayout);
        if (item != null) {
            dropDownTV.setText(item.getName());
        }
        return convertView;
    }
}
