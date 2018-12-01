package com.sergeyyaniuk.testity.di.component;


import com.sergeyyaniuk.testity.di.module.ApplicationModule;
import com.sergeyyaniuk.testity.di.module.CreateTestModule;
import com.sergeyyaniuk.testity.di.module.LoginModule;
import com.sergeyyaniuk.testity.di.module.MainModule;
import com.sergeyyaniuk.testity.di.module.MyTestsModule;
import com.sergeyyaniuk.testity.di.module.QuestionDetailFragModule;
import com.sergeyyaniuk.testity.di.module.QuestionsListModule;
import com.sergeyyaniuk.testity.di.module.QuestionsListFragModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {

    /* ----------Activity components --------------*/
    LoginComponent create(LoginModule loginModule);
    MainComponent create(MainModule mainModule);
    CreateTestComponent create(CreateTestModule createTestModule);
    QuestionsListComponent create(QuestionsListModule questionsListModule);
    MyTestsComponent create(MyTestsModule myTestsModule);


    /* ----------Fragment components --------------*/
    QuestionsListFragComponent create(QuestionsListFragModule questionsListFragModule);
    QuestionDetailFragComponent create(QuestionDetailFragModule questionDetailFragModule);
}
