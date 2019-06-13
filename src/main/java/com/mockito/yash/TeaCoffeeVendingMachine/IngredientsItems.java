package com.mockito.yash.TeaCoffeeVendingMachine;

public class IngredientsItems {
	private String ingredientName;
	private Integer consumptionOfMaterial;
	private Integer wastageOfMaterial;
	
	public String getIngredientName() {
		return ingredientName;
	}

	public Integer getConsumptionOfMaterial() {
		return this.consumptionOfMaterial;
	}

	public Integer getWastageOfMaterial() {
		return this.wastageOfMaterial;
	}

	public IngredientsItems(String ingredientName,Integer consumptionOfMaterial, Integer wastageOfMaterial) {
		this.ingredientName = ingredientName;
		this.consumptionOfMaterial = consumptionOfMaterial;
		this.wastageOfMaterial = wastageOfMaterial;
	}
	
}
