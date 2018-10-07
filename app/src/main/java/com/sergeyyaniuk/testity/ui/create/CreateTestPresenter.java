package com.sergeyyaniuk.testity.ui.create;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

public class CreateTestPresenter extends BasePresenter {

    private DatabaseManager mDatabase;

    public CreateTestPresenter(DatabaseManager database) {
        this.mDatabase = database;
    }


}
