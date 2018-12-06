package com.sergeyyaniuk.testity.ui.create.questions;

import android.annotation.SuppressLint;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.QuestionDetailFragModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailQuestionFragment extends Fragment {

    @Inject
    DetailQuestionPresenter mPresenter;

    private Unbinder unbinder;
    DetailQuestionListener mListener;

    @BindView(R.id.question_editText) EditText mQuestionEditText;

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

    private String mQuestionId, mTestId;
    private boolean isUpdating;
    private List<Answer> mAnswerList;

    public interface DetailQuestionListener{
        void onAddEditQuestionCompleted(Question question, List<Answer> answers, boolean isUpdating);
    }

    public DetailQuestionFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new QuestionDetailFragModule(this)).inject(this);
        mPresenter.onCreate();
        mTestId = mPresenter.getTestId();
        Bundle arguments = getArguments();
        if (arguments != null){
            mQuestionId = arguments.getString(QuestionsActivity.QUESTION_ID);
            isUpdating = true;
            mPresenter.loadQuestion(mQuestionId);
            mPresenter.loadAnswers(mQuestionId);
        } else {
            mQuestionId = generateQuestionId();
        }
        return view;
    }

    //if question exist, set text in EditText.Method is invoked from presenter, after question loaded from database.
    public void setQuestionTest(String questionTest){
        mQuestionEditText.setText(questionTest);
    }
    
    //set answers test if exist
    public void loadAnswerData(List<Answer> answers){
        setAnswerData(answers,0, mAnswer1EditText, mAnswer1Checkbox, mAnswer1Layout, null);
        setAnswerData(answers,1, mAnswer2EditText, mAnswer2Checkbox, mAnswer2Layout, null);
        setAnswerData(answers,2, mAnswer3EditText, mAnswer3Checkbox, mAnswer3Layout, button4Layout);
        setAnswerData(answers,3, mAnswer4EditText, mAnswer4Checkbox, mAnswer4Layout, button5Layout);
        setAnswerData(answers,4, mAnswer5EditText, mAnswer5Checkbox, mAnswer5Layout, button6Layout);
        setAnswerData(answers,5, mAnswer6EditText, mAnswer6Checkbox, mAnswer6Layout, button7Layout);
        setAnswerData(answers,6, mAnswer7EditText, mAnswer7Checkbox, mAnswer7Layout, button8Layout);
        setAnswerData(answers,7, mAnswer8EditText, mAnswer8Checkbox, mAnswer8Layout, null);
    }

    private void setAnswerData(List<Answer> answers, int index, EditText editText, CheckBox checkBox,
                               TextInputLayout editTextLayout, LinearLayout buttonLayout){
        if (index < answers.size()){
            Answer answer = answers.get(index);
            editText.setText(answer.getAnswerText());
            checkBox.setChecked(answer.isCorrect());
            editTextLayout.setVisibility(View.VISIBLE);
            if (buttonLayout != null){
                buttonLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.saveQuestionButton)
    public void onSaveQuestion(){
        if (isQuestionNotEmpty(mQuestionEditText)){
            return;
        }
        Question question = new Question(mQuestionId, mQuestionEditText.getText().toString(), mTestId);
        mAnswerList = new ArrayList<>();
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


    private boolean isQuestionNotEmpty(EditText questionET){
        boolean isEmpty;
        String questionText = questionET.getText().toString();
        if (TextUtils.isEmpty(questionText)){
            questionET.setError("Required.");
            isEmpty = true;
        } else {
            questionET.setError(null);
            isEmpty = false;
        }
        return isEmpty;
    }

    private String generateQuestionId(){
        @SuppressLint("SimpleDateFormat")
        String currentDateAndTime = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return mPresenter.getTestId() + currentDateAndTime;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DetailQuestionListener) context;
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
