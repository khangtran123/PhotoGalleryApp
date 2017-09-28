package com.example.khang.photogalleryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdvancedSearch extends AppCompatActivity {
    int currentYear, currentMonth, currentDay;
    static final int DATE_DIALOG_ID = 0;
    Button btnStartDate, btnEndDate, btnSearch, btnMenu;
    CheckBox chkLocation, chkTimeframe, chkKeywrd;
    TextView startDate, endDate;
    EditText enterLocation, enterKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        btnStartDate = (Button) findViewById(R.id.btnStartDate);
        btnEndDate = (Button) findViewById(R.id.btnEndDate);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        startDate = (TextView) findViewById(R.id.txtStart);
        endDate = (TextView) findViewById(R.id.txtEnd);
        chkLocation = (CheckBox) findViewById(R.id.chkLocation);
        chkTimeframe = (CheckBox) findViewById(R.id.chkDate);
        chkKeywrd = (CheckBox) findViewById(R.id.chkKeyword);
        enterLocation = (EditText) findViewById(R.id.txtLocation);
        enterKeyword = (EditText) findViewById(R.id.txtKeyword);
        /*btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        if(chkLocation.isChecked()){
            enterLocation.setVisibility(View.VISIBLE);
        } else{
            enterLocation.setVisibility(View.GONE);
        }*/

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
