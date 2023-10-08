package com.example.dishdiary.ui.search_compomemts.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdiary.Constants;
import com.example.dishdiary.R;

import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    public static final String INGREDIENT_BASE_URL = "https://www.themealdb.com/images/ingredients/";
    private Context context;
    private int state;
    private String[] flags;

    private List<MealsItemDTO> mealsItemsList;
    private List<CountryDTO> countries;
    private List<CategoryDTO> categories;
    private List<IngredientDTO> ingredientList;

    private OnCountryItemClickListener countryClickListener;
    private OnIngredientItemClickListener ingredientClickListener;
    private OnCategoryItemClickListener categoryClickListener;

    private OnMealItemClickListener onMealItemClickListener;


    public SearchRecyclerAdapter(Context context, List<MealsItemDTO> mealsItemsList,
                                 List<CountryDTO> countries, List<CategoryDTO> categories,
                                 List<IngredientDTO> ingredientList,OnMealItemClickListener onMealItemClickListener,
                                OnCountryItemClickListener onCountryItemClickListener,
                                OnIngredientItemClickListener onIngredientItemClickListener,
                                OnCategoryItemClickListener onCategoryItemClickListener, int state) {
        this.context = context;
        this.mealsItemsList = mealsItemsList;
        this.countries = countries;
        this.categories = categories;
        this.ingredientList = ingredientList;
        this.onMealItemClickListener = onMealItemClickListener;
        this.countryClickListener = onCountryItemClickListener;
        this.ingredientClickListener = onIngredientItemClickListener;
        this.categoryClickListener = onCategoryItemClickListener;
        this.state = state;
        this.flags = context.getResources().getStringArray(R.array.flags);
    }

    public void setMealsItemsList(List<MealsItemDTO> mealsItemsList) {
        this.mealsItemsList = mealsItemsList;
        this.notifyDataSetChanged();
    }
    public void setCountriesList(List<CountryDTO> countriesList) {
        this.countries = countriesList;
        this.notifyDataSetChanged();
    }
    public void setIngredientList(List<IngredientDTO> ingredientList) {
        this.ingredientList = ingredientList;
        this.notifyDataSetChanged();
    }
    public void setCategoriesList(List<CategoryDTO> categoriesList) {
        this.categories = categoriesList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {
        if (state == Constants.MEALS){
            MealsItemDTO mealsItemDTO = mealsItemsList.get(position);

            holder.itemName.setText(mealsItemDTO.getMealName());
            Glide.with(context)
                    .load(mealsItemDTO.getStrMealThumb())

                    .placeholder(R.drawable.baseline_image)
                    .error(R.drawable.baseline_broken_image)
                    .into(holder.itemImg);

            holder.itemImg.setOnClickListener(item ->{

                    onMealItemClickListener.onMealItemClickListener(mealsItemsList.get(holder.getAdapterPosition()));

            });

        } else if (state == Constants.COUNTRIES){
            CountryDTO countryDTO = countries.get(position);

            holder.itemName.setText(countryDTO.getStrArea());
            Glide.with(context)
                    .load(flags[position])
                    .placeholder(R.drawable.baseline_image)
                    .error(R.drawable.baseline_broken_image)
                    .into(holder.itemImg);

            holder.itemImg.setOnClickListener(item ->
                    countryClickListener.onCountryClickListener(
                            countries.get(holder.getAdapterPosition())));

        }else if (state == Constants.INGREDIENTS){
            IngredientDTO ingredientDTO = ingredientList.get(position);

            holder.itemName.setText(ingredientDTO.getStrIngredient());
            Glide.with(context)
                    .load(INGREDIENT_BASE_URL + ingredientDTO.getStrIngredient() + ".png")
                    .placeholder(R.drawable.baseline_image)
                    .error(R.drawable.baseline_broken_image)
                    .into(holder.itemImg);

            holder.itemImg.setOnClickListener(item ->
                    ingredientClickListener
                            .onIngredientClickListener(ingredientList
                                    .get(holder.getAdapterPosition()) ));


        }else if (state == Constants.CATEGORIES){
            CategoryDTO categoryDTO = categories.get(position);
            holder.itemName.setText(categoryDTO.getStrCategory());
            Glide.with(context)
                    .load(categoryDTO.getStrCategoryThumb())
                    .placeholder(R.drawable.baseline_image)
                    .error(R.drawable.baseline_broken_image)
                    .into(holder.itemImg);

            holder.itemImg.setOnClickListener(item ->
                    categoryClickListener.onCategoryClickListener(
                            categories.get(holder.getAdapterPosition()) ));

        }
    }




    @Override
    public int getItemCount() {
        if (state == Constants.MEALS) {
            return mealsItemsList != null ? mealsItemsList.size() : 0;
        } else if (state == Constants.COUNTRIES) {
            return countries.size();
        } else if (state == Constants.INGREDIENTS) {
            return ingredientList.size();
        }else{
            return categories.size();
        }

    }



    public interface OnCountryItemClickListener {
        void onCountryClickListener(CountryDTO country);
    }

    public interface OnIngredientItemClickListener {
        void onIngredientClickListener(IngredientDTO ingredient);
    }

    public interface OnCategoryItemClickListener {
        void onCategoryClickListener(CategoryDTO category);
    }

    public interface OnMealItemClickListener {
        void onMealItemClickListener(MealsItemDTO mealsItemDTO);
    }



    class ViewHolder extends RecyclerView.ViewHolder{
           private ImageView itemImg;
           private TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.search_image);

            itemName = itemView.findViewById(R.id.search_meal_name);

        }
    }
}
