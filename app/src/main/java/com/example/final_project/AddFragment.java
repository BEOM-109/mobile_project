package com.example.final_project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = FragmentAddBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        date = bundle.getString("Years") + "_" + bundle.getString("Months") + "_" + bundle.getString("Days");

        fragmentAddBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = bundle.getString("Years") + "_" + bundle.getString("Months") + "_" + bundle.getString("Days");
                category = fragmentAddBinding.edtClass.getText().toString();
                amount = fragmentAddBinding.edtAmount.getText().toString();
                detail = fragmentAddBinding.edtContent.getText().toString();


                if(category.isEmpty() || amount.isEmpty() || isInteger(amount)){
                    Toast.makeText(getActivity(), "정확한 값을 입력해주세요!", Toast.LENGTH_LONG).show();
                } else{
                    dbManager.insert(date, category, detail, amount);

                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    getFragmentManager().popBackStack();
                }
            }
        });

        return fragmentAddBinding.getRoot();
    }

    public static boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentAddBinding = null;
    }
}