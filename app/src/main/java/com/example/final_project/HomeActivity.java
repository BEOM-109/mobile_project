package com.example.final_project;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.final_project.databinding.ActivityHomeBinding;
import com.github.mikephil.charting.components.LegendEntry;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;
    private DBManager myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        myDb = new DBManager(this);
        Cursor cursor = myDb.getAll();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();

        homeBinding.bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = new HomeFragment();

                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment, "HOME_FRAG").commit();
                        break;
                    case R.id.statistics:
                        selectedFragment = new StatisFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment, "STATIS").commit();
                        break;
                    case R.id.setting:
                        selectedFragment = new SettingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment, "SETTING").commit();
                        break;
                }

                return true;
            }
        });
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "account_book.db";
        private static final int DATABASE_VERSION = 1;

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableSql = "CREATE TABLE AccountBook (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date TEXT NOT NULL, " +
                    "category TEXT NOT NULL, " +
                    "detail TEXT, " +
                    "amount TEXT NOT NULL)";
            db.execSQL(createTableSql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String dropTableSql = "DROP TABLE IF EXISTS my_table";
            db.execSQL(dropTableSql);
            onCreate(db);
        }
    }

    public static class DBManager {
        private DatabaseHelper dbHelper;
        private SQLiteDatabase db;

        public DBManager(Context context) {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
        }

        public void insert(String date, String category, String detail, String amount) {
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("category", category);
            values.put("detail", detail);
            values.put("amount", amount);
            db.insert("AccountBook", null, values);
        }

        public void delete(String detail) {
            db.delete("AccountBook", "detail = ?", new String[]{detail});
        }

        public Cursor getAll() {
            return db.rawQuery("SELECT * FROM AccountBook;", null);
        }

        public Cursor getOnDate(String date){
            String SELECT_QUERY = "SELECT * FROM AccountBook WHERE date=?";
            Cursor cur = dbHelper.getWritableDatabase().rawQuery(SELECT_QUERY, new String[]{date});

            return cur;
        }
    }
}
