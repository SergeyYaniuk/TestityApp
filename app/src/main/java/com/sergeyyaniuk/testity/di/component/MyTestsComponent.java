package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.MyTestsModule;
import com.sergeyyaniuk.testity.ui.tests.myTests.MyTestsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MyTestsModule.class)
public interface MyTestsComponent {

    MyTestsActivity inject(MyTestsActivity myTestsActivity);
}
