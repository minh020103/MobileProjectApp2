package com.example.mobileprojectapp2.viewpager2adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobileprojectapp2.fragment.loginregister.AbstractFragment;
import com.example.mobileprojectapp2.fragment.chutro.ListRoomFragment;
import com.example.mobileprojectapp2.fragment.chutro.MessageFragment;
import com.example.mobileprojectapp2.fragment.chutro.NotificationFragment;
import com.example.mobileprojectapp2.fragment.chutro.PackageUsingFragment;
import com.example.mobileprojectapp2.fragment.chutro.ProfileFragment;
import com.example.mobileprojectapp2.fragment.loginregister.LoginFragment;
import com.example.mobileprojectapp2.fragment.loginregister.RegisterFragment;

public class LoginRegisterViewPage2Adapter extends FragmentStateAdapter {

    AbstractFragment fragment;
    public static final int LOGIN = 0;
    public static final int REGISTER = 1;
    FragmentActivity fragmentActivity;

    public LoginRegisterViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
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
        return 2;
    }
    private Fragment replaceFragment(int screenID){
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(screenID + "") != null) {
            fragment = (AbstractFragment) fragmentManager.findFragmentByTag(screenID + "");
        } else {

            switch (screenID){
                case LOGIN:
                    fragment = new LoginFragment();
                    break;
                case REGISTER:
                    fragment = new RegisterFragment();
                    break;
            }
        }
        return fragment;
    }
}
