package com.dolla.e_commerce.ui.aboutus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.ui.fraghost.HomeActivity;
import com.dolla.e_commerce.utils.Constants;

public class AboutUsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        ImageView ivInstagram = view.findViewById(R.id.iv_instagram_button);
        ImageView ivFacebook = view.findViewById(R.id.iv_facebook_button);
        ImageView ivGmail = view.findViewById(R.id.iv_gmail_button);

        ivInstagram.setOnClickListener(v -> social(Constants.INSTAGRAM_URL));
        ivFacebook.setOnClickListener(v -> social(Constants.FACEBOOK_URL));
        ivGmail.setOnClickListener(v -> social("mailto:" + Constants.GMAIL_EMAIL));
        return view;
    }

    // navigate to required activity
    private void social(String url) {
        HomeActivity.isDestroying = false;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}