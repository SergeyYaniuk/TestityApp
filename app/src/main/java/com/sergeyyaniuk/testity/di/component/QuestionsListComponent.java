package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.QuestionsListModule;
import com.sergeyyaniuk.testity.ui.createTest.questions.QuestionsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = QuestionsListModule.class)
public interface QuestionsListComponent {

    QuestionsActivity inject(QuestionsActivity questionsActivity);
}
