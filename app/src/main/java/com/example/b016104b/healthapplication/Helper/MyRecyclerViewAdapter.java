package com.example.b016104b.healthapplication.Helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b016104b.healthapplication.R;


import java.util.ArrayList;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private ArrayList<Ticket> feedItemList;
    private Context mContext;
    private AdapterView.OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, ArrayList<Ticket> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_fragment, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Ticket feedItem = feedItemList.get(i);

        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
//            Picasso.with(mContext).load(feedItem.getThumbnail())
//                    .error(R.drawable.lock)
//                    .placeholder(R.drawable.lock)
//                    .into(customViewHolder.imageView);
        }

        //Setting text view title
        customViewHolder.textViewE.setText((feedItem.getName()));
        customViewHolder.textViewD.setText((feedItem.getDesc()));
        customViewHolder.textViewP.setText((feedItem.getPriceS()));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onItemClickListener.onItemClick(feedItem);
                //if (v.getId() == feedItem.getId()){
                    //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(customViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                //} else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(customViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                //Intent myIntent = new Intent(v.getContext(), Tickets_View.class);
                //myIntent.putExtra("id", customViewHolder.getAdapterPosition());
                //mContext.startActivity(myIntent);

                //}
            }
        };
        customViewHolder.imageView.setOnClickListener(listener);
        //customViewHolder.textViewE.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (feedItemList.size());
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textViewE;
        protected TextView textViewD;
        protected TextView textViewP;

        public CustomViewHolder(View view) {
            super(view);
//            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
//            this.textViewE = (TextView) view.findViewById(R.id.title);
//            this.textViewD = (TextView) view.findViewById(R.id.tDesc);
//            this.textViewP = (TextView) view.findViewById(R.id.tPrice);
        }
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}