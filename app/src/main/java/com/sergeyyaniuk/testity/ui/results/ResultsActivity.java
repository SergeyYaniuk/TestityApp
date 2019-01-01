package com.sergeyyaniuk.testity.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.ResultsModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsActivity extends BaseActivity implements LocalResultsFragment.LocalResultsListener,
        OnlineResultsFragment.OnlineResultsListener, DeleteLocalResultDialog.DeleteLocalResultListener,
        DeleteOnlineResultDialog.DeleteOnlineResultListener {

    public static final String RESULT_ID = "result_id";
    public static final String POSITION = "position";

    @Inject
    ResultsPresenter mPresenter;

    LocalResultsFragment mLocalFragment;
    OnlineResultsFragment mOnlineFragment;

    @BindView(R.id.results_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        App.get(this).getAppComponent().create(new ResultsModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        mLocalFragment = new LocalResultsFragment();
        mOnlineFragment = new OnlineResultsFragment();
        setBottomNavListener();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle.setText(R.string.results);
    }

    private void setBottomNavListener(){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new Fragment();
                switch (menuItem.getItemId()) {
                    case R.id.action_local_results:
                        fragment = mLocalFragment;
                        break;
                    case R.id.action_online_results:
                        fragment = mOnlineFragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_local_results);
    }

    @Override
    public void onLocalResultDeleted(String resultId, int position) {
        DeleteLocalResultDialog dialog = new DeleteLocalResultDialog();
        Bundle arguments = new Bundle();
        arguments.putString(RESULT_ID, resultId);
        arguments.putInt(POSITION, position);
        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(), "dialog_delete_local_result");
    }

    @Override
    public void onConfDelLocalResult(String resultId, int position) {
        mPresenter.deleteLocalResult(resultId);
        mLocalFragment.removeResult(position);
    }

    @Override
    public void onCancelDelLocalResult() {
        mLocalFragment.notifyAdapterAboutChanges();
    }

    @Override
    public void onOnlineResultDelete(String resultId, int position) {
        DeleteOnlineResultDialog dialog = new DeleteOnlineResultDialog();
        Bundle arguments = new Bundle();
        arguments.putString(RESULT_ID, resultId);
        arguments.putInt(POSITION, position);
        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(), "dialog_delete_online_result");
    }

    @Override
    public void onConfDelOnlineResult(String resultId, int position) {
        mPresenter.deleteOnlineResult(resultId);
        mOnlineFragment.removeResult(position);
    }

    @Override
    public void onCancelDelOnlineResult() {
        mOnlineFragment.notifyAdapterAboutChanges();
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
