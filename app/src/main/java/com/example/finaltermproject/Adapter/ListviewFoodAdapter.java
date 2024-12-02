//package com.example.finaltermproject.Adapter;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.finaltermproject.R;
//import com.example.finaltermproject.model.Food;
//
//import java.util.List;
//
//public class ListviewFoodAdapter extends BaseAdapter
//{
//    Activity activity;
//    List<Food> FOOD;
//
//    public ListviewFoodAdapter(Activity activity, List<Food> FOOD)
//    {
//        this.activity = activity;
//        this.FOOD = FOOD;
//    }
//
//    @Override
//    public int getCount() {
//        return FOOD.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return FOOD.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return FOOD.get(position).getFoodid();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        if (convertView == null)
//        {
//            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.admin_food_item_layout, parent, false);
//        }
//
//        ImageView imgFood = (ImageView) convertView.findViewById(R.id.imgFood);
//        TextView txtName = (TextView) convertView.findViewById(R.id.txtCustomerName);
//        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
//
//        Food food = (Food) getItem(position);
//        txtName.setText(food.getFoodname());
//        txtPrice.setText(String.format("Giá %.2f", food.getPrice()));
//        Bitmap bmFood = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);
//        imgFood.setImageBitmap(bmFood);
//
//        return convertView;
//    }
//}


package com.example.finaltermproject.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finaltermproject.DAO.FoodDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Food;

import java.util.List;

public class ListviewFoodAdapter extends BaseAdapter
{
    Activity activity;
    List<Food> FOOD;
    private ListviewFoodAdapter adapter;


    public ListviewFoodAdapter(Activity activity, List<Food> FOOD)
    {
        this.activity = activity;
        this.FOOD = FOOD;
        this.adapter = this;
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
            convertView = inflater.inflate(R.layout.admin_food_item_layout, parent, false);
        }

        ImageView imgFood = (ImageView) convertView.findViewById(R.id.imgFood);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtCustomerName);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
        Button btnXoa = (Button) convertView.findViewById(R.id.btnXoa);
        Button btnSua = (Button) convertView.findViewById(R.id.btnSua);

        Food food = (Food) getItem(position);
        txtName.setText(food.getFoodname());
        txtPrice.setText(String.format("Giá %.2f", food.getPrice()));
        Bitmap bmFood = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);
        imgFood.setImageBitmap(bmFood);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy đối tượng Food tương ứng với vị trí được click
                Food food = (Food) getItem(position);
                // Gọi hàm xóa món ăn
                FoodDAO foodDAO = new FoodDAO(activity);
                boolean result = foodDAO.xoaFood(food.getFoodid());
                if (result) {
                    // Cập nhật danh sách FOOD sau khi xóa thành công
                    FOOD.remove(food);

                    // Cập nhật lại ListView sau khi xóa
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }
}
