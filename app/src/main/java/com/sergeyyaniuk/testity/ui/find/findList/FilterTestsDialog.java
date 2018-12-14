package com.sergeyyaniuk.testity.ui.find.findList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.ui.base.BaseDialogNoTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilterTestsDialog extends BaseDialogNoTitle {

    @BindView(R.id.spinner_category)
    Spinner mCategorySpinner;

    @BindView(R.id.spinner_language)
    Spinner mLanguageSpinner;

    @BindView(R.id.spinner_sort)
    Spinner mSortSpinner;

    @BindView(R.id.author_editText)
    EditText mAuthorET;

    private View mRootView;
    private FilterListener mFilterListener;
    private Unbinder unbinder;

    interface FilterListener {
        void onFilter(Filters filters);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_filter_tests, container, false);
        unbinder = ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFilterListener = (FilterListener) context;
    }

    @Nullable
    private String getSelectedCategory() {
        String selected = (String) mCategorySpinner.getSelectedItem();
        if (getString(R.string.all_categories).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedLanguage() {
        String selected = (String) mLanguageSpinner.getSelectedItem();
        if (getString(R.string.all_languages).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedSortBy() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.no_sorting).equals(selected)){
            return null;
        }if (getString(R.string.sort_by_rating).equals(selected)) {
            return Test.FIELD_AVG_RATING;
        }if (getString(R.string.sort_by_popularity).equals(selected)) {
            return Test.FIELD_POPULARITY;
        }
        return null;
    }

    @Nullable
    private String getAuthorName() {
        return mAuthorET.getText().toString();
    }

    @OnClick(R.id.button_search)
    public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }
        dismiss();
    }

    @OnClick(R.id.button_cancel)
    public void onCancelClicked() {
        dismiss();
    }

    public void resetFilters() {
        if (mRootView != null) {
            mCategorySpinner.setSelection(0);
            mLanguageSpinner.setSelection(0);
            mSortSpinner.setSelection(0);
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {
            filters.setCategory(getSelectedCategory());
            filters.setLanguage(getSelectedLanguage());
            filters.setSortBy(getSelectedSortBy());
            filters.setAuthor(getAuthorName());
        }
        return filters;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFilterListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
