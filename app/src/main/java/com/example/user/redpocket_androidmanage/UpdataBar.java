package com.example.user.redpocket_androidmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UpdataBar extends AppCompatActivity {
    EditText barInfoUpdate ;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button cancelBtn ;
    Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_updata_bar);
        barInfoUpdate =(EditText)findViewById (R.id.barText);
        doneBtn = (Button)findViewById (R.id.doneBtn);
        cancelBtn = (Button)findViewById (R.id.cancelBtn);
        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("systemPreferences");

        myRef.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                barInfoUpdate.setText (dataSnapshot.child ("barInfoURLString").getValue ().toString ());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        doneBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Map<String,Object> childUpdates = new HashMap<String, Object>();
                childUpdates.put ("barInfoURLString",barInfoUpdate.getText ().toString ());
                myRef.updateChildren (childUpdates);
            }
        });
        cancelBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent gobackIntent = new Intent ();
                gobackIntent.setClass (UpdataBar.this, MainActivity.class);
                startActivity (gobackIntent);
                finish ();
            }
        });


    }
}
