package com.sergeyyaniuk.testity.ui.create;

import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;

import java.util.ArrayList;

public interface CreatePresenterContract {

        ArrayList<Question> loadQuestions(String testId);

        Test loadTest(String testId);
}
