package com.example.final_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.final_project.databinding.FragmentAddBinding;
import com.example.final_project.databinding.FragmentStatisBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisFragment extends Fragment {

    HomeActivity.DBManager dbManager;
    FragmentStatisBinding binding;

    int[] pastelColors = new int[]{
            Color.rgb(198, 219, 218), // pastel1
            Color.rgb(254, 225, 232), // pastel2
            Color.rgb(254, 215, 195), // pastel3
            Color.rgb(246, 234, 194), // pastel4
            Color.rgb(236, 213, 227) // pastel5
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.statistics_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edtYear.setAdapter(yearAdapter);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.statistics_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edtMonth.setAdapter(monthAdapter);

        binding.chart.getDescription().setEnabled(false);


        // Initialize the dbManager
        dbManager = new HomeActivity.DBManager(getContext());

        // Call updateChart when the view is created
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼이 클릭되면 차트를 업데이트합니다.
                updateChart();
            }
        });

        return view;
    }
    public void updateChart() {
        String selectedYear = binding.edtYear.getSelectedItem().toString();
        String selectedMonth = binding.edtMonth.getSelectedItem().toString();
        if (selectedMonth.length() == 1) {
            selectedMonth = "0" + selectedMonth; // 1~9월을 01~09월로 표시
        }

        String selectedDate = selectedYear + "_" + selectedMonth;

        // DB에서 모든 데이터 가져오기
        Cursor cursor = dbManager.getAll();

        Map<String, Float> dataMap = new HashMap<>();
        float totalAmount = 0;

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            String[] splitDate = date.split("_");
            if (splitDate.length >= 2) {
                String yearMonth = splitDate[0] + "_" + splitDate[1]; // 년도와 월 추출

                if (yearMonth.equals(selectedDate)) {
                    @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                    @SuppressLint("Range") float amount = cursor.getFloat(cursor.getColumnIndex("amount"));

                    if (dataMap.containsKey(category)) {
                        dataMap.put(category, dataMap.get(category) + amount);
                    } else {
                        dataMap.put(category, amount);
                    }
                    totalAmount += amount; // 합계 계산
                }
            }
        }

        cursor.close();

        List<PieEntry> entries = new ArrayList<>();

        for(Map.Entry<String, Float> entry: dataMap.entrySet()) {
            float percentage = entry.getValue() / totalAmount; // 퍼센트 계산
            entries.add(new PieEntry(percentage, entry.getKey())); // 퍼센트를 이용한 PieEntry 생성
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(pastelColors);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        binding.chart.setData(data);
        binding.chart.invalidate(); // 차트 갱신
    }
}