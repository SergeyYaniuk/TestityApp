package com.sergeyyaniuk.testity.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

//need add realization of deep link
public class DeepLinkActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for link in intent
        if (getIntent() != null && getIntent().getData() != null) {
            Uri data = getIntent().getData();
        }
    }
}
