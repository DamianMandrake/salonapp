package com.damian.salonapp.services.sentto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damian.salonapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 3/6/17.
 */

public class SentRecyclerAdapter extends RecyclerView.Adapter<SentRecyclerAdapter.SentToViewHolder> {

    private ArrayList<SentTo> sentTos;
    private LayoutInflater layoutInflater;
    public SentRecyclerAdapter(ArrayList<SentTo> sentTos,Context context){
        this.sentTos=sentTos;
        this.layoutInflater=LayoutInflater.from(context);
    }




    @Override
    public SentToViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SentToViewHolder(this.layoutInflater.inflate(R.layout.sent_to_recyler_item,parent));
    }

    @Override
    public void onBindViewHolder(SentToViewHolder holder, int position) {
        SentTo curr=this.sentTos.get(position);
        holder.to.setText(curr.getPhoneNum());
        holder.date.setText(curr.getDateTime());
        holder.content.setText(curr.getMsg());

    }

    @Override
    public int getItemCount() {
        return this.sentTos.size();
    }

    public static class SentToViewHolder extends RecyclerView.ViewHolder{
        TextView to,date,content;
        public SentToViewHolder(View view){
            super(view);
            to=(TextView)view.findViewById(R.id.to);
            date=(TextView)view.findViewById(R.id.time);
            content=(TextView)view.findViewById(R.id.msgContent);
        }
    }

}
