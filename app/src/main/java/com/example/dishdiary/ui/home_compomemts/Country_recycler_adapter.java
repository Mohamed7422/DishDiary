package com.example.dishdiary.ui.home_compomemts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiary.R;

public class Country_recycler_adapter extends RecyclerView.Adapter<Country_recycler_adapter.ViewHolder>{

    private Context context;

    private OnItemClickListener onItemClickListener;

    public Country_recycler_adapter(Context _context, OnItemClickListener _onItemClickListener ){
        this.context = _context;
        this.onItemClickListener = _onItemClickListener;

    }

//    public void setProductList( ) {
//       // this.productList = productList;
//        notifyDataSetChanged();
//
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this is a specific product at a specific position


    }




    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
