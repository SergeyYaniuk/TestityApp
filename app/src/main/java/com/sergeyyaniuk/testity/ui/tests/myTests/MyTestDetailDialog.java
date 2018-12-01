package com.sergeyyaniuk.testity.ui.tests.myTests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseDialogNoTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyTestDetailDialog extends BaseDialogNoTitle {

    @BindView(R.id.test_title_tv)
    TextView titleTV;

    @BindView(R.id.category_tv)
    TextView categoryTV;

    @BindView(R.id.language_tv)
    TextView languageTV;

    @BindView(R.id.number_of_ques_tv)
    TextView numberOfQuesTV;

    @BindView(R.id.description_tv)
    TextView descriptionTV;

    private Unbinder unbinder;
    MyTestDetailListener mListener;

    String mId, mTitle, mCategory, mLanguage, mDescription;
    int mNum_of_ques;

    public interface MyTestDetailListener{
        void onEditTest(String testId);
        void passTest(String testId);
    }

    public MyTestDetailDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_my_test_detail, container);
        unbinder = ButterKnife.bind(this, view);
        setUpViews();
        return view;
    }

    private void setUpViews(){
        Bundle arguments = getArguments();
        if (arguments != null){
            mId = arguments.getString(MyTestsActivity.TEST_ID);
            mTitle = arguments.getString(MyTestsActivity.TEST_TITLE);
            mCategory = arguments.getString(MyTestsActivity.TEST_CATEGORY);
            mLanguage = arguments.getString(MyTestsActivity.TEST_LANGUAGE);
            mNum_of_ques = arguments.getInt(MyTestsActivity.TEST_NUM_OF_QUES);
            mDescription = arguments.getString(MyTestsActivity.TEST_DESCR);
        }
        titleTV.setText(mTitle);
        categoryTV.setText(mCategory);
        languageTV.setText(mLanguage);
        numberOfQuesTV.setText(String.valueOf(mNum_of_ques));
        descriptionTV.setText(mDescription);
    }

    @OnClick(R.id.edit_test_btn)
    public void onEditTest(){
        mListener.onEditTest(mId);
        dismiss();
    }

    @OnClick(R.id.pass_btn)
    public void onPassTest(){
        mListener.passTest(mId);
        dismiss();
    }

    @OnClick(R.id.back_btn)
    public void onBack(){
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (MyTestDetailListener) context;
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
