package com.dolla.e_commerce.ui.homelist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.adapters.ProductListAdapter;
import com.dolla.e_commerce.utils.Constants;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setupRecyclerView(view);
        view.findViewById(R.id.image_log_out).setOnClickListener(v -> requireActivity().finish());

        // inflate the layout for this fragment
        return view;
    }

    // setup recycler view
    private void setupRecyclerView(View view) {
        // get context
        Context context = requireContext();
        // get recycler view
        RecyclerView recyclerView = view.findViewById(R.id.list_products);
        // initialize recycler view adapter
        ProductListAdapter myAdapter = new ProductListAdapter(context, Constants.PRODUCTS_LIST);
        // initialize layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);

        // set adapter and layout manager to recycler view
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
}