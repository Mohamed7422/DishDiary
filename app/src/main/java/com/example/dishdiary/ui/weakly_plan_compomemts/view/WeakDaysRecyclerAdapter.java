package com.example.dishdiary.ui.weakly_plan_compomemts.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dishdiary.R;
import java.util.List;
public class WeakDaysRecyclerAdapter extends RecyclerView.Adapter<WeakDaysRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<String> daysList;
    private OnDayCardClickListener onDayCardClickListener;
      int dayItemSelected = 0;

    public WeakDaysRecyclerAdapter(Context _context, List<String> days,OnDayCardClickListener onDayCardClickListener ){
        this.context = _context;
        this.daysList = days;
        this.onDayCardClickListener = onDayCardClickListener;

    }

    public interface OnDayCardClickListener {
        void onDayCardClick(String day);
    }



    @NonNull
    @Override
    public WeakDaysRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item_layout, parent, false);
        return new WeakDaysRecyclerAdapter.ViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull WeakDaysRecyclerAdapter.ViewHolder holder, int position) {
        String daySelected = daysList.get(position);
        holder.dayName.setText(daySelected);

        holder.dayCardView.setOnClickListener(item ->{

         dayItemSelected = holder.getAdapterPosition();
         onDayCardClickListener.onDayCardClick(daysList.get(holder.getAdapterPosition()));
         notifyDataSetChanged();

        });


    }

    public void setDaysList(List<String> daysList) {
        this.daysList = daysList;
        notifyDataSetChanged();

    }




    @Override
    public int getItemCount() {
        return daysList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

         private CardView dayCardView;
         private TextView dayName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayCardView = itemView.findViewById(R.id.dayCard);
            dayName = itemView.findViewById(R.id.day_name);


        }
    }
}
