package com.example.finaltermproject.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.finaltermproject.Adapter.SpinnerCategoryAdapter;
import com.example.finaltermproject.Adapter.RecyclerViewSizeAdapter;
import com.example.finaltermproject.Adapter.SpinnerCategoryAdapter;
import com.example.finaltermproject.DAO.CategoryDAO;
import com.example.finaltermproject.DAO.FoodDAO;
import com.example.finaltermproject.DAO.SizeDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Category;
import com.example.finaltermproject.model.Food;
import com.example.finaltermproject.model.Size;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ThemFoodActivity extends AppCompatActivity
{
    private static final int REQUEST_TAKE_PHOTO = 123;
    private static final int REQUEST_CHOOSE_PHOTO = 321;

    SpinnerCategoryAdapter spncategoryadapter;
    private List<Category> CATEGORY = new ArrayList<>();
    CategoryDAO categoryDAO;

    RecyclerViewSizeAdapter rclsizeadapter;
    private List<Size> SIZE = new ArrayList<>();
    private List<Size> SELECTEDSIZE = new ArrayList<>();
    SizeDAO sizeDAO;

    EditText edtFoodName, edtPrice, edtDescription;
    TextView txtLikes;
    Spinner spnCategory;
    RecyclerView rclSize;
    ImageView imgFood;
    Button btnThem, btnXoa, btnSua, btnChonHinh, btnChupHinh, btnHuy;

    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_food);

        getFormWidgets();
        loadCATEGORY();
        loadSize();
        addEventsForWidgets();
    }

    public void getFormWidgets()
    {
        edtFoodName = findViewById(R.id.edtFoodName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        txtLikes = findViewById(R.id.txtLikes);
        spnCategory = findViewById(R.id.spnCategory);
        rclSize = findViewById(R.id.rclFoodSize);
        imgFood = findViewById(R.id.imgFood);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        btnChupHinh = findViewById(R.id.btnChupHinh);
        btnHuy = findViewById(R.id.btnHuy);
    }

    public void loadCATEGORY()
    {
        categoryDAO = new CategoryDAO(this);
        CATEGORY = categoryDAO.layTatCaCategory();

        spncategoryadapter = new SpinnerCategoryAdapter(this, android.R.layout.simple_spinner_item, CATEGORY);
        spncategoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(spncategoryadapter);
    }

    public void loadSize()
    {
        sizeDAO = new SizeDAO(this);
        SIZE = sizeDAO.layTatCaSize();

        rclsizeadapter = new RecyclerViewSizeAdapter(SIZE, new RecyclerViewSizeAdapter.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(Size size, boolean isChecked)
            {
                if (isChecked)
                    SELECTEDSIZE.add(size);
                else
                    SELECTEDSIZE.remove(size);
            }
        });
        rclSize.setLayoutManager(new LinearLayoutManager(this));
        rclSize.setAdapter(rclsizeadapter);
    }

    public void addEventsForWidgets()
    {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInput();
            }
        });

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processChooseImage();
            }
        });

        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTakeImage();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCancel();
            }
        });
    }

    public void processInput()
    {
        String foodName = edtFoodName.getText().toString().trim();
        double price = Double.parseDouble(edtPrice.getText().toString().trim());
        String description = edtDescription.getText().toString().trim();
        int likes = Integer.parseInt(txtLikes.getText().toString().trim());
        List<Size> selectedSizes = SELECTEDSIZE;
        Category foodCategory = (Category) spnCategory.getSelectedItem();
        byte[] IMAGE = getByteArrayFromImageView(imgFood);

        if (selectedSizes.isEmpty())
        {
            Toast.makeText(this, "Vui lòng chọn ít nhất một kích thước", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Food food = new Food(foodName, price, description, likes, foodCategory, selectedSizes, IMAGE);

            FoodDAO foodDAO = new FoodDAO(ThemFoodActivity.this);
            foodDAO.themFood(food);
            Toast.makeText(ThemFoodActivity.this, "thêm food thành công", Toast.LENGTH_SHORT).show();
            processCancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_CHOOSE_PHOTO)
            {
                try
                {
                    Uri uri  = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgFood.setImageBitmap(bitmap);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
                if (requestCode == REQUEST_TAKE_PHOTO)
                {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgFood.setImageBitmap(bitmap);
                }
        }
    }

    public void processChooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CHOOSE_PHOTO);
    }

    public void processTakeImage()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    public void processCancel()
    {
        Intent intent = new Intent(ThemFoodActivity.this, FoodManagerActivity.class);
        startActivity(intent);
    }

    private byte[] getByteArrayFromImageView(ImageView imageView)
    {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
