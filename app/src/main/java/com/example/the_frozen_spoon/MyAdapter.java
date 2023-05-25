package com.example.the_frozen_spoon;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.the_frozen_spoon.dbdao.Product;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<com.example.the_frozen_spoon.MyAdapter.MyViewHolder> {

    private ArrayList<Product> mDataset;
    private ItemClickListener mClickListener;

    MyAdapter(ArrayList < Product > myDataset, ItemClickListener itemClickListener)
    {
        mDataset = myDataset;
        this.mClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtTitle;
        TextView txtPrice;
        TextView txtDesc;
        ImageView img_product;
        ItemClickListener itemClickListener;
        //MyViewHolder(LayoutInflater inflater, ViewGroup parent)
        MyViewHolder(View v, ItemClickListener itemClickListener)
        {
            //super(inflater.inflate(R.layout.product_list, parent, false));
            super(v);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            img_product = itemView.findViewById(R.id.img_product);

            this.itemClickListener = itemClickListener;
            v.setClickable(true);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            itemClickListener.onItemClick(getAdapterPosition());

            Log.i("test",  String.valueOf(getAdapterPosition()));
            //Intent intent = new Intent(null,MainActivity.class);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_page, parent, false);

        return new MyViewHolder(itemView,mClickListener);
        //return new MyViewHolder(layoutInflater,parent);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.txtTitle.setText(mDataset.get(position).getProduct_name());
        holder.txtPrice.setText(mDataset.get(position).getPrice());
        holder.txtDesc.setText(mDataset.get(position).getDescription());
        holder.img_product.setImageResource(mDataset.get(position).getImg());
    }

    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }


    public void setOnItemListener(ItemClickListener listener){
        this.mClickListener = listener;
        Log.i("test","s");

    }



}
