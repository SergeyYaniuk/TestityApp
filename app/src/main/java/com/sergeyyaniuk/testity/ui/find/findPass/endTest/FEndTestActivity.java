package com.sergeyyaniuk.testity.ui.find.findPass.endTest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FEndTestActivity extends BaseActivity {

    @BindView(R.id.end_test_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.score_tv)
    TextView mScoreTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fend_test);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle.setText(R.string.end_test);
        double score = getIntent().getDoubleExtra("score", 0);
        if (score > 0){
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String scoreString = decimalFormat.format(score) + "%";
            mScoreTextView.setText(scoreString);
        } else {
            mScoreTextView.setText("0.0%");
        }
    }

    @OnClick(R.id.finish_btn)
    public void onFinishButton(){
        startActivity(new Intent(FEndTestActivity.this, MainActivity.class));
    }
}
