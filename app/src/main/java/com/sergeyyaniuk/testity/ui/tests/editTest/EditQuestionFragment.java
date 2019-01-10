package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.EditQuestionModule;
import com.sergeyyaniuk.testity.ui.create.questions.QuestionsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EditQuestionFragment extends Fragment {

    @Inject
    EditQuestionPresenter mPresenter;

    private Unbinder unbinder;
    EditQuestionListener mListener;

    @BindView(R.id.question_editText) EditText mQuestionEditText;

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

    private String mQuestionId;
    private String mTestId;
    private boolean isUpdating;
    private List<Answer> mAnswerList;
    private int mNumberOfChecks;

    public interface EditQuestionListener{
        void onEditQuesFragCompleted(Question question, List<Answer> answers, boolean isUpdating);
    }

    public EditQuestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new EditQuestionModule(this))
                .inject(this);
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
        setAnswerData(answers,0, mAnswer1EditText, mAnswer1Checkbox);
        setAnswerData(answers,1, mAnswer2EditText, mAnswer2Checkbox);
        setAnswerData(answers,2, mAnswer3EditText, mAnswer3Checkbox);
        setAnswerData(answers,3, mAnswer4EditText, mAnswer4Checkbox);
        setAnswerData(answers,4, mAnswer5EditText, mAnswer5Checkbox);
        setAnswerData(answers,5, mAnswer6EditText, mAnswer6Checkbox);
        setAnswerData(answers,6, mAnswer7EditText, mAnswer7Checkbox);
        setAnswerData(answers,7, mAnswer8EditText, mAnswer8Checkbox);
    }

    private void setAnswerData(List<Answer> answers, int index, EditText editText, CheckBox checkBox){
        if (index < answers.size()){
            Answer answer = answers.get(index);
            editText.setText(answer.getAnswerText());
            checkBox.setChecked(answer.isCorrect());
        }
    }

    @OnClick(R.id.doneButton)
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
        if (mAnswerList.size() == 0){
            Toast toast = Toast.makeText(getApplicationContext(), R.string.did_not_specify_answer, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }
        if (mNumberOfChecks == 0){
            Toast toast = Toast.makeText(getApplicationContext(), R.string.check_correct_answer, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }
        hideKeyboard();
        mListener.onEditQuesFragCompleted(question, mAnswerList, isUpdating);
    }

    private void checkAddAnswer(String answerText, int number, boolean isCorrect){
        if (answerText.length() > 0){
            String answerId = mQuestionId + Integer.toString(number);
            Answer answer = new Answer(answerId, answerText, isCorrect, mQuestionId);
            mAnswerList.add(answer);
        }
        if (isCorrect && answerText.length() > 0) mNumberOfChecks += 1;
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
        mListener = (EditQuestionListener) context;
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
