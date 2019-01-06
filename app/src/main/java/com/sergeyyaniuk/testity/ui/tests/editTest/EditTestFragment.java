package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.EditTestFragModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

public class EditTestFragment extends Fragment {

    @Inject
    EditTestFragPresenter mPresenter;

    @BindView(R.id.titleEditText)
    EditText mTitleEditText;

    @BindView(R.id.category_spinner)
    Spinner mCategorySpinner;

    @BindView(R.id.language_spinner)
    Spinner mLanguageSpinner;

    @BindView(R.id.is_online_checkbox)
    CheckBox mIsOnlineCheckBox;

    @BindView(R.id.descriptionEditText)
    EditText mDescriptionEditText;

    String mTestId, mTitle, mCategory, mLanguage, mDescription;
    boolean isOnline = true;
    Test mTest;

    private Unbinder unbinder;
    EditTestFragListener mListener;

    public interface EditTestFragListener{
        void onEditTestFragCompleted(Test test);
    }

    public EditTestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new EditTestFragModule(this))
                .inject(this);
        mPresenter.onCreate();
        setCategoryAdapter();
        setLanguageAdapter();
        Bundle arguments = getArguments();
        if (arguments != null){
            mTestId = arguments.getString(EditTestActivity.TEST_ID);
            mPresenter.loadTest(mTestId);
        }
        mTest = new Test();
        return view;
    }

    private void setCategoryAdapter(){
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource
                (getContext(), R.array.category_list, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryAdapter);
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

    @OnClick(R.id.edit_questions_button)
    public void onEditQuestions(){
        if (!validateForm()) {
            return;
        }
        mTest.setTitle(mTitle);
        mTest.setCategory(mCategory);
        mTest.setLanguage(mLanguage);
        mTest.setOnline(isOnline);
        mTest.setDescription(mDescription);
        mListener.onEditTestFragCompleted(mTest);
        hideKeyboard();
    }

    public void enterDataToLayout(Test test){
        mTest = test;
        mTitleEditText.setText(test.getTitle());
        mDescriptionEditText.setText(test.getDescription());
        mIsOnlineCheckBox.setChecked(test.isOnline());
        String category = test.getCategory();
        String language = test.getLanguage();
        String[] categoryArray = getResources().getStringArray(R.array.category_list);
        String[] languageArray = getResources().getStringArray(R.array.language_list);
        int categoryPos = -1;
        int languagePos = -1;
        for (int i = 0; i < categoryArray.length; i++){
            if (categoryArray[i].equals(category)){
                categoryPos = i;
                break;
            }
        }
        for (int i = 0; i < languageArray.length; i++){
            if (languageArray[i].equals(language)){
                languagePos = i;
                break;
            }
        }
        mCategorySpinner.setSelection(categoryPos);
        mLanguageSpinner.setSelection(languagePos);
    }

    private boolean validateForm(){
        boolean valid = true;

        mTitle = mTitleEditText.getText().toString();
        if (TextUtils.isEmpty(mTitle)) {
            mTitleEditText.setError(getString(R.string.required));
            valid = false;
        } else {
            mTitleEditText.setError(null);
        }

        mDescription = mDescriptionEditText.getText().toString();
        if (TextUtils.isEmpty(mDescription)) {
            mDescriptionEditText.setError(getString(R.string.required));
            valid = false;
        } else {
            mDescriptionEditText.setError(null);
        }
        return valid;
    }

    private void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (EditTestFragListener) context;
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
        mPresenter.onDestroy();
    }
}
