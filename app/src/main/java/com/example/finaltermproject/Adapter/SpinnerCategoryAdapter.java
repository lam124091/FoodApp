package com.example.finaltermproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Category;

import java.util.List;

public class SpinnerCategoryAdapter extends ArrayAdapter<Category>
{
    private Context context;
    private List<Category> CATEGORY;

    public SpinnerCategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> CATEGORY)
    {
        super(context, resource, CATEGORY);
        this.context = context;
        this.CATEGORY = CATEGORY;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);

        Category category = CATEGORY.get(position);

        TextView txtCategory = convertView.findViewById(android.R.id.text1);
        txtCategory.setText(category.getCategoryname());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        Category category = CATEGORY.get(position);

        TextView txtCategory = convertView.findViewById(android.R.id.text1);
        txtCategory.setText(category.getCategoryname());

        return convertView;
    }
}
