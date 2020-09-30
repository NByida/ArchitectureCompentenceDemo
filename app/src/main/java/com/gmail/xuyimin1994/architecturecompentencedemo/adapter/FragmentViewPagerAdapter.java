package com.gmail.xuyimin1994.architecturecompentencedemo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = null;
    private List<Fragment> mFragmentList;

    /**
     * @param fm fm
     * @param titles titles
     * @param fragmentList fragmentList
     */
    public FragmentViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragmentList) {
        super(fm);
        mTitles = titles;
        mFragmentList = fragmentList;
    }

    public void updateTitle(String[] titles, List<Fragment> fragmentList){
        mTitles = titles;
        mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}
