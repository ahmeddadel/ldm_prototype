package com.dolla.e_commerce.ui.productdetails;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.dolla.e_commerce.R;
import com.dolla.e_commerce.model.Product;
import com.dolla.e_commerce.ui.fraghost.HomeActivity;
import com.dolla.e_commerce.utils.Constants;
import com.dolla.e_commerce.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageSlider imageSlider;
    private Button btMinus, btPlus, btAddToCart;
    private RadioGroup radioGroup;
    private RadioButton rBtSmall, rBtMedium, rBtLarge, rBtXLarge, rBtOneSize;
    private TextView tvTitle, tvSubTitle, tvAmount, tvDescription, tvTotalPrice;

    private int amount = 1;
    private int price;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialize the views
        ivBack = findViewById(R.id.button_back);
        imageSlider = findViewById(R.id.image_slider);
        tvTitle = findViewById(R.id.tv_title);
        tvSubTitle = findViewById(R.id.tv_sub_title);
        btMinus = findViewById(R.id.bt_minus);
        tvAmount = findViewById(R.id.tv_amount);
        btPlus = findViewById(R.id.bt_plus);
        radioGroup = findViewById(R.id.radio_group);
        rBtSmall = findViewById(R.id.radio_button_small);
        rBtMedium = findViewById(R.id.radio_button_medium);
        rBtLarge = findViewById(R.id.radio_button_large);
        rBtXLarge = findViewById(R.id.radio_button_xlarge);
        rBtOneSize = findViewById(R.id.radio_button_one_size);
        tvDescription = findViewById(R.id.tv_description);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btAddToCart = findViewById(R.id.bt_add_to_cart);

        // Get the product from the intent
        Product product = Constants.PRODUCTS_LIST.get(getIntent().getIntExtra(HomeActivity.PRODUCT_POSITION_INTENT, -1));

        // set click listener
        ivBack.setOnClickListener(view -> finish());
        btMinus.setOnClickListener(view -> changeAmount(-1));
        btPlus.setOnClickListener(view -> changeAmount(1));
        btAddToCart.setOnClickListener(view -> addProductToCart(product));

        // Set the data to the views
        tvTitle.setText(product.getProductTitle());
        tvSubTitle.setText(product.getProductSubTitle());
        tvAmount.setText(String.valueOf(amount));
        tvDescription.setText(product.getProductDescription());
        price = Integer.parseInt(product.getProductPrice());
        tvTotalPrice.setText("E£ " + price + ".00");

        // set context
        context = getApplicationContext();

        // set the image slider
        setImagesSlider(product.getProductImages());

        // radio buttons visibility
        setRadioButtonsVisibility(product.getProductSize());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to prevent memory leak
        context = null;
        imageSlider.stopSliding();
    }

    private void setImagesSlider(List<String> productImages) {
        ArrayList<SlideModel> imageList = new ArrayList<>();
        for (String image : productImages)
            imageList.add(new SlideModel(Utilities.getResourceId(context, image), null));
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);
    }

    private void setRadioButtonsVisibility(List<String> productSize) {
        for (String size : productSize) {
            switch (size) {
                case "S":
                    rBtSmall.setVisibility(View.VISIBLE);
                    break;
                case "M":
                    rBtMedium.setVisibility(View.VISIBLE);
                    break;
                case "L":
                    rBtLarge.setVisibility(View.VISIBLE);
                    break;
                case "XL":
                    rBtXLarge.setVisibility(View.VISIBLE);
                    break;
                case "O":
                    rBtOneSize.setVisibility(View.VISIBLE);
                    rBtOneSize.setChecked(true);
                    break;
            }
        }
    }

    // Change the amount of the product and the total price
    private void changeAmount(int i) {
        if (i > 0) {
            amount++;
            tvTotalPrice.setText("E£ " + (price * amount) + ".00");
        } else if (amount > 1) {
            amount--;
            tvTotalPrice.setText("E£ " + (price * amount) + ".00");
        }
        tvAmount.setText(String.valueOf(amount));
    }

    // Add the product to the cart list
    private void addProductToCart(Product product) {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            Constants.PRODUCTS_CART_LIST.add(product);
            Constants.PRODUCT_CART_AMOUNT.add(amount);
            Toasty.success(context, "Added to Cart", Toast.LENGTH_SHORT, true).show();
            finish();
        } else
            Toasty.warning(context, "Please select a size", Toast.LENGTH_SHORT, true).show();
    }
}