package com.example.final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.databinding.FragmentSettingBinding;
import com.example.final_project.databinding.FragmentStatisBinding;

public class SettingFragment extends Fragment {
    FragmentSettingBinding fragmentSettingBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingBinding = FragmentSettingBinding.inflate(getLayoutInflater());
        return fragmentSettingBinding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentSettingBinding = null;
    }
}