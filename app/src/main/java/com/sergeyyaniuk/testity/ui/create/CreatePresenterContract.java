package com.sergeyyaniuk.testity.ui.create;

import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;

import java.util.List;

public interface CreatePresenterContract {

    Test loadTest(String testId);
    Question loadQuestion(String questionId);
    List<Question> loadQuestions(String testId);
    List<Answer> loadAnswers(String questionId);
}
