package com.example.final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.databinding.ActivityMainBinding;
import com.example.final_project.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        fragmentHomeBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragment addFragment = new AddFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, addFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}