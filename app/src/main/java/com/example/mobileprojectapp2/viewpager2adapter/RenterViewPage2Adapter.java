package com.example.mobileprojectapp2.viewpager2adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobileprojectapp2.fragment.nguoithue.AbstractFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.HomeFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.MessageFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.MyRoomFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.NotificationFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.ProfileFragment;

import java.util.LinkedList;
import java.util.List;

public class RenterViewPage2Adapter extends FragmentStateAdapter {
    public static final int HOME = 0;
    public static final int MY_ROOM = 1;
    public static final int NOTIFICATION = 2;
    public static final int MESSAGE = 3;
    public static final int PROFILE = 4;


    private FragmentActivity fragmentActivity;
    private LinkedList<AbstractFragment> fragments;

    public void setFragments(LinkedList<AbstractFragment> fragments) {
        this.fragments = fragments;
    }

    public RenterViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
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
