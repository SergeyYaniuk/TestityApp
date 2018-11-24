package com.sergeyyaniuk.testity.ui.create;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddEditQuestionFragment extends Fragment {

    private Unbinder unbinder;
    AddEditQuestionListener mListener;

    @BindView(R.id.question_editText)
    EditText mQuestionEditText;

    @BindView(R.id.answer1TextInputLayout) TextInputLayout mAnswer1Layout;
    @BindView(R.id.answer2TextInputLayout) TextInputLayout mAnswer2Layout;
    @BindView(R.id.answer3TextInputLayout) TextInputLayout mAnswer3Layout;
    @BindView(R.id.answer4TextInputLayout) TextInputLayout mAnswer4Layout;
    @BindView(R.id.answer5TextInputLayout) TextInputLayout mAnswer5Layout;
    @BindView(R.id.answer6TextInputLayout) TextInputLayout mAnswer6Layout;
    @BindView(R.id.answer7TextInputLayout) TextInputLayout mAnswer7Layout;
    @BindView(R.id.answer8TextInputLayout) TextInputLayout mAnswer8Layout;

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

    @BindView(R.id.button4Layout) LinearLayout button4Layout;
    @BindView(R.id.button5Layout) LinearLayout button5Layout;
    @BindView(R.id.button6Layout) LinearLayout button6Layout;
    @BindView(R.id.button7Layout) LinearLayout button7Layout;
    @BindView(R.id.button8Layout) LinearLayout button8Layout;

    private String mTestId;
    private String mQuestionId;
    private boolean isUpdating;
    private List<Answer> mAnswerList = new ArrayList<>();

    CreatePresenterContract mPresenterContract;

    public interface AddEditQuestionListener{
        void onAddEditQuestionCompleted(Question question, List<Answer> answers, boolean isUpdating);
    }

    public AddEditQuestionFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null){
            mQuestionId = arguments.getString(CreateTestActivity.QUESTION_ID);
            mTestId = arguments.getString(CreateTestActivity.TEST_ID);
            isUpdating = arguments.getBoolean(CreateTestActivity.IS_UPDATING);
            if (isUpdating){
                loadQuestionWithAnswers();
            }
        }
        return view;
    }

    private void loadQuestionWithAnswers(){
        Question question = mPresenterContract.loadQuestion(mQuestionId);
        mAnswerList = mPresenterContract.loadAnswers(mQuestionId);
        mQuestionEditText.setText(question.getQuestionText());
        loadAnswer(0, mAnswer1EditText, mAnswer1Checkbox, mAnswer1Layout, null);
        loadAnswer(1, mAnswer2EditText, mAnswer2Checkbox, mAnswer2Layout, null);
        loadAnswer(2, mAnswer3EditText, mAnswer3Checkbox, mAnswer3Layout, button4Layout);
        loadAnswer(3, mAnswer4EditText, mAnswer4Checkbox, mAnswer4Layout, button5Layout);
        loadAnswer(4, mAnswer5EditText, mAnswer5Checkbox, mAnswer5Layout, button6Layout);
        loadAnswer(5, mAnswer6EditText, mAnswer6Checkbox, mAnswer6Layout, button7Layout);
        loadAnswer(6, mAnswer7EditText, mAnswer7Checkbox, mAnswer7Layout, button8Layout);
        loadAnswer(7, mAnswer8EditText, mAnswer8Checkbox, mAnswer8Layout, null);
    }

    @OnClick(R.id.saveQuestionButton)
    public void onSaveQuestion(){
        Question question = new Question(mQuestionId, mQuestionEditText.getText().toString(), mTestId);
        checkAddAnswer(mAnswer1EditText.getText().toString(), 1, mAnswer1Checkbox.isChecked());
        checkAddAnswer(mAnswer2EditText.getText().toString(), 2, mAnswer2Checkbox.isChecked());
        checkAddAnswer(mAnswer3EditText.getText().toString(), 3, mAnswer3Checkbox.isChecked());
        checkAddAnswer(mAnswer4EditText.getText().toString(), 4, mAnswer4Checkbox.isChecked());
        checkAddAnswer(mAnswer5EditText.getText().toString(), 5, mAnswer5Checkbox.isChecked());
        checkAddAnswer(mAnswer6EditText.getText().toString(), 6, mAnswer6Checkbox.isChecked());
        checkAddAnswer(mAnswer7EditText.getText().toString(), 7, mAnswer7Checkbox.isChecked());
        checkAddAnswer(mAnswer8EditText.getText().toString(), 8, mAnswer8Checkbox.isChecked());
        mListener.onAddEditQuestionCompleted(question, mAnswerList, isUpdating);
    }

    private void checkAddAnswer(String answerText, int number, boolean isCorrect){
        if (answerText.length() > 0){
            String answerId = mQuestionId + Integer.toString(number);
            Answer answer = new Answer(answerId, answerText, isCorrect, mQuestionId);
            mAnswerList.add(answer);
        }
    }

    private void loadAnswer(int index, EditText editText, CheckBox checkBox, TextInputLayout editTextLayout, LinearLayout buttonLayout){
        if (mAnswerList.get(index) != null){
            Answer answer = mAnswerList.get(index);
            editText.setText(answer.getAnswerText());
            checkBox.setChecked(answer.isCorrect());
            editTextLayout.setVisibility(View.VISIBLE);
            if (buttonLayout != null){
                buttonLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.add_answer3)
    public void setAnswer3Visible(){
        mAnswer3Layout.setVisibility(View.VISIBLE);
        button4Layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_answer4)
    public void setAnswer4Visible(){
        mAnswer4Layout.setVisibility(View.VISIBLE);
        button5Layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_answer5)
    public void setAnswer5Visible(){
        mAnswer5Layout.setVisibility(View.VISIBLE);
        button6Layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_answer6)
    public void setAnswer6Visible(){
        mAnswer6Layout.setVisibility(View.VISIBLE);
        button7Layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_answer7)
    public void setAnswer7Visible(){
        mAnswer7Layout.setVisibility(View.VISIBLE);
        button8Layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_answer8)
    public void setAnswer8Visible(){
        mAnswer8Layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AddEditQuestionListener) context;
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
