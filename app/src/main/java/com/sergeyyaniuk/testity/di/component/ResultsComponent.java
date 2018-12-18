package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.ResultsModule;
import com.sergeyyaniuk.testity.ui.results.ResultsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ResultsModule.class)
public interface ResultsComponent {

    ResultsActivity inject(ResultsActivity resultsActivity);
}
