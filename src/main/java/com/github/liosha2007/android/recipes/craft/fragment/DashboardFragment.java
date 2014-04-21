package com.github.liosha2007.android.recipes.craft.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;

/**
 * Created by liosha on 21.04.2014.
 */
public class DashboardFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState, R.layout.layout_dashboard);
    }
}
