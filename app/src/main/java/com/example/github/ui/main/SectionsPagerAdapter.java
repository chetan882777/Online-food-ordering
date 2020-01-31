package com.example.github.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.github.R;
import com.example.github.ui.main.repos.ReposFragment;
import com.example.github.ui.main.user.UserFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private final UserFragment userFragment;
    private final ReposFragment reposFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        userFragment = new UserFragment();
        reposFragment = new ReposFragment();
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return userFragment;
        }
        return reposFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    public UserFragment getUserFragment() {
        return userFragment;
    }

    public ReposFragment getReposFragment() {
        return reposFragment;
    }
}