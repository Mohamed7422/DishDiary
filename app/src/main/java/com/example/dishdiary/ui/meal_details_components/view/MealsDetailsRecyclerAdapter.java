package com.example.dishdiary.ui.meal_details_components.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiary.R;
import com.example.dishdiary.data.model.dto.IngredientDTO;

import java.util.List;

public class MealsDetailsRecyclerAdapter extends RecyclerView.Adapter<MealsDetailsRecyclerAdapter.ViewHolder>{

    private Context context;

    private List<IngredientDTO> ingredientList;

    public MealsDetailsRecyclerAdapter(Context _context, List<IngredientDTO> ingredientList  ){
        this.context = _context;
        this.ingredientList = ingredientList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this is a specific product at a specific position
        IngredientDTO ingredient = ingredientList.get(position);
        holder.ingredientNam.setText(ingredient.getStrIngredient());
        holder.ingredientAmount.setText(ingredient.getStrDescription());

    }

    public void setIngredientList(List<IngredientDTO> ingredientList ) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();

    }




    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
         TextView ingredientNam,ingredientAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNam = itemView.findViewById(R.id.ingredient_name);
            ingredientAmount = itemView.findViewById(R.id.ingredient_amount);

        }
    }
}
