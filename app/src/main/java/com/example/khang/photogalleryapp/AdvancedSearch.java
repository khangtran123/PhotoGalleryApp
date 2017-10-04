package com.example.khang.photogalleryapp;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AdvancedSearch extends AppCompatActivity {
    int currentYear, currentMonth, currentDay;
    static final int DATE_DIALOG_ID = 0;
    Button btnStartDate, btnEndDate, btnSearch, btnMenu;
    CheckBox chkLocation, chkTimeframe, chkKeywrd;
    EditText enterLocation, enterKeyword, startDate, endDate;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnMenu = (Button) findViewById(R.id.btnMenu);

        //This is for the Date
        chkTimeframe = (CheckBox) findViewById(R.id.chkDate);
        startDate = (EditText) findViewById(R.id.txtStartDate);
        endDate = (EditText) findViewById(R.id.txtEndDate);

        chkLocation = (CheckBox) findViewById(R.id.chkLocation);
        chkKeywrd = (CheckBox) findViewById(R.id.chkKeyword);
        enterLocation = (EditText) findViewById(R.id.txtLocation);
        enterKeyword = (EditText) findViewById(R.id.txtKeyword);

        startDate.setOnClickListener(new View.OnClickListener(){
            //this gets the current date, month and year from calendar
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                //This is the dialog box that pops up to allow user to pick a date
                datePickerDialog = new DatePickerDialog(AdvancedSearch.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener(){
            //this gets the current date, month and year from calendar
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                //This is the dialog box that pops up to allow user to pick a date
                datePickerDialog = new DatePickerDialog(AdvancedSearch.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdvancedSearch.this, Gallery.class));
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdvancedSearch.this, MainMenu.class));
            }
        });
    }
}
