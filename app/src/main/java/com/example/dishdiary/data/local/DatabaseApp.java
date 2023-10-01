package com.example.dishdiary.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

@Database(entities = {MealsItemDTO.class, MealPlanDTO.class},version =1)
public abstract class DatabaseApp extends RoomDatabase {
    private static DatabaseApp instance = null;

        public abstract Dao dao();
    public static synchronized DatabaseApp getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , DatabaseApp.class,"mealsDataBase").build();

        }
        return instance;
    }}
