package com.example.final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.databinding.FragmentAddBinding;
import com.example.final_project.databinding.FragmentStatisBinding;

public class StatisFragment extends Fragment {
    FragmentStatisBinding fragmentStatisBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStatisBinding = FragmentStatisBinding.inflate(getLayoutInflater());
        return fragmentStatisBinding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentStatisBinding = null;
    }
}