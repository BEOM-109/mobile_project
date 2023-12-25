package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.final_project.databinding.FragmentInBinding;
import com.example.final_project.databinding.FragmentSettingBinding;
import com.example.final_project.databinding.FragmentStatisBinding;

public class InFragment extends Fragment {
    FragmentInBinding fragmentInBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentInBinding = FragmentInBinding.inflate(getLayoutInflater());

        fragmentInBinding.btnPathIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = fragmentInBinding.edtPathIn.getText().toString();

                if(path.isEmpty()){
                    Toast.makeText(getActivity(), "경로를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else{
                    Bundle bundle = new Bundle();
                    bundle.putString("PATH", path);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 2, new Intent().putExtras(bundle));

                    getFragmentManager().popBackStack();
                }
            }
        });

        return fragmentInBinding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentInBinding = null;
    }
}