package com.example.dishdiary.ui.meal_details_components.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.RemoteSource;
import com.example.dishdiary.ui.meal_details_components.presenter.IMealDetailsPresenter;
import com.example.dishdiary.ui.meal_details_components.presenter.MealsDetailsPresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDetailActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView mealName;
    private ImageView addMealToFavouriteBtn;
    private ImageView addToCalendar;
    private RecyclerView ingredientItemRV;
    private MealsDetailsRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private TextView recipeContent;
    private YouTubePlayerView videoContentPlayer;
    boolean clicked = false;
    List<IngredientDTO> ingredientDTOList = new ArrayList<>();
    MealsItemDTO mealItemDto;
    MealPlanDTO mealPlanDTO;
    IMealDetailsPresenter mealDetailsPresenter;
    private int selectedItemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        MealsItemDTO mealItem = getIntent().getParcelableExtra("mealItem");
        mealItemDto = mealItem;

        initViews();
        mealPlanDTO = new MealPlanDTO(
                mealItem.getIdMeal(), mealItem.getMealName(), mealItem.getStrDrinkAlternate(), mealItem.getStrCategory(), mealItem.getStrArea(), mealItem.getStrInstructions(), mealItem.getStrMealThumb(), mealItem.getStrTags(), mealItem.getStrYoutube(), mealItem.getStrIngredient1(), mealItem.getStrIngredient2(), mealItem.getStrIngredient3(), mealItem.getStrIngredient4(), mealItem.getStrIngredient5(),
                mealItem.getStrIngredient6(), mealItem.getStrIngredient7(), mealItem.getStrIngredient8(), mealItem.getStrIngredient9(), mealItem.getStrIngredient10(), mealItem.getStrIngredient11(), mealItem.getStrIngredient12(), mealItem.getStrIngredient13(), mealItem.getStrIngredient14(), mealItem.getStrIngredient15(), mealItem.getStrIngredient16(), mealItem.getStrIngredient17(),
                mealItem.getStrIngredient18(), mealItem.getStrIngredient19(), mealItem.getStrIngredient20(), mealItem.getStrMeasure1(), mealItem.getStrMeasure2(), mealItem.getStrMeasure3(), mealItem.getStrMeasure4(), mealItem.getStrMeasure5(), mealItem.getStrMeasure6(), mealItem.getStrMeasure7(), mealItem.getStrMeasure8(), mealItem.getStrMeasure9(), mealItem.getStrMeasure10(), mealItem.getStrMeasure11(), mealItem.getStrMeasure12(), mealItem.getStrMeasure13(), mealItem.getStrMeasure14(), mealItem.getStrMeasure15(), mealItem.getStrMeasure16(), mealItem.getStrMeasure17(), mealItem.getStrMeasure18(), mealItem.getStrMeasure19(), mealItem.getStrMeasure20(), mealItem.getStrSource(), mealItem.getStrImageSource(), mealItem.getStrCreativeCommonsConfirmed(), mealItem.getDateModified(), ""
        );

        //For Item Image Loading
        Glide.with(this)
                .load(mealItem.getStrMealThumb())
                .placeholder(R.drawable.baseline_image)
                .error(R.drawable.baseline_broken_image)
                .into(itemImage);

        //For Appending  Meal Name
        mealName.setText(mealItem.getMealName());

        //Set List of Ingredient to Ingredients Adapter
        adapter.setIngredientList(ingredientDTOList);
        ingredientItemRV.setAdapter(adapter);

        //For Appending  Meal Recipe Content
        recipeContent.setText(mealItem.getStrInstructions());

        //For Appending  Meal Recipe Video
        if (!mealItem.getStrYoutube().isEmpty()) {
            videoContentPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = getVideoIdFromUrl(mealItem.getStrYoutube());
                    youTubePlayer.loadVideo(videoId, 0F);
                    youTubePlayer.pause();
                }
            });
        }

        // Handling add Meal to favourite
        addMealToFavouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add the item to the database
                if (!clicked) {
                    mealDetailsPresenter.addMealToFavourite(mealItemDto);
                    addMealToFavouriteBtn.setImageResource(R.drawable.baseline_favorite);
                    Toast.makeText(getApplicationContext(), "Added To favourite", Toast.LENGTH_SHORT).show();
                    clicked = true;
                } else {
                    mealDetailsPresenter.removeMealFromFavourite(mealItemDto);
                    addMealToFavouriteBtn.setImageResource(R.drawable.baseline_favorite_border);
                    Toast.makeText(getApplicationContext(), "Removed From favourite", Toast.LENGTH_SHORT).show();
                    clicked = false;
                }

            }
        });

        // Handling add Meal to Calender Plan For Weak
        addToCalendar.setOnClickListener(view -> {

            /*TODO-Authentication check whether user a guest or not*/
            //append a weak dialog
            appendWeakDialog();

        });

    }

    private void appendWeakDialog() {
        List<String> weekDays = Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        ArrayAdapter<String> weakAdapter = new ArrayAdapter<>(this, R.layout.weakly_item_check_layout, weekDays);
        new AlertDialog.Builder(this)
                .setTitle("Choose a day")
                .setSingleChoiceItems(weakAdapter, 0, (dialog, which) -> selectedItemIndex = which)
                .setPositiveButton("Ok", (dialog, which) ->
                {
                    if (selectedItemIndex >= 0) {
                        String selectedDayItem = weekDays.get(selectedItemIndex);
                        Toast.makeText(this, "Added to" + selectedDayItem, Toast.LENGTH_SHORT).show();
                        mealPlanDTO.setDay(selectedDayItem);
                        mealDetailsPresenter.addToWeakPlan(mealPlanDTO);
                    }
                }).setNegativeButton("Cancel", null)
                .show();

    }

    private String getVideoIdFromUrl(String strYoutube) {
        String id = null;
        Pattern pattern = Pattern.compile("(?<=v(=|/))([\\w-]+)");
        Matcher matcher = pattern.matcher(strYoutube);
        if (matcher.find()) {
            id = matcher.group();
        }
        return id;
    }

    //add Ingredient data to the Ingredient list
    private List<IngredientDTO> initIngredientList(MealsItemDTO mealsItemDTO) {
        List<IngredientDTO> ingredientList = new ArrayList<>();
        if (mealsItemDTO != null) {
            if ((mealsItemDTO.getStrIngredient1() != null) && !mealsItemDTO.getStrIngredient1().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient1(), mealsItemDTO.getStrMeasure1()));
            }
            if ((mealsItemDTO.getStrIngredient2() != null) && !mealsItemDTO.getStrIngredient2().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient2(), mealsItemDTO.getStrMeasure2()));
            }
            if ((mealsItemDTO.getStrIngredient3() != null) && !mealsItemDTO.getStrIngredient3().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient3(), mealsItemDTO.getStrMeasure3()));
            }
            if ((mealsItemDTO.getStrIngredient4() != null) && !mealsItemDTO.getStrIngredient4().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient4(), mealsItemDTO.getStrMeasure4()));
            }
            if ((mealsItemDTO.getStrIngredient5() != null) && !mealsItemDTO.getStrIngredient5().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient5(), mealsItemDTO.getStrMeasure5()));
            }
            if ((mealsItemDTO.getStrIngredient6() != null) && !mealsItemDTO.getStrIngredient6().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient6(), mealsItemDTO.getStrMeasure6()));
            }
            if ((mealsItemDTO.getStrIngredient7() != null) && !mealsItemDTO.getStrIngredient7().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient7(), mealsItemDTO.getStrMeasure7()));
            }
            if ((mealsItemDTO.getStrIngredient8() != null) && !mealsItemDTO.getStrIngredient8().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient8(), mealsItemDTO.getStrMeasure8()));
            }
            if ((mealsItemDTO.getStrIngredient9() != null) && !mealsItemDTO.getStrIngredient9().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient9(), mealsItemDTO.getStrMeasure9()));
            }
            if ((mealsItemDTO.getStrIngredient10() != null) && !mealsItemDTO.getStrIngredient10().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient10(), mealsItemDTO.getStrMeasure10()));
            }
            if ((mealsItemDTO.getStrIngredient11() != null) && !mealsItemDTO.getStrIngredient11().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient11(), mealsItemDTO.getStrMeasure11()));
            }
            if ((mealsItemDTO.getStrIngredient12() != null) && !mealsItemDTO.getStrIngredient12().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient12(), mealsItemDTO.getStrMeasure12()));
            }
            if ((mealsItemDTO.getStrIngredient13() != null) && !mealsItemDTO.getStrIngredient13().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient13(), mealsItemDTO.getStrMeasure13()));
            }
            if ((mealsItemDTO.getStrIngredient14() != null) && !mealsItemDTO.getStrIngredient14().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient14(), mealsItemDTO.getStrMeasure14()));
            }
            if ((mealsItemDTO.getStrIngredient15() != null) && !mealsItemDTO.getStrIngredient15().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient15(), mealsItemDTO.getStrMeasure15()));
            }
            if ((mealsItemDTO.getStrIngredient16() != null) && !mealsItemDTO.getStrIngredient16().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient16(), mealsItemDTO.getStrMeasure16()));
            }
            if ((mealsItemDTO.getStrIngredient17() != null) && !mealsItemDTO.getStrIngredient17().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient17(), mealsItemDTO.getStrMeasure17()));
            }
            if ((mealsItemDTO.getStrIngredient18() != null) && !mealsItemDTO.getStrIngredient18().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient18(), mealsItemDTO.getStrMeasure18()));
            }
            if ((mealsItemDTO.getStrIngredient19() != null) && !mealsItemDTO.getStrIngredient19().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient19(), mealsItemDTO.getStrMeasure19()));
            }
            if ((mealsItemDTO.getStrIngredient20() != null) && !mealsItemDTO.getStrIngredient20().isEmpty()) {
                ingredientList.add(new IngredientDTO(mealsItemDTO.getStrIngredient20(), mealsItemDTO.getStrMeasure20()));
            }
        }

        return ingredientList;
    }

    private void initViews() {
        itemImage = findViewById(R.id.itemImage);
        mealName = findViewById(R.id.meal_name);
        addMealToFavouriteBtn = findViewById(R.id.addMealToFavouriteBtn);
        addToCalendar = findViewById(R.id.addToCalender);
        recipeContent = findViewById(R.id.recipeContent);
        videoContentPlayer = findViewById(R.id.videoContentPlayer);

        ingredientItemRV = findViewById(R.id.ingredientItemRV);

        ingredientDTOList = initIngredientList(mealItemDto);

        adapter = new MealsDetailsRecyclerAdapter(getApplicationContext(), new ArrayList<>());
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        ingredientItemRV.setLayoutManager(layoutManager);


        mealDetailsPresenter = new MealsDetailsPresenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(this)));

    }

}