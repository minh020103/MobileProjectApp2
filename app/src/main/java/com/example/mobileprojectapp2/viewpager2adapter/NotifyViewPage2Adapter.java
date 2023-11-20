package com.example.mobileprojectapp2.viewpager2adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobileprojectapp2.fragment.loginregister.LoginFragment;
import com.example.mobileprojectapp2.fragment.loginregister.RegisterFragment;
import com.example.mobileprojectapp2.fragment.thongbao.AbstractFragment;
import com.example.mobileprojectapp2.fragment.thongbao.KetQuaFragment;
import com.example.mobileprojectapp2.fragment.thongbao.YeuCauFragment;

import java.util.LinkedList;

public class NotifyViewPage2Adapter extends FragmentStateAdapter {

    public static final int RESULT_NOTIFY = 0;
    public static final int REQUEST_NOTIFY = 1;

    FragmentActivity fragmentActivity;
    AbstractFragment fragment;

    public NotifyViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
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
                case RESULT_NOTIFY:
                    fragment = new KetQuaFragment();
                    break;
                case REQUEST_NOTIFY:
                    fragment = new YeuCauFragment();
                    break;
            }
        }
        return fragment;
    }
}
