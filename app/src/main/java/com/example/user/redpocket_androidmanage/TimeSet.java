package com.example.user.redpocket_androidmanage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.redpocket_androidmanage.Data.ScoreBoardTime;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeSet extends AppCompatActivity {
    Button startDateSet, endDateSet, TimeDoneBtn;
    TextView startDateShow, endDateShow;
    GregorianCalendar gregorianCalendar = new GregorianCalendar ();
    String dayStart, monthStart, yearStart;
    String dayFinal, monthFinal, yearFinal;
    DatePickerDialog startdatePickerDialog, finaldatePickerDialog;
    String startDate, endDate;
    Timestamp timeForStartDate, timeForEndDate;
    Long startTimetoLong, endTimetoLong;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set);

        startDateSet = (Button) findViewById (R.id.startDateSet);
        startDateShow = (TextView) findViewById (R.id.startDateShow);

        TimeDoneBtn = (Button)findViewById (R.id.TimeDoneBtn);


        startdatePickerDialog = new DatePickerDialog (TimeSet.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                yearStart = DateFix (year);
                monthStart = DateFix (month);
                dayStart = DateFix (dayOfMonth);
                startDateShow.setText (yearStart + "/" + monthStart + "/" + dayStart);

                startDate = yearStart+"-"+monthStart+"-"+dayStart+" "+"00:00:00";
                timeForStartDate = Timestamp.valueOf (startDate);
                startTimetoLong = timeForStartDate.getTime ()/1000;
            }
        }, gregorianCalendar.get (Calendar.YEAR), gregorianCalendar.get (Calendar.MONTH), gregorianCalendar.get (Calendar.DAY_OF_MONTH));


        startDateSet.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startdatePickerDialog.show ();
            }
        });


        endDateSet = (Button) findViewById (R.id.endDateSet);
        endDateShow = (TextView) findViewById (R.id.endDateShow);

        finaldatePickerDialog = new DatePickerDialog (TimeSet.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                yearFinal = DateFix (year);
                monthFinal = DateFix (month);
                dayFinal = DateFix (dayOfMonth);
                endDateShow.setText (yearFinal + "/" + monthFinal + "/" + dayFinal);

                endDate = yearFinal+"-"+monthFinal+"-"+dayFinal+" "+"23:59:59";
                timeForEndDate = Timestamp.valueOf (endDate);
                endTimetoLong = timeForEndDate.getTime ()/1000;
            }
        }, gregorianCalendar.get (Calendar.YEAR), gregorianCalendar.get (Calendar.MONTH), gregorianCalendar.get (Calendar.DAY_OF_MONTH));

        endDateSet.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finaldatePickerDialog.show ();
            }
        });


        TimeDoneBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                alertDialog ("時間設定","起始日:" + startDateShow.getText () + "\n" + "截止日:" + endDateShow.getText () ).show ();
            }
        });

    }
    private static String DateFix(int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void checkandUpdate (){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference ("score-boards");

        myRef.orderByChild ("endDateInterval").limitToLast (1).addChildEventListener (new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child ("endDateInterval").getValue () != null)
                {
                    long endTime = (long) dataSnapshot.child ("endDateInterval").getValue ();
                    int times =  Integer.parseInt(dataSnapshot.getKey ());

                    if (endTime > startTimetoLong || endTime >endTimetoLong || startTimetoLong > endTimetoLong){

                    }
                    else {

                        String key = myRef.push ().getKey ();
                        times = times + 1;
                        String ntimes;
                        ntimes = String.valueOf (times);
                        ScoreBoardTime scoreBoardTime = new ScoreBoardTime (key, startTimetoLong, endTimetoLong);
                        myRef.child (ntimes).setValue (scoreBoardTime);

                    }
                }
                else {
                    String key = myRef.push ().getKey ();
                    int times = 0 ;
                    String ntimes;
                    ntimes = String.valueOf (times);
                    ScoreBoardTime scoreBoardTime = new ScoreBoardTime (key, startTimetoLong, endTimetoLong);
                    myRef.child (ntimes).setValue (scoreBoardTime);
                }




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    private AlertDialog alertDialog (String tittle, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder (TimeSet.this);
        builder.setTitle (tittle);
        builder.setMessage (message);
        builder.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkandUpdate ();
                Intent reIntent = getIntent ();
                finish ();
                startActivity (reIntent);
            }
        });
        builder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent reIntent = getIntent ();
                finish ();
                startActivity (reIntent);
            }
        });
        return builder.create ();




    }


}
