package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.CountryDTO;

import java.util.List;

public class CountriesResponse{
	public void setMeals(List<CountryDTO> countries) {
		this.meals = countries;
	}

	private List<CountryDTO> meals;

	public List<CountryDTO> getCountries(){
		return meals;
	}
}