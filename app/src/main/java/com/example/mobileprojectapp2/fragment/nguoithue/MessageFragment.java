package com.example.mobileprojectapp2.fragment.nguoithue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;

public class MessageFragment extends AbstractFragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_message_layout, container, false);

        return fragmentLayout;
    }
    @Override
    public void onResume() {
        super.onResume();
        RenterActivity.viewPager2NguoiThue.setUserInputEnabled(true);
    }
}
