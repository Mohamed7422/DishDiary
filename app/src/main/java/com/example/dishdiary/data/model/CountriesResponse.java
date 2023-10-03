package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.CountryDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse{
	public void setMeals(List<CountryDTO> countries) {
		this.meals = countries;
	}

	@SerializedName("meals")
	private List<CountryDTO> meals;

	public List<CountryDTO> getCountries(){
		return meals;
	}
}