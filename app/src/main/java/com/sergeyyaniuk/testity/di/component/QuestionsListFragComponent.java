package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.QuestionsListFragModule;
import com.sergeyyaniuk.testity.ui.create.questions.QuestionsListFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = QuestionsListFragModule.class)
public interface QuestionsListFragComponent {

    QuestionsListFragment inject(QuestionsListFragment questionsListFragment);
}
