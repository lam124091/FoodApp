package com.example.finaltermproject.Adapter;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

public class ListviewCategoryAdapter extends ArrayAdapter<Category>
{
    Activity activity;
    int resourceid;
    private List<Category> CATEGORY;

    public ListviewCategoryAdapter(@NonNull Activity activity, int resourceid, List<Category> CATEGORY)
    {
        super(activity, resourceid, CATEGORY);
        this.activity = activity;
        this.resourceid = resourceid;
        this.CATEGORY = CATEGORY;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        // View này được sử dụng lại, chỉ việc cập nhật lại nội dung mới
        // Nếu null cần tạo mới
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(resourceid, null);
            //convertView = LayoutInflater.from(activity).inflate(resourceid, null);

        TextView txtCategoryName = convertView.findViewById(R.id.txtCategoryName);

        Category category = CATEGORY.get(position);
        txtCategoryName.setText(category.getCategoryname());

        return convertView;
    }
}
