package com.example.myapplication.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> item= new ArrayList<>();
    private OnItemClickListener monItemClickListener;
    Context context;
    private originalviewholder view;

    /*public void setOnItemClickListener(final OnItemClickListener mItemClickListener){

    }*/
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.monItemClickListener=mItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,ProductModel obj,int post);
    }
    public AdapterProduct(List<ProductModel> item, MainActivity mainActivity){
        this.item=item;
    }
    public class originalviewholder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageButton more;
        public View lyt_parent;
        public originalviewholder(View view){
            super(view);
          image=  (ImageView)   view.findViewById(R.id.image);
          title=(TextView) view.findViewById(R.id.title);
            price=(TextView) view.findViewById(R.id.price);
            lyt_parent=(View) view.findViewById(R.id.lyt_parent);
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        viewHolder=new originalviewholder(view);
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position){
        if (holder instanceof originalviewholder) {
            originalviewholder view = (originalviewholder) holder;

            final ProductModel p = item.get(position);
            view.title.setText(p.title + "");
            view.price.setText(p.price + "");


            Glide.with(context)
                    .load(p.image)
                    .animate(R.anim.abc_fade_in)
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .centerCrop()
                    .into(view.image)
            ;


            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (monItemClickListener != null) {
                        monItemClickListener.onItemClick(view, item.get(position), position);
                    }
                }
            });
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (monItemClickListener != null) {
                        monItemClickListener.onItemClick(view, item.get(position), position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
