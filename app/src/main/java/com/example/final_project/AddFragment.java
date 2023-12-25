package com.example.final_project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.final_project.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {
    FragmentAddBinding fragmentAddBinding;
    String date = "";
    String category = "";
    String amount = "";
    String detail = "";
    int count = 0;

    private HomeActivity.DBManager dbManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new HomeActivity.DBManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = FragmentAddBinding.inflate(getLayoutInflater());
        count = 0;

        Bundle bundle = getArguments();
        date = bundle.getString("Years") + "_" + bundle.getString("Months") + "_" + bundle.getString("Days");

        fragmentAddBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = bundle.getString("Years") + "_" + bundle.getString("Months") + "_" + bundle.getString("Days");
                category = fragmentAddBinding.edtClass.getText().toString();
                amount = fragmentAddBinding.edtAmount.getText().toString();
                detail = fragmentAddBinding.edtContent.getText().toString();


                if(category.isEmpty() && amount.isEmpty()){
                    Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
                } else{
                    count++;
                    dbManager.insert(date, category, detail, amount);

                    getFragmentManager().popBackStack();
                }
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