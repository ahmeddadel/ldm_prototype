package com.dolla.e_commerce.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.utils.Constants;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView tvName = view.findViewById(R.id.tv_user_name);
        TextView tvEmail = view.findViewById(R.id.tv_user_email);
        TextView tvPhone = view.findViewById(R.id.tv_user_phone);

        tvName.setText(Constants.USER.getUserName());
        tvEmail.setText(Constants.USER.getUserEmail());
        tvPhone.setText(Constants.USER.getUserPhone());

        return view;
    }
}