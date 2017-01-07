package com.example.user.redpocket_androidmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.redpocket_androidmanage.Data.GetData;
import com.example.user.redpocket_androidmanage.Recycler.MyDividerItemDecoration;
import com.example.user.redpocket_androidmanage.Recycler.MyRecycle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Top100Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<GetData> toplist = new ArrayList<>();
    RecyclerView queryTopview;
    MyRecycle topadapter;
    String knode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_top100);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("score-boards");
        queryTopview = (RecyclerView) findViewById(R.id.queryTopview);
        queryTopview.setHasFixedSize(true);
        queryTopview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        queryTopview.addItemDecoration (new MyDividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        Intent intent =this.getIntent ();
        Bundle bundle = intent.getExtras ();
        knode = bundle.getString ("node");
        queryTop ();



    }
    public void queryTop(){

        Query query = myRef.child (knode).child ("scores").orderByChild ("score").limitToFirst (100);
        query.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    GetData getData = new GetData();

                    getData.setDisplayName(ds.child("displayName").getValue().toString());
                    getData.setScore(ds.child("score").getValue().toString());

                    toplist.add(getData);
                }
                Collections.reverse (toplist);
                topadapter = new MyRecycle(Top100Activity.this,toplist);
                queryTopview.setAdapter(topadapter);
                topadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
