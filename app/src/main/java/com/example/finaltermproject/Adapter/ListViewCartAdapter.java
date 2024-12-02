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

public class ListViewCartAdapter extends ArrayAdapter<LineItem>
{
    private TextView txtTotalCart;
    private Cart cart;
    public ListViewCartAdapter(Context context, ArrayList<LineItem> lineItems, TextView txtTotalCart, Cart cart) {
        super(context, 0, lineItems);
        this.txtTotalCart = txtTotalCart;
        this.cart = cart; // Gán giá trị cho biến cart
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LineItem lineItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item_layout, parent, false);
        }

        TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtCategory = convertView.findViewById(R.id.txtCategory);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        ImageView imgProduct = convertView.findViewById(R.id.imgFood);
        TextView txtToTalPrice = convertView.findViewById(R.id.txtTotalPrice);
        ImageButton btnTang = convertView.findViewById(R.id.btnTang);
        ImageButton btnGiam = convertView.findViewById(R.id.btnGiam);
        ImageButton btnXoa = convertView.findViewById(R.id.btnXoa);

        txtFoodName.setText("Tên món ăn: " + lineItem.getFood().getFoodname());
        double totalPrice = lineItem.getTotal();
        txtPrice.setText("Giá: " + lineItem.getFood().getPrice());
        txtCategory.setText("Phân loại: " + lineItem.getFood().getFoodcategory().getCategoryname());
        imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(lineItem.getFood().getImage(), 0, lineItem.getFood().getImage().length));
        txtQuantity.setText("Quantity: " + lineItem.getQuantity());
        txtToTalPrice.setText("Tổng tiền: " + totalPrice);

        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = lineItem.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    lineItem.setQuantity(quantity);
                    txtQuantity.setText("Quantity: " + quantity);

                    // Cập nhật số lượng trong database
                    LineItemDAO lineItemDAO = new LineItemDAO(getContext());
                    lineItemDAO.updateLineItemQuantity(lineItem);

                    double totalPrice = lineItem.getTotal();
                    txtToTalPrice.setText("Tổng tiền: " + totalPrice);
                    updateTotalPrice();
                }
            }
        });

        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = lineItem.getQuantity();
                quantity++;
                lineItem.setQuantity(quantity);
                txtQuantity.setText("Quantity: " + quantity);

                // Cập nhật số lượng trong database
                LineItemDAO lineItemDAO = new LineItemDAO(getContext());
                lineItemDAO.updateLineItemQuantity(lineItem);

                // Tính toán và cập nhật lại giá tiền
                double totalPrice = lineItem.getTotal();
                txtToTalPrice.setText("Tổng tiền: " + totalPrice);
                updateTotalPrice();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa lineitem từ database
                LineItemDAO lineItemDAO = new LineItemDAO(getContext());
                lineItemDAO.deleteLineItem(lineItem);

                // Xóa lineitem khỏi danh sách hiển thị
                remove(lineItem);
                notifyDataSetChanged();updateTotalPrice();
            }
        });

        return convertView;
    }
    private void updateTotalPrice()
    {
        if (cart != null && cart.getCount() > 0)
        {
            double totalAmount = cart.calculateTotalAmount();
            String totalAmountFormatted = String.format("Tổng giá: %.0fđ", totalAmount);
            txtTotalCart.setText(totalAmountFormatted);
        } else
            txtTotalCart.setText("Tổng giá: 0");
    }
}
