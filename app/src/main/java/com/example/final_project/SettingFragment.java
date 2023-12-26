package com.example.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.final_project.databinding.FragmentSettingBinding;
import com.example.final_project.databinding.FragmentStatisBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class SettingFragment extends Fragment {
    FragmentSettingBinding fragmentSettingBinding;
    String path = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingBinding = FragmentSettingBinding.inflate(getLayoutInflater());

        fragmentSettingBinding.btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InFragment inFragment = new InFragment();
                inFragment.setTargetFragment(SettingFragment.this, 0);

                getFragmentManager().beginTransaction().hide(SettingFragment.this)
                        .add(R.id.frame_container, inFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fragmentSettingBinding.btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutFragment outFragment = new OutFragment();
                outFragment.setTargetFragment(SettingFragment.this, 0);

                getFragmentManager().beginTransaction().hide(SettingFragment.this)
                        .add(R.id.frame_container, outFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return fragmentSettingBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            this.path = data.getExtras().getString("PATH");
            this.exportDatabase(getActivity());
            Toast.makeText(getActivity(), "DB 파일이 저장되었습니다.", Toast.LENGTH_LONG).show();
        } else if(resultCode == 2){
            this.path = data.getExtras().getString("PATH");
            this.importDatabase(getActivity(), this.path);

            Toast.makeText(getActivity(), "DB 파일을 불러옵니다.", Toast.LENGTH_LONG).show();
        }

    }

    public void exportDatabase(Context context) {
        try {
            File dbFile = context.getDatabasePath("/data/data/com.example.final_project/databases/account_book.db");
            FileInputStream fis = new FileInputStream(dbFile);

            String outputPath = Environment.getExternalStorageDirectory() + "/Download";

            File outputFolder = new File(outputPath);

            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            File outputFile = new File(outputFolder, this.path + ".db");

            if (!outputFile.exists()) {
                outputFolder.delete();
            }

            OutputStream output = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importDatabase(Context context, String importFilePath) {
        importFilePath = Environment.getExternalStorageDirectory() + "/Download/" + importFilePath + ".db";

        try {
            File dbFile = context.getDatabasePath("account_book.db");
            if (dbFile.exists()) {
                dbFile.delete();
            }

            File importFile = new File(importFilePath);
            FileInputStream fis = new FileInputStream(importFile);

            String outputFilePath = "/data/data/com.example.final_project/databases/account_book.db";
            OutputStream output = new FileOutputStream(outputFilePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentSettingBinding = null;
    }
}