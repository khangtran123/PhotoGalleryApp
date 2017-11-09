package com.example.khang.photogalleryapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class AdvancedSearch extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.khang.photogalleryapp";
    int currentYear, currentMonth, currentDay;
    static final int DATE_DIALOG_ID = 0;
    Button btnStartDate, btnEndDate, btnSearch, btnMenu;
    //CheckBox chkLocation, chkTimeframe, chkKeywrd;
    EditText enterLong, enterLat, enterKeyword, startDate, endDate;
    DatePickerDialog datePickerDialog;
    TextView dateSLabel, dateELabel, longLabel, latLabel, keywordLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnMenu = (Button) findViewById(R.id.btnMenu);

        /*These are all of the check boxes fields
        chkTimeframe = (CheckBox) findViewById(R.id.chkDate);
        chkLocation = (CheckBox) findViewById(R.id.chkLocation);
        chkKeywrd = (CheckBox) findViewById(R.id.chkKeyword); */

        //these are all of the input text fields
        startDate = (EditText) findViewById(R.id.txtStartDate);
        endDate = (EditText) findViewById(R.id.txtEndDate);
        enterLong = (EditText) findViewById(R.id.txtLong);
        enterLat = (EditText) findViewById(R.id.txtLat);
        enterKeyword = (EditText) findViewById(R.id.txtKeyword);

        //these are all of the text label fields
        dateSLabel = (TextView) findViewById(R.id.txtStart);
        dateELabel = (TextView) findViewById(R.id.txtEnd);
        longLabel = (TextView) findViewById(R.id.txtLongLabel);
        latLabel = (TextView) findViewById(R.id.txtLatLabel);
        keywordLabel = (TextView) findViewById(R.id.txtKeyLabel);

        /*
        if (chkTimeframe.isChecked()){
            dateSLabel.setVisibility(View.VISIBLE);
            dateELabel.setVisibility(View.VISIBLE);
            startDate.setVisibility(View.VISIBLE);
            endDate.setVisibility(View.VISIBLE);
        } else if(chkLocation.isChecked()){
            longLabel.setVisibility(View.VISIBLE);
            latLabel.setVisibility(View.VISIBLE);
            enterLong.setVisibility(View.VISIBLE);
            enterLat.setVisibility(View.VISIBLE);
        } else if(chkKeywrd.isChecked()){
            keywordLabel.setVisibility(View.VISIBLE);
            enterKeyword.setVisibility(View.VISIBLE);
        } */

        //exif Date format -> yyyy:mm:dd
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
                        //startDate.setText((monthOfYear + 1) + ":" + dayOfMonth + ":" + year);
                        //startDate.setText(year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
                        if((monthOfYear + 1) < 10){
                            if(dayOfMonth < 10) {
                                startDate.setText(year + ":0" + (monthOfYear + 1) + ":0" + dayOfMonth);
                            } else{
                                startDate.setText(year + ":0" + (monthOfYear + 1) + ":" + dayOfMonth);
                            }
                        } else {
                            if(dayOfMonth < 10) {
                                startDate.setText(year + ":" + (monthOfYear + 1) + ":0" + dayOfMonth);
                            }else {
                                startDate.setText(year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
                            }
                        }
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
                        if((monthOfYear + 1) < 10){
                            if(dayOfMonth < 10) {
                                endDate.setText(year + ":0" + (monthOfYear + 1) + ":0" + dayOfMonth);
                            } else{
                                endDate.setText(year + ":0" + (monthOfYear + 1) + ":" + dayOfMonth);
                            }
                        } else {
                            if(dayOfMonth < 10) {
                                endDate.setText(year + ":" + (monthOfYear + 1) + ":0" + dayOfMonth);
                            }else {
                                endDate.setText(year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
                            }
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvancedSearch.this, Gallery.class);
                String getStartD = startDate.getText().toString();
                String getEndD = endDate.getText().toString();
                String getLat = enterLat.getText().toString();
                String getLong = enterLong.getText().toString();
                String getKeys = enterKeyword.getText().toString();

                try {

                    /*
                    Create 2 Conditions before moving to gallery page
                    - Check Marks must be applied: if user clicks Time Frame, everything is disabled other than Time Block
                    1. Dates
                        a) Start Date cannot be null
                        b) End Date cannot be null
                        c) Start Date <= End Date
                        d) End Date >= Start Date
                    2. Location
                        a) Latititude cannot be null
                        b) Longitude cannot be null
                        c) 90 <= Longitude >= -90
                        d) 180 <= Latititude >= -180
                    3. Keywords
                        a) max length: 5 keywords

                    if(getLat != null && getLong != null || (getStartD != null && getEndD != null)) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        Double getLatNum = Double.parseDouble(getLat);
                        Double getLongNum = Double.parseDouble(getLong);
                        Date compDateS = formatter.parse(getStartD);
                        Date compDateE = formatter.parse(getEndD);
                        if ((getStartD != null && getEndD != null) || (getLat != null && getLong != null) || (getKeys.length() < 12)) {
                            if ((compDateS.compareTo(compDateE) < 0) || ((getLongNum <= 90 || getLongNum >= -90) && (getLatNum >= -180 || getLatNum <= 180))) {
                                //End Date is greater than the start Date --> continue
                                intent.putExtra("STARTDATE", getStartD);
                                intent.putExtra("ENDDATE", getEndD);
                                intent.putExtra("LONGITUDE", getLong);
                                intent.putExtra("LATITUDE", getLat);
                                intent.putExtra("KEYWORDS", getKeys);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } else {
                            //now popup a warning dialog box informing user that Date or Locational Fields cannot be left blank
                            AlertDialog errorDialog = new AlertDialog.Builder(AdvancedSearch.this).create();
                            errorDialog.setTitle("Attention");
                            if (getStartD == null) {
                                errorDialog.setMessage("You cannot leave the start date blank!");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            } else if (getEndD == null) {
                                errorDialog.setMessage("You cannot leave the end date blank!");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            } else if (compDateS.compareTo(compDateE) > 0) {
                                errorDialog.setMessage("Your start date cannot be greater than your end date");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            } else if (getLongNum >= 90 || getLongNum <= -90) {
                                errorDialog.setMessage("Longitude Coordinates must be within the range of -90 --> 90");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            } else if (getLatNum <= -180 || getLatNum >= 180) {
                                errorDialog.setMessage("Latitude Coordinates must be within the range of -180 --> 180");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            } else if (getLat == null) {
                                errorDialog.setMessage("You cannot leave Latitude blank!");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            } else if (getLong == null) {
                                errorDialog.setMessage("You cannot leave Longitude blank!");
                                errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
                    }*/
                    intent.putExtra("STARTDATE", getStartD);
                    intent.putExtra("ENDDATE", getEndD);
                    intent.putExtra("LONGITUDE", getLong);
                    intent.putExtra("LATITUDE", getLat);
                    intent.putExtra("KEYWORDS", getKeys);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                catch (Exception e){
                    System.out.println("Exception: " + e);
                }
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvancedSearch.this, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
