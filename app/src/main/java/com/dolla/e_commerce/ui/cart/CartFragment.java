package com.dolla.e_commerce.ui.cart;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.adapters.CartAdapter;
import com.dolla.e_commerce.utils.Constants;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import es.dmoral.toasty.Toasty;

public class CartFragment extends Fragment {
    private View view;
    private ListView listView;
    private CartAdapter myAdapter;
    private ProgressBar progressBar;
    private TextView emptyCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        listView = view.findViewById(R.id.lv_cart_list);
        progressBar = view.findViewById(R.id.progress_bar);

        setupListView();

        TextView tvTotalPrice = view.findViewById(R.id.tv_total_price);
        Button btPlaceOrder = view.findViewById(R.id.bt_place_order);
        emptyCart = view.findViewById(R.id.tv_empty_cart);

        if (Constants.PRODUCTS_CART_LIST.isEmpty())
            emptyCart.setVisibility(View.VISIBLE);


        tvTotalPrice.setText(totalPriceString());
        btPlaceOrder.setOnClickListener(v -> onPlaceOrderClicked());

        // Inflate the layout for this fragment
        return view;
    }

    private void onPlaceOrderClicked() {
        if (!Constants.PRODUCTS_CART_LIST.isEmpty()) {
            // Simulate a network access
            listView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                // clear cart list
                Constants.PRODUCTS_CART_LIST.clear();
                Constants.PRODUCT_CART_AMOUNT.clear();

                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                emptyCart.setVisibility(View.VISIBLE);
                Toasty.success(requireContext(), "Your order has been placed successfully", Toasty.LENGTH_SHORT, true).show();

                // navigate to home fragment
                ChipNavigationBar bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation);
                bottomNavigation.setItemSelected(R.id.navHome, true);

            }, Constants.DELAY_PERIOD);
        }
    }

    private void setupListView() {
        // initialize list view adapter
        myAdapter = new CartAdapter(requireContext(), Constants.PRODUCTS_CART_LIST);
        // set adapter to list view
        listView.setAdapter(myAdapter);
    }

    private String totalPriceString() {
        if (!Constants.PRODUCTS_CART_LIST.isEmpty() || !Constants.PRODUCT_CART_AMOUNT.isEmpty()) {
            int totalPrice = 0;
            for (int index = 0; index < Constants.PRODUCTS_CART_LIST.size(); index++) {
                totalPrice += Integer.parseInt(Constants.PRODUCTS_CART_LIST.get(index).getProductPrice()) * Constants.PRODUCT_CART_AMOUNT.get(index);
            }
            return "E£ " + totalPrice + ".00";
        }
        return "E£ 0.00";
    }
}