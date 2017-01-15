package com.example.user.redpocket_androidmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.redpocket_androidmanage.Data.GetData;
import com.example.user.redpocket_androidmanage.Recycler.MyDividerItemDecoration;
import com.example.user.redpocket_androidmanage.Recycler.MyRecycle;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AllUserScore extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    ArrayList<GetData> userlist = new ArrayList<> ();
    RecyclerView userRcycler;
    MyRecycle useradapter;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_all_user_score);
        userRcycler = (RecyclerView) findViewById(R.id.userRcycler);
        userRcycler.setHasFixedSize(true);
        userRcycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        userRcycler.addItemDecoration (new MyDividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference ("score-boards");
        myRef.addChildEventListener (new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                long startTime = (long) dataSnapshot.child ("startDateInterval").getValue ();
                long endTime = (long) dataSnapshot.child ("endDateInterval").getValue ();
                Timestamp timestamp1 = new Timestamp (startTime);
                Timestamp timestamp2 = new Timestamp (endTime);
                Long tslong = System.currentTimeMillis () / 1000;
                Timestamp timestamp4 = new Timestamp (tslong);


                if (timestamp1.before (timestamp4) && timestamp2.after (timestamp4)) {


                    key = dataSnapshot.child ("id").getValue ().toString ();
                    queryTop ();
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

    public void queryTop() {
        Query query =database.getReference ("scores").child (key);
        query.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren ()) {

                    GetData getData = new GetData ();

                    getData.setDisplayName(ds.child("displayName").getValue().toString());
                    getData.setScore(ds.child("score").getValue().toString());

                    userlist.add (getData);

                }

                useradapter = new MyRecycle (AllUserScore.this, userlist);
                userRcycler.setAdapter (useradapter);
                useradapter.notifyDataSetChanged ();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
