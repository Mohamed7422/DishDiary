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
import com.example.dishdiary.data.model.dto.CategoryDTO;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>{

    private Context context;

    private OnItemClickListener onItemClickListener;
    private List<CategoryDTO> categoriesList;

    public CategoryRecyclerAdapter(Context _context,List<CategoryDTO> categoriesList, OnItemClickListener _onItemClickListener ){
        this.context = _context;
        this.onItemClickListener = _onItemClickListener;
        this.categoriesList = categoriesList;
    }

//    public void setProductList( ) {
//       // this.productList = productList;
//        notifyDataSetChanged();
//
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this is a specific product at a specific position
        CategoryDTO category = categoriesList.get(position);
        holder.categoryName.setText(category.getStrCategory());
        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.categoryImg);


    }

    public void setCategoriesList (List<CategoryDTO> list){

        this.categoriesList = list;
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

      ImageView categoryImg;
      TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           categoryImg = itemView.findViewById(R.id.category_image_item);
           categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
