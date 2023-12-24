package com.example.final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.databinding.FragmentAddBinding;
import com.example.final_project.databinding.FragmentHomeBinding;

public class AddFragment extends Fragment {
    FragmentAddBinding fragmentAddBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = FragmentAddBinding.inflate(getLayoutInflater());

        fragmentAddBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return fragmentAddBinding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentAddBinding = null;
    }
}