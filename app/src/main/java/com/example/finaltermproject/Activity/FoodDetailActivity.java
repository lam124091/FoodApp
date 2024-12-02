package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltermproject.Adapter.RecyclerViewSizeAdapter;
import com.example.finaltermproject.Adapter.RecyclerViewSizeCustomerAdapter;
import com.example.finaltermproject.DAO.CartDAO;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.DAO.LineItemDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Cart;
import com.example.finaltermproject.model.Category;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Food;
import com.example.finaltermproject.model.LineItem;
import com.example.finaltermproject.model.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity
{
    RecyclerViewSizeCustomerAdapter rclsizecusadapter;
    private List<Size> SIZE = new ArrayList<>();
    ArrayList<Integer> ALLSIZEID;

    CustomerDAO customerDAO;

    CartDAO cartDAO;
    LineItemDAO lineItemDAO;

    ImageView imgFood;
    TextView txtFoodName, txtPrice, txtDescription, txtFoodCategory;
    RecyclerView rclFoodSize;
    CheckBox chkLikes;
    Button btnAddToCart;

    private Food selectedfood;
    private List<Size> selectedSize;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        getFormWidgets();

        Intent intent = getIntent();
        if (intent != null)
        {
            int foodid = intent.getIntExtra("foodid", 0);
            String foodName = intent.getStringExtra("foodname");
            double price = intent.getDoubleExtra("price", 0.0);
            String description = intent.getStringExtra("description");
            int likes = intent.getIntExtra("likes", 0);
            //String categoryName = intent.getStringExtra("categoryname");
            Category category = (Category) intent.getSerializableExtra("foodcategory");
            byte[] IMAGE = intent.getByteArrayExtra("image");
            List<Size> SIZE = (List<Size>) intent.getSerializableExtra("SIZE");

            txtFoodName.setText(foodName);
            txtPrice.setText(String.valueOf(price));
            txtDescription.setText(description);
            chkLikes.setText(String.valueOf(likes));
            txtFoodCategory.setText(category.getCategoryname());
            Bitmap bitmap = BitmapFactory.decodeByteArray(IMAGE, 0, IMAGE.length);
            imgFood.setImageBitmap(bitmap);

            rclFoodSize.setLayoutManager(new LinearLayoutManager(this));
            rclsizecusadapter = new RecyclerViewSizeCustomerAdapter(SIZE, new RecyclerViewSizeCustomerAdapter.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(List<Size> selectedSizes)
                {
                    Food food = new Food(foodid ,foodName, price, description, likes, category, selectedSizes, IMAGE);

                    int numberOfElements = selectedSizes.size();
                    System.out.println("Số lượng phần tử trong selectedSizes là: " + numberOfElements);
                    Size size = selectedSizes.get(0);
                    int sizeId = size.getSizeid();
                    System.out.println("Size ID: " + sizeId);

                    selectedfood = food;
                    selectedSize = selectedSizes;

                }
            });
            rclFoodSize.setAdapter(rclsizecusadapter);
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                processAddToCart();

            }
        });

        addEventsForWidgets();
    }

//    private void processAddToCart()
//    {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart)
//        {
//            Toast.makeText(FoodDetailActivity.this, "Tạo giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//        Cart cart = cartDAO.getCartByCustomer2(customer);
//
////        getAllSizeIds(cart);
////        System.out.println("Danh sách sizeid: " + ALLSIZEID);
//
//        //ArrayList<Integer> allSizeIds = getAllSizeIds(cart);
//
////        boolean foundSimilarItem = false;
////        for (LineItem item : cart.getLINEITEM())
////        {
////            if (item.getFood().getFoodid() == selectedfood.getFoodid())
////            {
////                if (checkSizeIdExists(selectedSize.get(0).getSizeid(), allSizeIds))
////                {
////                    item.setQuantity(item.getQuantity() + 1);
////                    lineItemDAO.updateLineItemQuantity(item);
////                   foundSimilarItem = true;
////                   break;
////                }
////            }
////        }
//
//        HashSet<Integer> allSizeIds = getAllSizeIds(cart);
//        System.out.println("Danh sách Size ID: " + allSizeIds);
//
//        boolean foundSimilarItem = false;
//        for (LineItem item : cart.getLINEITEM())
//        {
//            if (item.getFood().getFoodid() == selectedfood.getFoodid())
//            {
//                List<Size> itemSizes = item.getFood().getFoodsize();
//                for (Size itemSize : itemSizes)
//                {
//                    if (allSizeIds.contains(itemSize.getSizeid()))
//                    {
//                        if (itemSize.getSizeid() == selectedSize.get(0).getSizeid())
//                        {
//                            item.setQuantity(item.getQuantity() + 1);
//                            lineItemDAO.updateLineItemQuantity(item);
//                            foundSimilarItem = true;
//                            break;
//                        }
//                    }
//                }
//            }
//            if (foundSimilarItem)
//            {
//                break;
//            }
//        }
//
//        if (!foundSimilarItem)
//        {
//            LineItem lineItem = new LineItem(selectedfood, 1);
//            lineItemDAO.insertLineItem(lineItem);
//            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//            lineItemDAO.insertCartLineItem(cartId, lineItemId);
//        }
//        Toast.makeText(FoodDetailActivity.this, "Thêm cart thanh cong", Toast.LENGTH_SHORT).show();
//    }
//
//    private boolean checkSizeIdExists(int sizeId, ArrayList<Integer> allSizeIds)
//    {
//        return allSizeIds.contains(sizeId);
//    }
//
//    private HashSet<Integer> getAllSizeIds(Cart cart) {
//        HashSet<Integer> allSizeIds = new HashSet<>();
//        for (LineItem item : cart.getLINEITEM()) {
//            List<Size> foodSizes = item.getFood().getFoodsize();
//            for (Size size : foodSizes) {
//                allSizeIds.add(size.getSizeid());
//            }
//        }
//        return allSizeIds;
//    }


//    private ArrayList<Integer> getAllSizeIds(Cart cart) {
//        ArrayList<Integer> allSizeIds = new ArrayList<>();
//        for (LineItem item : cart.getLINEITEM()) {
//            List<Size> foodSizes = item.getFood().getFoodsize();
//            for (Size size : foodSizes) {
//                allSizeIds.add(size.getSizeid());
//            }
//        }
//        return allSizeIds;
//    }


//    private ArrayList<Integer> getAllSizeIds(Cart cart) {
//        ArrayList<Integer> allSizeIds = new ArrayList<>();
//        for (LineItem item : cart.getLINEITEM()) {
//            Food food = item.getFood();
//            Size size = food.getFoodsize().get(0); // Lấy phần tử đầu tiên trong danh sách foodsize
//            allSizeIds.add(size.getSizeid());
//        }
//        return allSizeIds;
//    }


//    public boolean check(int sizeIdToCheck)
//    {
//        return ALLSIZEID.contains(sizeIdToCheck);
//    }
//

    private void processAddToCart() {
        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        String email = sharedpreferences.getString("email", "");

        customerDAO = new CustomerDAO(FoodDetailActivity.this);
        Customer customer = customerDAO.layCustomer(email);
        cartDAO = new CartDAO(FoodDetailActivity.this);
        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);

        boolean hasCart = cartDAO.isCartExitsts(customer);
        if (!hasCart) {
            cartDAO.themCartChoCustomer(customer);
        }

        int cartId = cartDAO.getCartIdByCustomer(customer);
        Cart cart = cartDAO.getCartByCustomer2(customer);

        boolean foundSimilarItem = false;
        for (LineItem item : cart.getLINEITEM()) {
            // Kiểm tra xem mỗi mục hàng đã tồn tại trong giỏ hàng chưa
            if (item.getFood().getFoodid() == selectedfood.getFoodid() && containsSize(item.getFood().getFoodsize(), selectedSize))
            {
                // Nếu đã tồn tại, không tạo LineItem mới, chỉ tăng số lượng
                   item.setQuantity(item.getQuantity() + 1);
                   lineItemDAO.updateLineItemQuantity(item);
                   foundSimilarItem = true;
                   break;
            }

        }


        if (!foundSimilarItem) {
            // Nếu không tìm thấy, tạo một LineItem mới với số lượng là 1
            LineItem newLineItem = new LineItem(selectedfood, 1);
            lineItemDAO.insertLineItem(newLineItem);
            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
            lineItemDAO.insertCartLineItem(cartId, lineItemId);
        }

        Toast.makeText(FoodDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
    }

//    private void processAddToCart() {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart) {
//            Toast.makeText(FoodDetailActivity.this, "Tạo giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//
//        Cart cart = cartDAO.getCartByCustomer2(customer);
//        boolean foundSimilarItem = false;
//        for (LineItem item : cart.getLINEITEM()) {
//            if (item.getFood().getFoodid() == selectedfood.getFoodid()) {
//                // Tìm thấy sản phẩm tương tự (cùng foodid) trong giỏ hàng
//                // Kiểm tra xem size có trùng khớp không
//                for (Size size : selectedSize) {
//                    if (lineItemDAO.existsLineItemWithFoodAndSize(selectedfood.getFoodid(), size.getSizeid())) {
//                        // Tìm thấy sản phẩm tương tự (cùng foodid và size) trong giỏ hàng
//                        item.setQuantity(item.getQuantity() + 1);
//                        lineItemDAO.updateLineItemQuantity(item); // Cập nhật số lượng trong bảng lineitem
//                        foundSimilarItem = true;
//                        break;
//                    }
//                }
//                if (foundSimilarItem) {
//                    break;
//                }
//            }
//        }
//
//        if (!foundSimilarItem) {
//            // Không tìm thấy sản phẩm tương tự trong giỏ hàng, thêm mới vào bảng lineitem
//            for (Size size : selectedSize) {
//                LineItem lineItem = new LineItem(selectedfood, 1);
//                lineItemDAO.insertLineItem(lineItem);
//
//                // Lấy id của line item vừa thêm vào
//                int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//
//                // Thêm vào bảng cart_lineitem
//                lineItemDAO.insertCartLineItem(cartId, lineItemId);
//            }
//        }
//
//        Toast.makeText(FoodDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//    }


//    private void processAddToCart() {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart) {
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//        Cart cart = cartDAO.getCartByCustomer2(customer);
//
//        // Tìm LineItem tương ứng trong giỏ hàng
//        LineItem existingLineItem = null;
//        for (LineItem item : cart.getLINEITEM()) {
//            if (item.getFood().getFoodid() == selectedfood.getFoodid() && item.getFood().getFoodsize().get(0).getSizeid() == selectedSize.get(0).getSizeid()) {
//                existingLineItem = item;
//                break;
//            }
//        }
//
//        if (existingLineItem != null) {
//            // Tăng số lượng của LineItem hiện có
//            existingLineItem.setQuantity(existingLineItem.getQuantity() + 1);
//            lineItemDAO.updateLineItemQuantity(existingLineItem);
//        } else {
//            // Tạo một LineItem mới với số lượng là 1
//            LineItem newLineItem = new LineItem(selectedfood, 1);
//            lineItemDAO.insertLineItem(newLineItem);
//            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//            lineItemDAO.insertCartLineItem(cartId, lineItemId);
//        }
//
//        Toast.makeText(FoodDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//    }


//    private void processAddToCart() {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart) {
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//        Cart cart = cartDAO.getCartByCustomer2(customer);
//
//        // Tìm LineItem tương ứng trong giỏ hàng
//        LineItem existingLineItem = null;
//        for (LineItem item : cart.getLINEITEM()) {
//            if (item.getFood().getFoodid() == selectedfood.getFoodid() && item.getFood().getFoodsize().get(0).getSizeid() == selectedSize.get(0).getSizeid()) {
//                existingLineItem = item;
//                break;
//            }
//        }
//
//        if (existingLineItem != null) {
//            // Tăng số lượng của LineItem hiện có
//            existingLineItem.setQuantity(existingLineItem.getQuantity() + 1);
//            lineItemDAO.updateLineItemQuantity(existingLineItem);
//        } else {
//            // Tạo một LineItem mới với số lượng là 1
//            LineItem newLineItem = new LineItem(selectedfood, 1);
//            lineItemDAO.insertLineItem(newLineItem);
//            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//            lineItemDAO.insertCartLineItem(cartId, lineItemId);
//        }
//
//        // Kiểm tra xem có LineItem nào khớp với Food ID và Size ID khác không
//        for (LineItem item : cart.getLINEITEM()) {
//            if (item.getFood().getFoodid() == selectedfood.getFoodid() && item.getFood().getFoodsize().get(0).getSizeid() != selectedSize.get(0).getSizeid()) {
//                // Tăng số lượng của LineItem đó
//                item.setQuantity(item.getQuantity() + 1);
//                lineItemDAO.updateLineItemQuantity(item);
//            }
//        }
//
//        Toast.makeText(FoodDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//    }




    //    private void processAddToCart() {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart) {
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//        Cart cart = cartDAO.getCartByCustomer2(customer);
//
//        // Kiểm tra xem đã có line item tương tự trong giỏ hàng chưa
//        boolean foundSimilarItem = false;
//        for (LineItem item : cart.getLINEITEM()) {
//            // Kiểm tra xem foodid và size có trùng khớp không
//            if (item.getFood().getFoodid() == selectedfood.getFoodid() && containsSize(item.getFood().getFoodsize(), selectedSize)) {
//                // Nếu đã tìm thấy, chỉ cập nhật số lượng
//                item.setQuantity(item.getQuantity() + 1); // Tăng số lượng lên 1
//                lineItemDAO.updateLineItemQuantity(item); // Cập nhật số lượng trong bảng lineitem
//                foundSimilarItem = true;
//                break;
//            }
//        }
//
//        // Nếu chưa có line item tương tự, thêm mới
//        if (!foundSimilarItem) {
//            // Thêm line item với số lượng là số lần chọn
//            for (Size size : selectedSize) {
//                LineItem newLineItem = new LineItem(selectedfood, 1); // Tạo mới line item với số lượng là 1
//                lineItemDAO.insertLineItem(newLineItem); // Thêm vào cơ sở dữ liệu
//
//                // Lấy id của line item mới thêm vào
//                int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//
//                // Thêm vào bảng cart_lineitem
//                lineItemDAO.insertCartLineItem(cartId, lineItemId);
//            }
//
//            Toast.makeText(FoodDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//        } else {
//            // Nếu đã có line item tương tự, hiển thị thông báo
//            Toast.makeText(FoodDetailActivity.this, "Mặt hàng đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
    private boolean containsSize(List<Size> foodSizes, List<Size> selectedSizes) {
        // Duyệt qua từng kích thước đã chọn
        for (Size selectedSize : selectedSizes) {
            boolean found = false;
            // Duyệt qua từng kích thước của món ăn
            for (Size foodSize : foodSizes) {
                // Nếu tìm thấy một kích thước trong danh sách kích thước của món ăn
                // có cùng id với kích thước đã chọn, đánh dấu là đã tìm thấy và thoát khỏi vòng lặp trong
                // danh sách kích thước của món ăn
                if (foodSize.getSizeid() == selectedSize.getSizeid()) {
                    found = true;
                    break;
                }
            }
            // Nếu không tìm thấy kích thước đã chọn trong danh sách kích thước của món ăn, trả về false
            if (!found) {
                return false;
            }
        }
        // Nếu mỗi kích thước đã chọn tạo ra một line item riêng trong món ăn, trả về false
        return true;
    }
//
//
//
//
//
//    //    private boolean containsItem(Cart cart, Food food, List<Size> selectedSizes) {
////        for (LineItem item : cart.getLINEITEM()) {
////            // Kiểm tra xem mỗi mặt hàng đã tồn tại trong giỏ hàng với foodid và tất cả các size đã chọn hay chưa
////            if (item.getFood().getFoodid() == food.getFoodid() && containsAllSizes(item.getFood().getFoodsize(), selectedSizes)) {
////                return true;
////            }
////        }
////        return false;
////    }
//    private boolean containsSize(List<Size> sizes, Size selectedSize) {
//        // Kiểm tra xem selectedSize có tồn tại trong danh sách sizes hay không
//        for (Size size : sizes) {
//            if (size.getSizeid() == selectedSize.getSizeid()) {
//                return true; // Nếu tìm thấy, trả về true
//            }
//        }
//        return false; // Nếu không tìm thấy, trả về false
//    }
//
//
//    // Hàm này kiểm tra xem tất cả các size trong danh sách selectedSizes có tồn tại trong danh sách sizes hay không
//    private boolean containsAllSizes(List<Size> sizes, List<Size> selectedSizes) {
//        // Kiểm tra xem selectedSizes có chứa tất cả các size trong sizes hay không
//        for (Size size : sizes) {
//            boolean found = false;
//            for (Size selectedSize : selectedSizes) {
//                if (size.getSizeid() == selectedSize.getSizeid()) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                return false;
//            }
//        }
//        return true;
//    }



//    private void processAddToCart() {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart) {
//            Toast.makeText(FoodDetailActivity.this, "Tạo giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//
//        LineItem lineItem = new LineItem(selectedfood, 1);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
//        Cart cart = cartDAO.getCartByCustomer2(customer);
//        boolean foundSimilarItem = false;
//        for (LineItem item : cart.getLINEITEM()) {
//            if (item.getFood().getFoodid() == selectedfood.getFoodid())
//            {
//                // Tìm thấy sản phẩm tương tự (cùng foodid và size) trong giỏ hàng
//                item.setQuantity(item.getQuantity() + 1);
//                lineItemDAO.updateLineItemQuantity(item); // Cập nhật số lượng trong bảng lineitem
//                foundSimilarItem = true;
//                break;
//            }
//        }
//
//        if (!foundSimilarItem) {
//            // Không tìm thấy sản phẩm tương tự trong giỏ hàng, thêm mới vào bảng lineitem
//            lineItemDAO.insertLineItem(lineItem);
//
//            // Lấy id của line item vừa thêm vào
//            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//
//            // Thêm vào bảng cart_lineitem
//            lineItemDAO.insertCartLineItem(cartId, lineItemId);
//        }
//
//        Toast.makeText(FoodDetailActivity.this, "Thêm cart thanh cong", Toast.LENGTH_SHORT).show();
//    }



//    private void processAddToCart() {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");
//
//        customerDAO = new CustomerDAO(FoodDetailActivity.this);
//        Customer customer = customerDAO.layCustomer(email);
//        cartDAO = new CartDAO(FoodDetailActivity.this);
//
//        boolean hasCart = cartDAO.isCartExitsts(customer);
//        if (!hasCart) {
//            Toast.makeText(FoodDetailActivity.this, "Tạo giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//            cartDAO.themCartChoCustomer(customer);
//        }
//
//        int cartId = cartDAO.getCartIdByCustomer(customer);
//        Cart cart = cartDAO.getCartByCustomer(customer);
//
//        LineItem lineItem = new LineItem(selectedfood, 1);
//        lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
////        boolean foundSimilarItem = false;
////        for (LineItem item : cart.getLINEITEM()) {
////            if (item.getFood().getFoodid() == selectedfood.getFoodid() && checkSizeMatch(item.getFood().getFoodsize(), selectedSize)) {
////                // Tìm thấy sản phẩm tương tự trong giỏ hàng
////                item.setQuantity(item.getQuantity() + 1);
////                lineItemDAO.updateLineItemQuantity(item); // Cập nhật số lượng trong bảng lineitem
////                foundSimilarItem = true;
////                break;
////            }
////        }
////
////        if (!foundSimilarItem) {
////            // Không tìm thấy sản phẩm tương tự trong giỏ hàng, thêm mới vào bảng lineitem
////            lineItemDAO.insertLineItem(lineItem);
////
////            // Lấy id của line item vừa thêm vào
////            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
////
////            // Thêm vào bảng cart_lineitem
////            lineItemDAO.insertCartLineItem(cartId, lineItemId);
////        }
//
////        boolean foundSimilarItem = false;
////        for (LineItem item : cart.getLINEITEM()) {
////            if (item.getFood().getFoodid() == selectedfood.getFoodid())
////            {
////                if (item.getQuantity() == 1)
////                {
////                    // Tìm thấy sản phẩm tương tự trong giỏ hàng với các kích thước đã chọn
////                    item.setQuantity(item.getQuantity() + 1); // Tăng số lượng lên 1
////                    lineItemDAO.updateLineItemQuantity(item); // Cập nhật số lượng trong bảng lineitem
////                    foundSimilarItem = true;
////                    break;
////                }
////            }
////        }
////
////        if (foundSimilarItem == false)
////        {
////            // Không tìm thấy sản phẩm tương tự trong giỏ hàng, thêm mới vào giỏ hàng
////            lineItemDAO.insertLineItem(lineItem);
////
////            // Lấy id của mục hàng vừa thêm vào
////            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
////
////            // Thêm vào bảng cart_lineitem
////            lineItemDAO.insertCartLineItem(cartId, lineItemId);
////        }
//
//        LineItem existingItem = cart.findItemById(selectedfood.getFoodid());
//        if (existingItem != null) {
//            existingItem.setQuantity(existingItem.getQuantity() + 1);
//            lineItemDAO.updateLineItemQuantity(existingItem);
//        } else {
//            lineItemDAO.insertLineItem(lineItem);
//            int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//            lineItemDAO.insertCartLineItem(cartId, lineItemId);
//        }
//
//        Toast.makeText(FoodDetailActivity.this, "Thêm cart thành công", Toast.LENGTH_SHORT).show();
//    }

//private void processAddToCart() {
//    SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//    String email = sharedpreferences.getString("email", "");
//
//    customerDAO = new CustomerDAO(FoodDetailActivity.this);
//    Customer customer = customerDAO.layCustomer(email);
//    cartDAO = new CartDAO(FoodDetailActivity.this);
//
//    boolean hasCart = cartDAO.isCartExitsts(customer);
//    if (!hasCart) {
//        Toast.makeText(FoodDetailActivity.this, "Tạo giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//        cartDAO.themCartChoCustomer(customer);
//    }
//
//    int cartId = cartDAO.getCartIdByCustomer(customer);
//    Cart cart = cartDAO.getCartByCustomer(customer);
//
//    LineItem lineItem = new LineItem(selectedfood, 1);
//    lineItemDAO = new LineItemDAO(FoodDetailActivity.this);
//
//    LineItem existingItem = cart.findItemById(selectedfood.getFoodid());
//    if (existingItem != null) {
//        existingItem.setQuantity(existingItem.getQuantity() + 1);
//        lineItemDAO.updateLineItemQuantity(existingItem);
//    } else {
//        lineItemDAO.insertLineItem(lineItem);
//        int lineItemId = lineItemDAO.getLastInsertedLineItemId();
//        lineItemDAO.insertCartLineItem(cartId, lineItemId);
//    }
//
//    Toast.makeText(FoodDetailActivity.this, "Thêm cart thành công", Toast.LENGTH_SHORT).show();
//}

//    public boolean checkSizeMatch(List<Size> foodSizes, List<Size> selectedSizes)
//    {
//        if (foodSizes.size() != selectedSizes.size()) {
//            return false;
//        }
//
//        // Sử dụng một vòng lặp để so sánh từng cặp sizeid của mỗi food và selectedfood
//        for (int i = 0; i < foodSizes.size(); i++) {
//            Size foodSize = foodSizes.get(i);
//            Size selectedSize = selectedSizes.get(i);
//
//            // Nếu có bất kỳ cặp sizeid nào không khớp, trả về false
//            if (foodSize.getSizeid() != selectedSize.getSizeid()) {
//                return false;
//            }
//        }
//
//        // Nếu tất cả các cặp sizeid đều khớp, trả về true
//        return true;
//    }

    private void getFormWidgets()
    {
        imgFood = findViewById(R.id.imgFood);
        txtFoodName = findViewById(R.id.txtFoodName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtFoodCategory = findViewById(R.id.txtFoodCategory);
        rclFoodSize = findViewById(R.id.rclFoodSize);
        chkLikes = findViewById(R.id.chkLikes);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void addEventsForWidgets()
    {

    }
}