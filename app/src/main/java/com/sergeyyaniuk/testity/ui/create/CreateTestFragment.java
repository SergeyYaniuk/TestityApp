package com.sergeyyaniuk.testity.ui.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.sergeyyaniuk.testity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateTestFragment extends Fragment {

    private Unbinder unbinder;

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

    @BindView(R.id.add_questions_button)
    Button addQuestionsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
