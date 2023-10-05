package com.example.dishdiary.ui.favourite_compomemts.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdiary.R;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public class Favourite_recycler_adapter extends RecyclerView.Adapter<Favourite_recycler_adapter.ViewHolder>{

    private Context context;
    private List<MealsItemDTO> mealsList;
    OnFavouriteItemClickListener onFavouriteItemClickListener;




    public Favourite_recycler_adapter(Context _context,List<MealsItemDTO> mealsList ,OnFavouriteItemClickListener onFavouriteItemClickListener ){
        this.context = _context;
        this.onFavouriteItemClickListener = onFavouriteItemClickListener;
        this.mealsList = mealsList;

    }

    public void setFavouriteItemsList( List<MealsItemDTO> mealsList ) {
        this.mealsList = mealsList;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_meal_item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealsItemDTO mealItem = mealsList.get(position);
        holder.favouriteTV.setText(mealItem.getMealName());
        Glide.with(context)
                .load(mealItem.getStrMealThumb())
                .placeholder(R.drawable.baseline_image)
                .error(R.drawable.baseline_broken_image)
                .into(holder.favouriteImage);

        holder.favouriteCardView.setOnClickListener(item ->
                onFavouriteItemClickListener.onFavouriteItemClick(mealItem));
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    protected interface OnFavouriteItemClickListener{
        void onFavouriteItemClick(MealsItemDTO mealsItemDTO);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CardView favouriteCardView;
        private ImageView favouriteImage;
        private TextView favouriteTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favouriteCardView = itemView.findViewById(R.id.favourite_card_view);
            favouriteImage = itemView.findViewById(R.id.meal_image_item);
            favouriteTV = itemView.findViewById(R.id.meal_item_name);

        }
    }
}
