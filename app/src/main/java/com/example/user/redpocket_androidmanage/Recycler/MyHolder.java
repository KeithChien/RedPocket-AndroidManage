package com.example.user.redpocket_androidmanage.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.redpocket_androidmanage.R;


public class MyHolder extends RecyclerView.ViewHolder{
    TextView displayName;
    TextView score;

    public MyHolder(View itemView) {
        super(itemView);
        displayName = (TextView)itemView.findViewById(R.id.displayName);
        score = (TextView)itemView.findViewById(R.id.score);
    }
}