package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.final_project.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();

        homeBinding.bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = new HomeFragment();

                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.gallery:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.statistics:
                        selectedFragment = new StatisFragment();
                        break;
                    case R.id.setting:
                        selectedFragment = new SettingFragment();
                        break;
                }

                if(selectedFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                }

                return true;
            }
        });
    }
}