package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.final_project.databinding.FragmentInBinding;
import com.example.final_project.databinding.FragmentOutBinding;
import com.example.final_project.databinding.FragmentSettingBinding;
import com.example.final_project.databinding.FragmentStatisBinding;

public class OutFragment extends Fragment {
    FragmentOutBinding fragmentOutBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOutBinding = FragmentOutBinding.inflate(getLayoutInflater());

        fragmentOutBinding.btnPathOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = fragmentOutBinding.edtPathOut.getText().toString();

                if(path.isEmpty()){
                    Toast.makeText(getActivity(), "경로를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else{
                    Bundle bundle = new Bundle();
                    bundle.putString("PATH", path);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 1, new Intent().putExtras(bundle));

                    getFragmentManager().popBackStack();
                }
            }
        });

        return fragmentOutBinding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentOutBinding = null;
    }
}