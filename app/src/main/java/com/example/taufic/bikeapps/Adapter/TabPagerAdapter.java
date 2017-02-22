package com.example.taufic.bikeapps.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import static android.R.attr.fragment;

/**
 * Created by taufic on 2/19/2017.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(Fragment fragment) {
        fragments.add(fragment);
    }

    public void deleteFragment(int position) {
        fragments.remove(position);
    }

    public void deleteAllFragment() {
        fragments.clear();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
