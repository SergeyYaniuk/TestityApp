package com.sergeyyaniuk.testity.ui.find.findPass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;

public class FindPassActivity extends AppCompatActivity {

    String mTestId;

    TextView mTestIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);
        mTestId = getIntent().getStringExtra("test_id");
        mTestIdTextView = findViewById(R.id.test_id_tv);
        mTestIdTextView.setText(mTestId);
    }
}
