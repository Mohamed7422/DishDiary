package com.example.dishdiary.data.model.dto;

public class IngredientDTO {
    public IngredientDTO() {
    }
    public IngredientDTO(String strIngredient, String strDescription) {
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
    }


    private String idIngredient;
    private String strIngredient;
    private String strDescription;

    private String strType;


    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

}
