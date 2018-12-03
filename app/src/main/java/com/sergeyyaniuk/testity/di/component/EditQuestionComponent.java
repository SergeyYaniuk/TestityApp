package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.EditQuestionModule;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditQuestionFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = EditQuestionModule.class)
public interface EditQuestionComponent {

    EditQuestionFragment inject(EditQuestionFragment editQuestionFragment);
}
