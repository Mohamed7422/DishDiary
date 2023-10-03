package com.example.dishdiary.data.remote;

public interface RemoteSource {
    //make retrofit builder
    void getDailyMeal(NetworkDelegate callback);
    void getCategories(NetworkDelegate callback);
    void getCountries(NetworkDelegate callback);
    void getIngredients(NetworkDelegate callback);
    /*****************************************/
    void filterByFirstLetter(String fLetter,FilterNetworkDelegate callback);
    void filterByName(String mealName,FilterNetworkDelegate callback);
    void getMealByID(int mealId,FilterNetworkDelegate callback);
    void filterByCountry(String query,FilterNetworkDelegate callback);
    void filterByIngredient(String query,FilterNetworkDelegate callback);
    void filterByCategory(String query,FilterNetworkDelegate callback);
}
