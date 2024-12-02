package com.example.finaltermproject.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Food;

import java.util.List;


public class GridViewFoodAdapter extends BaseAdapter
{

    Activity activity;
    List<Food> FOOD;

    public GridViewFoodAdapter(Activity activity, List<Food> FOOD)
    {
        this.activity = activity;
        this.FOOD = FOOD;
    }

    @Override
    public int getCount() {
        return FOOD.size();
    }

    @Override
    public Object getItem(int position) {
        return FOOD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return FOOD.get(position).getFoodid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customer_food_item_layout, parent, false);
        }

        ImageView imgFood = (ImageView) convertView.findViewById(R.id.imgFood);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtCustomerName);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
        TextView txtLikes = (TextView) convertView.findViewById(R.id.txtLikes);

        Food food = (Food) getItem(position);
        txtName.setText(food.getFoodname());
        txtPrice.setText(String.format("Giá %.2f", food.getPrice()));
        Bitmap bmFood = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);
        imgFood.setImageBitmap(bmFood);
        txtLikes.setText(String.valueOf(food.getLikes()));


        return convertView;
    }
}
