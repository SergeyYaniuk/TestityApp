package com.sergeyyaniuk.testity.ui.tests.passTest.endTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EndTestActivity extends BaseActivity {

    @BindView(R.id.score_tv)
    TextView mScoreTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_test);
        ButterKnife.bind(this);
        double score = getIntent().getDoubleExtra("score", 0);
        @SuppressLint("DefaultLocale") String scoreStr = String.format ("%.2f", score) + " %";
        mScoreTextView.setText(scoreStr);
    }

    @OnClick(R.id.finish_btn)
    public void onFinishButton(){
        startActivity(new Intent(EndTestActivity.this, MainActivity.class));
    }
}
