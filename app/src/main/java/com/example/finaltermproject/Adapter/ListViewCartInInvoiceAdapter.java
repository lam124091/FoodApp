package com.example.finaltermproject.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finaltermproject.DAO.LineItemDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Cart;
import com.example.finaltermproject.model.LineItem;

import java.util.ArrayList;

public class ListViewCartInInvoiceAdapter extends ArrayAdapter<LineItem>
{
    private Cart cart;

    public ListViewCartInInvoiceAdapter(Context context, ArrayList<LineItem> lineItems, Cart cart) {
        super(context, 0, lineItems);
        this.cart = cart; // Gán giá trị cho biến cart
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LineItem lineItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_in_invoice_item_layout, parent, false);
        }

        TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtCategory = convertView.findViewById(R.id.txtCategory);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        ImageView imgProduct = convertView.findViewById(R.id.imgFood);
        TextView txtToTalPrice = convertView.findViewById(R.id.txtTotalPrice);

        txtFoodName.setText("Tên món ăn: " + lineItem.getFood().getFoodname());
        double totalPrice = lineItem.getTotal();
        txtPrice.setText("Giá: " + lineItem.getFood().getPrice());
        txtCategory.setText("Phân loại: " + lineItem.getFood().getFoodcategory().getCategoryname());
        imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(lineItem.getFood().getImage(), 0, lineItem.getFood().getImage().length));
        txtQuantity.setText("Quantity: " + lineItem.getQuantity());
        txtToTalPrice.setText("Tổng tiền: " + totalPrice);

        return convertView;
    }
}
