package com.example.jespe.initiativiet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 09-01-2018.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> FragList = new ArrayList<>();
    private final List<String> FragTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        FragList.add(fragment);
        FragTitleList.add(title);
    }
    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragList.get(position);
    }

    @Override
    public int getCount() {
        return FragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragTitleList.get(position);
    }
}
