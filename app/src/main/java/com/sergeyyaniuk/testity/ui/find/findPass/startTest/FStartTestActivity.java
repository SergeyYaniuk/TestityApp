package com.sergeyyaniuk.testity.ui.find.findPass.startTest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.find.findPass.passTest.FPassTestActivity;
import com.sergeyyaniuk.testity.ui.tests.passTest.passTest.PassTestActivity;
import com.sergeyyaniuk.testity.ui.tests.passTest.startTest.StartTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FStartTestActivity extends BaseActivity {

    public static final String TEST_ID = "test_id";
    public static final String NAME = "name";
    public static final String TEST_TITLE = "test_title";

    @BindView(R.id.name_edit_text)
    EditText mNameEditText;

    private String mTestId, mName, mTestTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fstart_test);
        ButterKnife.bind(this);
        mTestId = getIntent().getStringExtra("test_id");
        mTestTitle = getIntent().getStringExtra("test_title");
    }

    @OnClick(R.id.start_test_btn)
    public void onStartTest() {
        if (!validateForm()) {
            return;
        }
        mName = mNameEditText.getText().toString();
        Intent intent = new Intent(FStartTestActivity.this, FPassTestActivity.class);
        intent.putExtra(TEST_ID, mTestId);
        intent.putExtra(NAME, mName);
        intent.putExtra(TEST_TITLE, mTestTitle);
        startActivity(intent);
    }

    //required not empty fields
    public boolean validateForm() {
        boolean valid = true;
        mName = mNameEditText.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            mNameEditText.setError("Required.");
            valid = false;
        } else {
            mNameEditText.setError(null);
        }
        return valid;
    }
}
