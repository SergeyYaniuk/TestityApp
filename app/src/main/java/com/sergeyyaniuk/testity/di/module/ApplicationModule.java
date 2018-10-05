package com.sergeyyaniuk.testity.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.sergeyyaniuk.testity.data.database.TestityDatabase;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.di.DatabaseInfo;
import com.sergeyyaniuk.testity.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Application mApplication;

    public ApplicationModule(Application application){
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    Context provideContext(Application application){
        return application;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName(){
        return Constants.DB_NAME;
    }

    @Provides
    @Singleton
    TestityDatabase provideDatabase(@DatabaseInfo String databaseName, Context context){
        return Room.databaseBuilder(context, TestityDatabase.class, databaseName).build();
    }

    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(TestityDatabase testityDatabase){
        return new DatabaseManager(testityDatabase);
    }
}
