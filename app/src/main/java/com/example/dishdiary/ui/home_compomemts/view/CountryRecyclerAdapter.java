package com.example.dishdiary.ui.home_compomemts.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdiary.R;
import com.example.dishdiary.data.model.dto.CountryDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<CountryDTO> countriesList;
    private String[] flags;

    private OnItemClickListener onItemClickListener;


    public CountryRecyclerAdapter(Context _context, List<CountryDTO> countriesList, OnItemClickListener _onItemClickListener ){
        this.context = _context;
        this.onItemClickListener = _onItemClickListener;
        this.countriesList = countriesList;
        this.flags = context.getResources().getStringArray(R.array.flags);

    }

    public void setCountriesList( List<CountryDTO> countriesList) {
       this.countriesList = countriesList;
       notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this is a specific product at a specific position
       CountryDTO countryDTO=   countriesList.get(position);
       holder.countryName.setText(countryDTO.getStrArea());
       Glide.with(context)
               .load(flags[position])
               .placeholder(R.drawable.ic_launcher_foreground)
               .error(R.drawable.ic_launcher_foreground)
               .into(holder.circleImageView);

    }




    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

         ImageView circleImageView;
         TextView countryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.country_image);
            countryName = itemView.findViewById(R.id.country_name);

        }
    }
}
