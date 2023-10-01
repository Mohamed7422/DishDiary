package com.example.dishdiary.data.network;

public interface RemoteSource {
    //make retrofit builder
    void makeRandomMealsCall(NetworkDelegate callback);
    void getCategories(NetworkDelegate callback);
    void getCountries(NetworkDelegate callback);
    void getIngredients(NetworkDelegate callback);
    /*****************************************/
    void filterByFirstLetter(String fLetter,NetworkDelegate callback);
    void filterByName(String mealName,NetworkDelegate callback);
    void getMealByID(int mealId,NetworkDelegate callback);
    void filterByCountry(String query,NetworkDelegate callback);
    void filterByIngredient(String query,NetworkDelegate callback);
    void filterByCategory(String query,NetworkDelegate callback);
}
