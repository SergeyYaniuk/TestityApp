package com.sergeyyaniuk.testity.ui.create;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class CreateTestFragment extends Fragment {

    private Unbinder unbinder;
    CreateTestListener mListener;   //Listener for communication with Activity
    TestContract.UserActionListener mPresenterContract;  //Listener for communication with CreateTestPresenter

    @BindView(R.id.titleTextInputLayout)
    TextInputLayout mTitleEditText;

    @BindView(R.id.category_spinner)
    Spinner mCategorySpinner;

    @BindView(R.id.language_spinner)
    Spinner mLanguageSpinner;

    @BindView(R.id.is_online_checkbox)
    CheckBox mIsOnlineCheckBox;

    @BindView(R.id.descriptionTextInputLayout)
    TextInputLayout mDescriptionEditText;

    @BindView(R.id.add_questions_button)
    Button addQuestionsButton;

    String mTitle, mCategory, mLanguage, mDescription;
    boolean isOnline;
    boolean isRenewal;  //continue editing test

    public interface CreateTestListener{
        void onCreateTestCompleted(String title, String category, String language,
                                   boolean isOnline, String description);
    }

    public CreateTestFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_test, container, false);
        unbinder = ButterKnife.bind(this, view);

        //user would like to continue edit test
        Bundle arguments = getArguments();
        if (arguments != null){
            Long testId = arguments.getLong(CreateTestActivity.TEST_ID);
            loadTest(testId);
            isRenewal = true;
        }
        setCategoryAdapter();
        setLanguageAdapter();

        return view;
    }

    private void setCategoryAdapter(){
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource
                (getContext(), R.array.category_list, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryAdapter);
        if (!isRenewal){
            mCategorySpinner.setSelection(3);
        }
    }

    @OnItemSelected(value = R.id.category_spinner, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onCategorySelected(AdapterView<?> parent, View view, int pos, long id){
        String[] category = getResources().getStringArray(R.array.category_list);
        mCategory = category[pos];
    }

    private void setLanguageAdapter(){
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource
                (getContext(), R.array.language_list, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLanguageSpinner.setAdapter(languageAdapter);
        if (!isRenewal){
            mLanguageSpinner.setSelection(2);
        }
    }

    @OnItemSelected(value = R.id.language_spinner, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onLanguageSelected(AdapterView<?> parent, View view, int pos, long id){
        String[] language = getResources().getStringArray(R.array.language_list);
        mLanguage = language[pos];
    }

    @OnCheckedChanged(R.id.is_online_checkbox)
    public void onOnlineChecked(boolean checked){
        isOnline = checked;
    }

    @OnClick(R.id.add_questions_button)
    public void onClick(){
        if (!validateForm()) {
            return;
        }
        mListener.onCreateTestCompleted(mTitle, mCategory, mLanguage, isOnline, mDescription);
    }

    private void loadTest(Long testId){
        Test test = mPresenterContract.loadTest(testId);
        String title = test.getTitle();
        int categoryPosition = getCategoryPosition(test);
        int languagePosition = getLanguagePosition(test);
        boolean online = test.isOnline();
        String description = test.getDescription();
        mTitleEditText.getEditText().setText(title);
        mCategorySpinner.setSelection(categoryPosition);
        mLanguageSpinner.setSelection(languagePosition);
        mIsOnlineCheckBox.setChecked(online);
        mDescriptionEditText.getEditText().setText(description);
    }

    private int getCategoryPosition(Test test){
        int position = 0;
        String[] categoryArray = getResources().getStringArray(R.array.category_list);
        String category = test.getCategory();
        for (int i = 0; i < categoryArray.length; i++){
            if (category.equals(categoryArray[i])){
                position = i;
            }
        }
        return position;
    }

    private int getLanguagePosition(Test test){
        int position = 0;
        String[] languageArray = getResources().getStringArray(R.array.language_list);
        String language = test.getLanguage();
        for (int i = 0; i < languageArray.length; i++){
            if (language.equals(languageArray[i])){
                position = i;
            }
        }
        return position;
    }

    private boolean validateForm(){
        boolean valid = true;

        mTitle = mTitleEditText.getEditText().getText().toString();
        if (TextUtils.isEmpty(mTitle)) {
            mTitleEditText.setError(getString(R.string.required));
            valid = false;
        } else {
            mTitleEditText.setError(null);
        }

        mDescription = mDescriptionEditText.getEditText().getText().toString();
        if (TextUtils.isEmpty(mDescription)) {
            mDescriptionEditText.setError(getString(R.string.required));
            valid = false;
        } else {
            mDescriptionEditText.setError(null);
        }
        return valid;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (CreateTestListener) context;
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
