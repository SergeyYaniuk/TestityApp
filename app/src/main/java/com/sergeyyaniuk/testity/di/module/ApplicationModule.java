package com.sergeyyaniuk.testity.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.sergeyyaniuk.testity.data.database.TestityDatabase;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.DatabaseInfo;
import com.sergeyyaniuk.testity.di.PreferencesInfo;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    public static final String TAG = "MyLog";

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
        Log.d(TAG, "AppModule________provideDatabaseName: ");
        return Constants.DB_NAME;
    }

    @Provides
    @Singleton
    TestityDatabase provideDatabase(@DatabaseInfo String databaseName, Context context){
        Log.d(TAG, "AppModule________provideDatabase: ");
        return Room.databaseBuilder(context, TestityDatabase.class, databaseName)
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(TestityDatabase testityDatabase){
        Log.d(TAG, "AppModule_________provideDatabaseManager: ");
        return new DatabaseManager(testityDatabase);
    }

    @Provides
    @Singleton
    Authentication provideAuthentication(Application application){
        return new Authentication(application);
    }

    @Provides
    @Singleton
    Firestore provideFirestore(){
        return new Firestore();
    }

    @Provides
    @Singleton
    PrefHelper provideSharedPreferences(Context context, @PreferencesInfo String prefFileName){
        return new PrefHelper(context, prefFileName);
    }

    @Provides
    @PreferencesInfo
    String providePreferenceName(){
        return Constants.PREF_NAME;
    }
}
