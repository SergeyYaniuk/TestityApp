package com.sergeyyaniuk.testity.data.database;

import android.util.Log;

import com.sergeyyaniuk.testity.data.model.Test;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DatabaseManager {

    public static final String TAG = "MyLog";

    private final TestityDatabase testityDatabase;

    public DatabaseManager(TestityDatabase testityDatabase) {
        this.testityDatabase = testityDatabase;
    }

//    public Observable<Boolean> insertTest(final Test test){
//        Log.d(TAG, "insertTest: start");
//        return Observable.fromCallable(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Log.d(TAG, "call: before interface");
//                testityDatabase.testDao().insert(test);
//                Log.d(TAG, "call: after interface");
//                return true;
//            }
//        });
//    }

    public Observable<Long> insertTestWithId (final Test test){
        return Observable.fromCallable(() -> testityDatabase.testDao().insertWithId(test))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
