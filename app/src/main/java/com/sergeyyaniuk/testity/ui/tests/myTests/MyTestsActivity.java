package com.sergeyyaniuk.testity.ui.tests.myTests;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.MyTestsModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditTestActivity;
import com.sergeyyaniuk.testity.ui.tests.passTest.startTest.StartTestActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTestsActivity extends BaseActivity implements MyTestDetailDialog.MyTestDetailListener,
        ConfirmDelTestDialog.DeleteTestListener {

    public static final String TEST_ID = "test_id";
    public static final String TEST_TITLE = "test_title";
    public static final String TEST_CATEGORY = "test_category";
    public static final String TEST_LANGUAGE = "test_language";
    public static final String TEST_NUM_OF_QUES = "number_of_questions";
    public static final String TEST_DATE = "date";
    public static final String TEST_DESCR = "test_description";

    public static final String POSITION = "position";
    public static final String IS_TEST_ONLINE = "is_test_online";

    @Inject
    MyTestsPresenter mPresenter;

    TestListAdapter mAdapter;

    @BindView(R.id.my_tests_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.testsRecView)
    RecyclerView mRecyclerView;

    List<Test> mTests;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tests);
        App.get(this).getAppComponent().create(new MyTestsModule(this)).inject(this);
        mPresenter.onCreate();
        ButterKnife.bind(this);
        mPresenter.loadTests();
        //setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle.setText(R.string.my_tests_title);
    }

    //invoke from presenter
    public void setRecyclerView(List<Test> tests){
        mTests = tests;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestListAdapter(tests, testClickListener);
        mRecyclerView.setAdapter(mAdapter);
        enableSwipe();
    }

    TestListAdapter.TestClickListener testClickListener = new TestListAdapter.TestClickListener() {
        @Override
        public void onTestClick(int position) {
            MyTestDetailDialog testDetailDialog = new MyTestDetailDialog();

            Test test = mTests.get(position);
            Bundle arguments = new Bundle();
            arguments.putString(TEST_ID, test.getId());
            arguments.putString(TEST_TITLE, test.getTitle());
            arguments.putString(TEST_CATEGORY, test.getCategory());
            arguments.putString(TEST_LANGUAGE, test.getLanguage());
            arguments.putInt(TEST_NUM_OF_QUES, test.getNumberOfQuestions());
            arguments.putString(TEST_DATE, test.getDate());
            arguments.putString(TEST_DESCR, test.getDescription());

            testDetailDialog.setArguments(arguments);
            testDetailDialog.show(getSupportFragmentManager(), "dialog_detail_test");
        }
    };

    private void enableSwipe(){
        TestSwipeCallback testSwipeCallback = new TestSwipeCallback(this){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                String testId = mAdapter.getItem(position).getId();
                boolean isTestOnline = mAdapter.getItem(position).isOnline();

                ConfirmDelTestDialog confirmDelTestDialog = new ConfirmDelTestDialog();
                Bundle arguments = new Bundle();
                arguments.putString(TEST_ID, testId);
                arguments.putInt(POSITION, position);
                arguments.putBoolean(IS_TEST_ONLINE, isTestOnline);
                confirmDelTestDialog.setArguments(arguments);
                confirmDelTestDialog.show(getSupportFragmentManager(), "confirm_delete_test_dialog");
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(testSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDeleteTest(String testId, int position, boolean isTestOnline) {
        mPresenter.deleteTest(testId, isTestOnline);
        mAdapter.removeTest(position);
    }

    @Override
    public void onCancelDelete() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditTest(String testId) {
        Intent intent = new Intent(MyTestsActivity.this, EditTestActivity.class);
        intent.putExtra(TEST_ID, testId);
        startActivity(intent);
    }

    @Override
    public void passTest(String testId, int numberOfQuestions, String title) {
        if (numberOfQuestions > 0){
            Intent intent = new Intent(MyTestsActivity.this, StartTestActivity.class);
            intent.putExtra(TEST_ID, testId);
            intent.putExtra(TEST_TITLE, title);
            startActivity(intent);
        } else {
            showToast(this, R.string.please_add_ques);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyTestsActivity.this, MainActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
