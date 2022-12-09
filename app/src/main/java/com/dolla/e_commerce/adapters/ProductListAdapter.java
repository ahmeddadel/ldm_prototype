package com.dolla.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.model.Product;
import com.dolla.e_commerce.utils.Utilities;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private final List<Product> products;
    ItemClicked activity;
    Context context;

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public ProductListAdapter(Context context, List<Product> products) {
        // get list of products
        this.products = products;
        // get activity
        activity = (ItemClicked) context;
        // get context
        this.context = context;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        // declare views
        RoundedImageView rivImage;
        TextView tvTitle, tvSubTitle, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize views
            rivImage = itemView.findViewById(R.id.rounded_image_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubTitle = itemView.findViewById(R.id.tv_sub_title);
            tvPrice = itemView.findViewById(R.id.tv_price);

            // set on click listener
            itemView.setOnClickListener(view -> activity.onItemClicked(products.indexOf((Product) view.getTag())));
        }

        private void setProductImage(String imageId) {
            rivImage.setImageResource(Utilities.getResourceId(context, imageId));
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // set product attributes
        holder.itemView.setTag(products.get(position));
        holder.setProductImage(products.get(position).getProductImages().get(0));
        holder.tvTitle.setText(products.get(position).getProductTitle());
        holder.tvSubTitle.setText(products.get(position).getProductSubTitle());
        String price = "E£ " + products.get(position).getProductPrice() + ".00";
        holder.tvPrice.setText(price);

        // set animation to the view holder item view (card view) when it is created for the first time only
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

