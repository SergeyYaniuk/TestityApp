package com.sergeyyaniuk.testity.ui.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sergeyyaniuk.testity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddEditQuestionFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.question_editText)
    EditText mQuestionEditText;

    @BindView(R.id.answer1_checkbox) CheckBox mAnswer1Checkbox;
    @BindView(R.id.answer2_checkbox) CheckBox mAnswer2Checkbox;
    @BindView(R.id.answer3_checkbox) CheckBox mAnswer3Checkbox;
    @BindView(R.id.answer4_checkbox) CheckBox mAnswer4Checkbox;
    @BindView(R.id.answer5_checkbox) CheckBox mAnswer5Checkbox;
    @BindView(R.id.answer6_checkbox) CheckBox mAnswer6Checkbox;
    @BindView(R.id.answer7_checkbox) CheckBox mAnswer7Checkbox;
    @BindView(R.id.answer8_checkbox) CheckBox mAnswer8Checkbox;

    @BindView(R.id.answer1_editText) EditText mAnswer1EditText;
    @BindView(R.id.answer2_editText) EditText mAnswer2EditText;
    @BindView(R.id.answer3_editText) EditText mAnswer3EditText;
    @BindView(R.id.answer4_editText) EditText mAnswer4EditText;
    @BindView(R.id.answer5_editText) EditText mAnswer5EditText;
    @BindView(R.id.answer6_editText) EditText mAnswer6EditText;
    @BindView(R.id.answer7_editText) EditText mAnswer7EditText;
    @BindView(R.id.answer8_editText) EditText mAnswer8EditText;

    @BindView(R.id.add_answer3) ImageButton addAnswer3Button;
    @BindView(R.id.add_answer4) ImageButton addAnswer4Button;
    @BindView(R.id.add_answer5) ImageButton addAnswer5Button;
    @BindView(R.id.add_answer6) ImageButton addAnswer6Button;
    @BindView(R.id.add_answer7) ImageButton addAnswer7Button;
    @BindView(R.id.add_answer8) ImageButton addAnswer8Button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
