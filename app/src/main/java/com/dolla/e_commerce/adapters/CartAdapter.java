package com.dolla.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.model.Product;
import com.dolla.e_commerce.utils.Constants;
import com.dolla.e_commerce.utils.Utilities;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {

    private final List<Product> cartList;
    private final Context context;

    public CartAdapter(@NonNull Context context, List<Product> cartList) {
        super(context, R.layout.cart_item_layout, cartList);
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cart_item_layout, parent, false);

        RoundedImageView rivImageCart = rowView.findViewById(R.id.rounded_image_view);
        TextView tvTitle = rowView.findViewById(R.id.tv_title);
        TextView tvSubTitle = rowView.findViewById(R.id.tv_sub_title);
        TextView tvPrice = rowView.findViewById(R.id.tv_price);
        TextView tvAmount = rowView.findViewById(R.id.tv_amount);

        rivImageCart.setImageResource(Utilities.getResourceId(context, cartList.get(position).getProductImages().get(0)));
        tvTitle.setText(cartList.get(position).getProductTitle());
        tvSubTitle.setText(cartList.get(position).getProductSubTitle());

        int price = Integer.parseInt(cartList.get(position).getProductPrice()) * Constants.PRODUCT_CART_AMOUNT.get(position);
        String priceString = "E£ " + price + ".00";
        tvPrice.setText(priceString);
        tvAmount.setText(String.valueOf(Constants.PRODUCT_CART_AMOUNT.get(position)));

        return rowView;
    }
}
