package com.example.user.redpocket_androidmanage.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.redpocket_androidmanage.Data.GetData;
import com.example.user.redpocket_androidmanage.R;

import java.util.ArrayList;



public class MyRecycle extends RecyclerView.Adapter<MyHolder> {

    Context context;
    ArrayList<GetData> list;

    public MyRecycle(Context context,ArrayList<GetData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        final GetData g = list.get(position);
        holder.num.setText (""+(position+1));
        holder.displayName.setText(g.getDisplayName());
        holder.score.setText (g.getScore ());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
