package com.example.final_project;

import static android.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    public FragmentHomeBinding fragmentHomeBinding;
    int years, months, days;

    private HomeActivity.DBManager dbManager;
    private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new HomeActivity.DBManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        long dataMilli = fragmentHomeBinding.calendar.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataMilli);

        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH) + 1;
        days = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentHomeBinding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                years = year; months = month + 1; days = day;
                fragmentHomeBinding.btnRefresh.performClick();
            }
        });

        fragmentHomeBinding.btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AddFragment addFragment = new AddFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Years", Integer.toString(years));
                bundle.putString("Months", Integer.toString(months));
                bundle.putString("Days", Integer.toString(days));
                addFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().hide(HomeFragment.this)
                        .add(R.id.frame_container, addFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fragmentHomeBinding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = Integer.toString(years) + "_" + Integer.toString(months) + "_" + Integer.toString(days);
                ArrayList<Data> dataList = new ArrayList<>();

                Cursor cursor = dbManager.getAll();

                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String Dates = cursor.getString(cursor.getColumnIndex("date"));
                    @SuppressLint("Range") String Amounts = cursor.getString(cursor.getColumnIndex("amount"));
                    @SuppressLint("Range") String Categories = cursor.getString(cursor.getColumnIndex("category"));
                    @SuppressLint("Range") String Details = cursor.getString(cursor.getColumnIndex("detail"));

                    if (Dates.equals(date)) {
                        Data data = new Data();
                        data.setAmount(Amounts);
                        data.setCategory(Categories);
                        data.setDetail(Details);

                        dataList.add(data);
                    }
                }

                MyAdapter adapter = new MyAdapter(getActivity(), dataList);
                fragmentHomeBinding.listView.setAdapter(adapter);
            }
        });

        fragmentHomeBinding.btnRefresh.performClick();

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();

        fragmentHomeBinding.btnRefresh.performClick();
    }

    public class Data {
        private String amount;
        private String category;
        private String detail;

        public String getAmount() {
            return amount;
        }
        public String getCategory() {
            return category;
        }
        public String getDetail() {
            return detail;
        }
        public void setAmount(String amount) {
            this.amount = amount;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    public class MyAdapter extends ArrayAdapter<Data> {
        public MyAdapter(Context context, ArrayList<Data> dataList) {
            super(context, 0, dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Data data = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
            }

            TextView amount = convertView.findViewById(R.id.amount);
            TextView category = convertView.findViewById(R.id.category);
            TextView detail = convertView.findViewById(R.id.detail);

            amount.setText(data.amount);
            category.setText(data.category);
            detail.setText(data.detail);

            return convertView;
        }
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}