package com.example.finaltermproject.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finaltermproject.R;
import com.example.finaltermproject.model.InvoiceDetail;

import java.util.ArrayList;

public class ListViewInvoiceDetailAdapter extends ArrayAdapter<InvoiceDetail> {
    public ListViewInvoiceDetailAdapter(Context context, ArrayList<InvoiceDetail> invoiceDetails) {
        super(context, 0, invoiceDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InvoiceDetail invoiceDetail = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.invoice_detail_item_layout, parent, false);
        }

        TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtCategory = convertView.findViewById(R.id.txtCategory);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        ImageView imgProduct = convertView.findViewById(R.id.imgFood);
        TextView txtToTalPrice = convertView.findViewById(R.id.txtTotalPrice);

        txtFoodName.setText("Tên món ăn: " + invoiceDetail.getFood().getFoodname());
        double totalPrice = invoiceDetail.getQuantity() * invoiceDetail.getFood().getPrice();
        txtPrice.setText("Giá: " + invoiceDetail.getFood().getPrice());
        txtCategory.setText("Phân loại: " + invoiceDetail.getFood().getFoodcategory().getCategoryname());
        imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(invoiceDetail.getFood().getImage(), 0, invoiceDetail.getFood().getImage().length));
        txtQuantity.setText("Quantity: " + invoiceDetail.getQuantity());
        txtToTalPrice.setText("Tổng tiền: " + totalPrice);

        return convertView;
    }
}

