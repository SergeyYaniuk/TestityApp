package com.sergeyyaniuk.testity.ui.find.findList;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.FindListModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.find.findList.adapter.TestAdapter;
import com.sergeyyaniuk.testity.ui.find.findPass.startTest.FStartTestActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindListActivity extends BaseActivity implements TestAdapter.OnTestSelectedListener,
        FilterTestsDialog.FilterListener, FindDetailDialog.FindDetailListener{

    public static final String TEST_ID = "test_id";
    public static final String TEST_TITLE = "test_title";
    public static final String TEST_CATEGORY = "test_category";
    public static final String TEST_LANGUAGE = "test_language";
    public static final String TEST_NUM_OF_QUES = "number_of_questions";
    public static final String TEST_DESCR = "test_description";

    @Inject
    FindListPresenter mPresenter;

    @BindView(R.id.find_test_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.find_rec_view)
    RecyclerView mTestRecView;

    @BindView(R.id.loading_test_pb)
    ProgressBar mLoadingTestsPB;

    TestAdapter mAdapter;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_list);
        App.get(this).getAppComponent().create(new FindListModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(R.string.find_test_title);
        mQuery = mPresenter.getTopTestsList();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new TestAdapter(mQuery, this){

            @Override
            protected void onDataChanged() {
                if (getItemCount() == 0) {
                    mTestRecView.setVisibility(View.GONE);
                    mLoadingTestsPB.setVisibility(View.VISIBLE);
                } else {
                    mTestRecView.setVisibility(View.VISIBLE);
                    mLoadingTestsPB.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                showToast(getParent(), R.string.no_tests_error);
            }
        };
        mTestRecView.setLayoutManager(new LinearLayoutManager(this));
        mTestRecView.setAdapter(mAdapter);
    }

    @Override
    public void onTestSelected(DocumentSnapshot test) {
        FindDetailDialog dialog = new FindDetailDialog();
        Test testObj = test.toObject(Test.class);
        Bundle arguments = new Bundle();
        arguments.putString(TEST_ID, testObj.getId());
        arguments.putString(TEST_TITLE, testObj.getTitle());
        arguments.putString(TEST_CATEGORY, testObj.getCategory());
        arguments.putString(TEST_LANGUAGE, testObj.getLanguage());
        arguments.putInt(TEST_NUM_OF_QUES, testObj.getNumberOfQuestions());
        arguments.putString(TEST_DESCR, testObj.getDescription());

        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(), "find_detail_dialog");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.find_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_tests:
                showFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFilterDialog(){
        FragmentManager fm = getSupportFragmentManager();
        FilterTestsDialog filterTestsDialog = new FilterTestsDialog();
        filterTestsDialog.show(fm, "FilterTestsDialog");
    }

    @Override
    public void onFilter(Filters filters) {
        Query query = mPresenter.getFilterTestList(filters);
        mQuery = query;
        mAdapter.setQuery(query);
    }

    @Override
    public void passTest(String testId, int numberOfQues, String testTitle) {
        if (numberOfQues > 0){
            Intent intent = new Intent(FindListActivity.this, FStartTestActivity.class);
            intent.putExtra(TEST_ID, testId);
            intent.putExtra(TEST_TITLE, testTitle);
            startActivity(intent);
        } else {
            showToast(this, R.string.this_test_not_completed);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
