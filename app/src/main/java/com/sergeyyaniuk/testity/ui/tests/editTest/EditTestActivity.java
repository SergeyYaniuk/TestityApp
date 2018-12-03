package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sergeyyaniuk.testity.R;

public class EditTestActivity extends AppCompatActivity {

    private String mTestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);

        mTestId = getIntent().getStringExtra("test_id");
    }
}
