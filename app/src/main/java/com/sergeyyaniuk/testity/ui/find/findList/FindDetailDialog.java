package com.sergeyyaniuk.testity.ui.find.findList;

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

public class FindDetailDialog extends BaseDialogNoTitle {

    @BindView(R.id.test_title_tv)
    TextView titleTV;

    @BindView(R.id.category_tv)
    TextView categoryTV;

    @BindView(R.id.language_tv)
    TextView languageTV;

    @BindView(R.id.number_of_ques_tv)
    TextView numberOfQuesTV;

    @BindView(R.id.date_tv)
    TextView dateTV;

    @BindView(R.id.author_tv)
    TextView authorTV;

    @BindView(R.id.description_tv)
    TextView descriptionTV;

    private Unbinder unbinder;
    FindDetailListener mListener;

    String mId, mTitle, mCategory, mLanguage, mDate, mAuthor, mDescription;
    int mNum_of_ques;

    public interface FindDetailListener{
        void passTest(String testId, int numberOfQues, String testTitle);
    }

    public FindDetailDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_find_detail, container);
        unbinder = ButterKnife.bind(this, view);
        setUpViews();
        return view;
    }

    private void setUpViews(){
        Bundle arguments = getArguments();
        if (arguments != null){
            mId = arguments.getString(FindListActivity.TEST_ID);
            mTitle = arguments.getString(FindListActivity.TEST_TITLE);
            mCategory = arguments.getString(FindListActivity.TEST_CATEGORY);
            mLanguage = arguments.getString(FindListActivity.TEST_LANGUAGE);
            mNum_of_ques = arguments.getInt(FindListActivity.TEST_NUM_OF_QUES);
            mDate = arguments.getString(FindListActivity.TEST_DATE);
            mAuthor = arguments.getString(FindListActivity.TEST_AUTHOR);
            mDescription = arguments.getString(FindListActivity.TEST_DESCR);
        }
        titleTV.setText(mTitle);
        categoryTV.setText(mCategory);
        languageTV.setText(mLanguage);
        numberOfQuesTV.setText(String.valueOf(mNum_of_ques));
        dateTV.setText(mDate);
        authorTV.setText(mAuthor);
        descriptionTV.setText(mDescription);
    }

    @OnClick(R.id.pass_btn)
    public void onPassTest(){
        mListener.passTest(mId, mNum_of_ques, mTitle);
        dismiss();
    }

    @OnClick(R.id.back_btn)
    public void onBack(){
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FindDetailListener) context;
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
