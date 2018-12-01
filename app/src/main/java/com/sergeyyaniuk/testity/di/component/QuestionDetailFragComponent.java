package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.QuestionDetailFragModule;
import com.sergeyyaniuk.testity.ui.create.questions.DetailQuestionFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = QuestionDetailFragModule.class)
public interface QuestionDetailFragComponent {

    DetailQuestionFragment inject(DetailQuestionFragment detailQuestionFragment);
}
