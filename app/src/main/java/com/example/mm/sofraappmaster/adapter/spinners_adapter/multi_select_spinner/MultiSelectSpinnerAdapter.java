package com.example.mm.sofraappmaster.adapter.spinners_adapter.multi_select_spinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mm.sofraappmaster.R;
import com.example.mm.sofraappmaster.data.model.general.spinner.categories.CategoriesDatum;

import java.util.List;


public class MultiSelectSpinnerAdapter extends ArrayAdapter<CategoriesDatum> {

    private List<String> mCategoriesItemsText;
    private List<Integer> mCategoriesItemsId;
    private Context mContext;
    private boolean[] mCategoriesSelected;
    private MultipleSelectCheckBoxChangeListener checkBoxChangeListener;

    public MultiSelectSpinnerAdapter(List<CategoriesDatum> customList, List<String> items, List<Integer> idList,
                                     Context context, MultipleSelectCheckBoxChangeListener listener) {
        super(context, 0, customList);
        this.mCategoriesItemsText = items;
        this.mCategoriesItemsId = idList;
        mCategoriesSelected = new boolean[idList.size()];
        this.mContext = context;
        this.checkBoxChangeListener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_dropdown_item, parent, false);
        }

        getCustomView(position, convertView);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_dropdown_item, parent, false);
        }

        getCustomView(position, convertView);

        return convertView;
    }

    private void getCustomView(final int position, @Nullable View convertView) {
        CategoriesDatum item = getItem(position);

        TextView spinnerText = convertView.findViewById(R.id.dropdown_text);
        final CheckBox spinnerSelection = convertView.findViewById(R.id.dropdown_checkbox);

        if (item != null) {
            spinnerText.setText(item.getName());

            spinnerSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spinnerSelection.isChecked()) {
                        mCategoriesSelected[mCategoriesItemsId.get(position)] = true;
                        checkBoxChangeListener.onMultiSelectOptionSelected(mCategoriesItemsId.get(position),
                                mCategoriesSelected[mCategoriesItemsId.get(position)]);

                    } else {
                        mCategoriesSelected[mCategoriesItemsId.get(position)] = false;
                        checkBoxChangeListener.onMultiSelectOptionSelected(mCategoriesItemsId.get(position),
                                mCategoriesSelected[mCategoriesItemsId.get(position)]);
                    }
                }
            });
        }
    }
}