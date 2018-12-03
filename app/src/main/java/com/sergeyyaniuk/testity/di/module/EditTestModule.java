package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditPresenter;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditTestActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class EditTestModule {

    private EditTestActivity mActivity;

    public EditTestModule(EditTestActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    EditTestActivity provideActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    EditPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new EditPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
