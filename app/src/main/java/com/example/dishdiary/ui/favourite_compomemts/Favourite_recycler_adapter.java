package com.example.dishdiary.ui.favourite_compomemts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiary.R;

public class Favourite_recycler_adapter extends RecyclerView.Adapter<Favourite_recycler_adapter.ViewHolder>{

    private Context context;



    public Favourite_recycler_adapter(Context _context  ){
        this.context = _context;


    }

//    public void setProductList( ) {
//       // this.productList = productList;
//        notifyDataSetChanged();
//
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_meal_item_layout, parent, false);

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
