package com.example.dishdiary.ui.weakly_plan_compomemts.view;

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
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import java.util.List;

public class WeakDishesRecyclerAdapter extends RecyclerView.Adapter<WeakDishesRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<MealPlanDTO> mealPlanList;

    private OnWeakDishClickListener onWeakDishClickListener;

    public WeakDishesRecyclerAdapter(Context _context, List<MealPlanDTO>  mealPlanList ,OnWeakDishClickListener onWeakDishClickListener ){
        this.context = _context;
        this.mealPlanList = mealPlanList;
        this.onWeakDishClickListener =onWeakDishClickListener;
    }

   public interface OnWeakDishClickListener{
      void onWeakDishClick(MealPlanDTO mealPlanDTO);
   }



    @NonNull
    @Override
    public WeakDishesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_meal_item_layout, parent, false);
        return new WeakDishesRecyclerAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WeakDishesRecyclerAdapter.ViewHolder holder, int position) {
        //this is a specific product at a specific position
        MealPlanDTO mealPlanItem = mealPlanList.get(position);
        holder.mealPlanName.setText(mealPlanItem.getMealName());
        Glide.with(context)
                .load(mealPlanItem.getStrMealThumb())
                .placeholder(R.drawable.baseline_image)
                .error(R.drawable.baseline_broken_image)
                .into(holder.mealImg);

        holder.mealPlanItem.setOnClickListener(item ->
                onWeakDishClickListener.onWeakDishClick(mealPlanItem));
     }

    public void setMealPlanList(List<MealPlanDTO> mealPlanList ) {
        this.mealPlanList = mealPlanList;
        notifyDataSetChanged();

    }




    @Override
    public int getItemCount() {
        return mealPlanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CardView mealPlanItem;
        private TextView mealPlanName;
        private ImageView mealImg;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealPlanItem = itemView.findViewById(R.id.favourite_card_view);
            mealPlanName = itemView.findViewById(R.id.meal_item_name);
            mealImg = itemView.findViewById(R.id.meal_image_item);


        }
    }
}
