package com.example.final_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.final_project.databinding.FragmentAddBinding;
import com.example.final_project.databinding.FragmentStatisBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
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
            Color.rgb(233, 154, 154),
            Color.rgb(248, 203, 159),
            Color.rgb(183, 214, 170),
            Color.rgb(165, 195, 242),
            Color.rgb(180, 168, 213)
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

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

        dbManager = new HomeActivity.DBManager(getContext());

        binding.edtYear.setSelection(1);
        binding.edtMonth.setSelection(11);
        updateChart();

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart();
            }
        });

        return view;
    }
    public void updateChart() {
        List<PieEntry> entries = new ArrayList<>();

        String selectedYear = binding.edtYear.getSelectedItem().toString();
        String selectedMonth = binding.edtMonth.getSelectedItem().toString();

        Cursor cursor = dbManager.getAll();
        float totalAmount = 0;

        Map<String, Float> dataMap = new HashMap<>();

        String selectedDate = selectedYear + "_" + selectedMonth;

        binding.bestItem1.setText("");
        binding.bestItem2.setText("");
        binding.bestItem3.setText("");
        binding.bestItem4.setText("");
        binding.bestItem5.setText("");

        if (selectedMonth.length() == 1) {
            selectedMonth = "0" + selectedMonth;
        }

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            String[] splitDate = date.split("_");
            if (splitDate.length >= 2) {
                String yearMonth = splitDate[0] + "_" + splitDate[1];

                if (yearMonth.equals(selectedDate)) {
                    @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                    @SuppressLint("Range") float amount = cursor.getFloat(cursor.getColumnIndex("amount"));

                    if (dataMap.containsKey(category)) {
                        dataMap.put(category, dataMap.get(category) + amount);
                    } else {
                        dataMap.put(category, amount);
                    }

                    totalAmount += amount;
                }
            }
        }

        cursor.close();

        for(Map.Entry<String, Float> entry: dataMap.entrySet()) {
            float percentage = (entry.getValue() / totalAmount) * 100;
            entries.add(new PieEntry(percentage, entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(pastelColors);
        dataSet.setValueTextSize(20f);
        dataSet.setValueTextColor(Color.BLACK);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        binding.chart.setEntryLabelColor(Color.BLACK);
        binding.chart.setEntryLabelTextSize(20f);
        binding.chart.getLegend().setTextSize(20f);
        binding.chart.getLegend().setTextColor(Color.BLACK);
        binding.chart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        binding.chart.getLegend().setForm(Legend.LegendForm.CIRCLE);

        binding.chart.setData(data);
        binding.chart.invalidate();

        TextView[] bestItems = new TextView[]{
                binding.bestItem1,
                binding.bestItem2,
                binding.bestItem3,
                binding.bestItem4,
                binding.bestItem5
        };

        binding.sum.setText("총 금액: " + (int) totalAmount + "원");

        for (int i = 0; i < bestItems.length && i < entries.size(); i++) {
            bestItems[i].setText(entries.get(i).getLabel() + ": " + Math.round(dataMap.get(entries.get(i).getLabel())) + "원 / "
                    + Math.round(entries.get(i).getValue() * 100) / 100.0 + "%");
            bestItems[i].setTextColor(Color.BLACK);
        }
    }
}
