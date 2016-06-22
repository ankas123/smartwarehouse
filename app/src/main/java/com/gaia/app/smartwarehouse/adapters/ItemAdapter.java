package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.DetailActivity;
import com.gaia.app.smartwarehouse.R;

/**
 * Created by anant on 13/06/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context context;
    private String[] dataarray;

    public ItemAdapter(Context context, String[] dataarray)
    {
        this.context=context;
        this.dataarray=dataarray;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        ImageView fill;
        CardView card;
        public ViewHolder(View v){
            super(v);
            card = (CardView) v.findViewById(R.id.card_item);
            imageView = (ImageView) v.findViewById(R.id.cardrec);
            textView = (TextView) v.findViewById(R.id.cardtext);
            fill = (ImageView) v.findViewById(R.id.cardrec);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(context,DetailActivity.class);
            context.startActivity(intent);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dataarray[position]);
        holder.fill.getLayoutParams().height=20;
    }

    @Override
    public int getItemCount() {
        return dataarray.length;
    }


}