package com.sergeyyaniuk.testity.ui.find.findPass.passTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseDialogNoTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateTestDialog extends BaseDialogNoTitle {

    @BindView(R.id.test_rating_bar)
    MaterialRatingBar mTestRatingBar;

    private Unbinder unbinder;
    RateTestListener mListener;

    public interface RateTestListener{
        void onRateTest(double rating);
        void onCancelRating();
    }

    public RateTestDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rate_test, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.cancel_button)
    public void onCancelClicked(){
        mListener.onCancelRating();
        dismiss();
    }

    @OnClick(R.id.ok_button)
    public void onOkClicked(){
        mListener.onRateTest(mTestRatingBar.getRating());
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (RateTestListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
