package com.sergeyyaniuk.testity.ui.find.findList;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.FindListModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.find.findList.adapter.TestAdapter;
import com.sergeyyaniuk.testity.ui.find.findPass.FindPassActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindListActivity extends BaseActivity implements TestAdapter.OnTestSelectedListener{

    private static final String TAG = "MyLog";
    private static final String TEST_ID = "test_id";

    @Inject
    FindListPresenter mPresenter;

    @BindView(R.id.find_test_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.find_rec_view)
    RecyclerView mTestRecView;

    @BindView(R.id.no_results_tv)
    TextView mNoResultsTV;

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
        loadTestList();
        initRecyclerView();
    }

    private void loadTestList(){
        mQuery = mPresenter.getTestList();
    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.d(TAG, "initRecyclerView: query is empty");
        }
        mAdapter = new TestAdapter(mQuery, this){

            @Override
            protected void onDataChanged() {
                if (getItemCount() == 0) {
                    mTestRecView.setVisibility(View.GONE);
                    mNoResultsTV.setVisibility(View.VISIBLE);
                } else {
                    mTestRecView.setVisibility(View.VISIBLE);
                    mNoResultsTV.setVisibility(View.GONE);
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
        Intent intent = new Intent(FindListActivity.this, FindPassActivity.class);
        intent.putExtra(TEST_ID, test.getId());
        startActivity(intent);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
