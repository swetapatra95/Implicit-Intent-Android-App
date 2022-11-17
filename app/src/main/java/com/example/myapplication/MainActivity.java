package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int PHOTO_REQUEST_CODE = 1200;
    TextView btnDatePickerStart, btnTimePickerStart,btnDatePickerEnd, btnTimePickerEnd;
    EditText title,strDescription,attendes,phoneNo;
    private int mYearStart, mMonthStart, mDayStart, mHourStart, mMinuteStart, mYearEnd, mMonthEnd, mDayEnd, mHourEnd, mMinuteEnd;
    CheckBox allDayEvent;
    Switch access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDatePickerStart = (TextView) findViewById(R.id.btn_datestart);
        btnTimePickerStart = (TextView) findViewById(R.id.btn_timestart);
        btnDatePickerEnd = (TextView) findViewById(R.id.btn_dateend);
        btnTimePickerEnd = (TextView) findViewById(R.id.btn_timeend);
        title = (EditText) findViewById(R.id.etTitle);
        strDescription = (EditText) findViewById(R.id.etDescription);
        allDayEvent = (CheckBox) findViewById(R.id.checkbox);
        attendes = (EditText) findViewById(R.id.etEmails);
        access = (Switch) findViewById(R.id.accessSwitch);
        phoneNo = (EditText) findViewById(R.id.etPhone);

    }


    public void btnDatePickerStart(View view) {


            // Get Current Date
            Calendar c = Calendar.getInstance();
            mYearStart = c.get(Calendar.YEAR);
            mMonthStart = c.get(Calendar.MONTH);
            mDayStart = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            btnDatePickerStart.setText(String.format(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                            mYearStart = year;
                            mMonthStart = monthOfYear + 1;
                            mDayStart = dayOfMonth;
                        }
                    }, mYearStart, mMonthStart, mDayStart);
            datePickerDialog.show();

    }
    public void btnTimePickerStart(View view) {


            // Get Current Time
            Calendar c = Calendar.getInstance();
            mHourStart = c.get(Calendar.HOUR_OF_DAY);
            mMinuteStart = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            btnTimePickerStart.setText(String.format(Locale.getDefault() ,"%02d:%02d",hourOfDay,minute));
                            mHourStart = hourOfDay;
                            mMinuteStart = minute;
                        }
                    }, mHourStart, mMinuteStart, false);
            timePickerDialog.show();

    }

    public void btnDatePickerEnd(View view) {


        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYearEnd = c.get(Calendar.YEAR);
        mMonthEnd = c.get(Calendar.MONTH);
        mDayEnd = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        btnDatePickerEnd.setText(String.format(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                           mYearEnd = year;
                           mMonthEnd = monthOfYear + 1;
                           mDayEnd = dayOfMonth;
                    }
                }, mYearEnd, mMonthEnd, mDayEnd);

        datePickerDialog.show();

    }
    public void btnTimePickerEnd(View view) {


        // Get Current Time
        Calendar c = Calendar.getInstance();
        mHourEnd = c.get(Calendar.HOUR_OF_DAY);
        mMinuteEnd = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        btnTimePickerEnd.setText(String.format(hourOfDay + ":" + minute));
                           mHourEnd = hourOfDay;
                           mMinuteEnd = minute;
                    }
                }, mHourEnd, mMinuteEnd, false);
        timePickerDialog.show();

    }

    public void addEventClicked(View view) {

        Calendar calendarStart = Calendar.getInstance();

        if (!btnDatePickerStart.getText().toString().isEmpty()) {

            calendarStart.set(Calendar.YEAR, mYearStart);
            calendarStart.set(Calendar.MONTH, mMonthStart-1);
            calendarStart.set(Calendar.DAY_OF_MONTH, mDayStart);
        }
        if (!btnTimePickerStart.getText().toString().isEmpty()) {

            calendarStart.set(Calendar.HOUR_OF_DAY, mHourStart);
            calendarStart.set(Calendar.MINUTE, mMinuteStart);
        }


        Date startDateTime = calendarStart.getTime();

        Calendar calendarEnd = Calendar.getInstance();


        if (!btnDatePickerEnd.getText().toString().isEmpty()) {

            calendarEnd.set(Calendar.YEAR, mYearEnd);
            calendarEnd.set(Calendar.MONTH, mMonthEnd-1);
            calendarEnd.set(Calendar.DAY_OF_MONTH, mDayEnd);
        }

        if (!btnTimePickerEnd.getText().toString().isEmpty()) {

            calendarEnd.set(Calendar.HOUR_OF_DAY, mHourEnd);
            calendarEnd.set(Calendar.MINUTE, mMinuteEnd);
        }
        Date endTime = calendarEnd.getTime();


        int accessLevel = CalendarContract.Events.ACCESS_DEFAULT;

        if(access.isChecked()) {
            accessLevel = CalendarContract.Events.ACCESS_PRIVATE;
        }
        else {
            accessLevel = CalendarContract.Events.ACCESS_PUBLIC;
        }

        Intent intent = new Intent(Intent.ACTION_EDIT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDateTime.getTime())
                .putExtra(CalendarContract.Events.TITLE, title.getText().toString())
                .putExtra(CalendarContract.Events.DESCRIPTION, strDescription.getText().toString())
                .putExtra(CalendarContract.Events.ALL_DAY, allDayEvent.isChecked())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime.getTime())
                .putExtra(Intent.EXTRA_EMAIL,attendes.getText().toString())
                .putExtra(CalendarContract.Events.ACCESS_LEVEL, accessLevel);

        startActivity(intent);
    }


    public void photoClicked(View view) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoIntent, PHOTO_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //Display the photo in Image View
        ImageView imageView = findViewById(R.id.imageView);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CODE) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
    public void callClicked(View view) {
        String phone_number = phoneNo.getText().toString();

        // Getting instance of Intent with action as ACTION_DIAL
        Intent phone_intent = new Intent(Intent.ACTION_DIAL);

        // Set data of Intent through Uri by parsing phone number
        phone_intent.setData(Uri.parse("tel:" + phone_number));
            startActivity(phone_intent);


    }
    }




