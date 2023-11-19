package com.example.mobileprojectapp2.viewpager2adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobileprojectapp2.fragment.chutro.AbstractFragment;
import com.example.mobileprojectapp2.fragment.chutro.ListRoomFragment;
import com.example.mobileprojectapp2.fragment.chutro.MessageFragment;
import com.example.mobileprojectapp2.fragment.chutro.NotificationFragment;
import com.example.mobileprojectapp2.fragment.chutro.PackageUsingFragment;
import com.example.mobileprojectapp2.fragment.chutro.ProfileFragment;
import com.example.mobileprojectapp2.fragment.chutro.TinNhanFragment;

import java.util.LinkedList;

public class MotelRoomOwnerViewPager2Adapter extends FragmentStateAdapter {

    public static final int LIST_ROOM = 0;
    public static final int PACKAGE_USING = 1;
    public static final int NOTIFICATION =2;
    public static final int MESSAGE = 3;
    public static final int PROFILE =4;

    private LinkedList<AbstractFragment> fragments;
    private FragmentActivity fragmentActivity;

    public void setFragments(LinkedList<AbstractFragment> fragments) {
        this.fragments = fragments;
    }

    public MotelRoomOwnerViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }


}
