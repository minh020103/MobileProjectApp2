package com.example.mobileprojectapp2.viewpager2adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobileprojectapp2.fragment.chutro.AbstractFragment;
import com.example.mobileprojectapp2.fragment.chutro.ListRoomFragment;
import com.example.mobileprojectapp2.fragment.chutro.MessageFragment;
import com.example.mobileprojectapp2.fragment.chutro.NotificationFragment;
import com.example.mobileprojectapp2.fragment.chutro.PackageUsingFragment;
import com.example.mobileprojectapp2.fragment.chutro.ProfileFragment;

public class MotelRoomOwnerViewPager2Adapter extends FragmentStateAdapter {

    public static final int LIST_ROOM = 0;
    public static final int PACKAGE_USING = 1;
    public static final int NOTIFICATION =2;
    public static final int MESSAGE = 3;
    public static final int PROFILE =4;

    private AbstractFragment fragment;
    private FragmentActivity fragmentActivity;

    public MotelRoomOwnerViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return replaceFragment(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    // replace fragment
    private Fragment replaceFragment(int screenID){
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(screenID + "") != null) {
            fragment = (AbstractFragment) fragmentManager.findFragmentByTag(screenID + "");
        } else {

            switch (screenID){
                case LIST_ROOM:
                    fragment = new ListRoomFragment();
                    break;
                case PACKAGE_USING:
                    fragment = new PackageUsingFragment();
                    break;
                case NOTIFICATION:
                    fragment = new NotificationFragment();
                    break;
                case MESSAGE:
                    fragment = new MessageFragment();
                    break;
                case PROFILE:
                    fragment = new ProfileFragment();
                    break;
                default:
                    fragment = new ListRoomFragment();
                    break;
            }
        }
        return fragment;
    }
}
