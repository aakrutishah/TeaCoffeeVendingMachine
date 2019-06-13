package com.mockito.yash.TeaCoffeeVendingMachine;

import java.util.Arrays;

public enum DrinksAvailable {
	TCVM,
	TEA(new PriceAndIngredients(Arrays.asList(new IngredientsItems("TEA",5,1),new IngredientsItems("WATER",60,5),new IngredientsItems("MILK",40,4),new IngredientsItems("SUGAR",15,2)),10)),
	COFFEE(new PriceAndIngredients( Arrays.asList(new IngredientsItems("COFFEE",4,1),new IngredientsItems("WATER",20,3),new IngredientsItems("MILK",80,8),new IngredientsItems("SUGAR",15,2)),15)),
	WATER,
	MILK,
	SUGAR,
	EXIT,
	BLACKTEA(new PriceAndIngredients(Arrays.asList(new IngredientsItems("TEA",3,0),new IngredientsItems("WATER",100,12),new IngredientsItems("SUGAR",15,2)),5)),
	BLACKCOFFEE(new PriceAndIngredients(Arrays.asList(new IngredientsItems("COFFEE",3,0),new IngredientsItems("WATER",100,12),new IngredientsItems("SUGAR",15,2)),10)),
	RESET,
	REFILL;
	private PriceAndIngredients ingredients;

	public PriceAndIngredients getIngredients() {
		return this.ingredients;
	}
	
	private DrinksAvailable(PriceAndIngredients ingredients) {
		this.ingredients = ingredients;
	}
	
	private DrinksAvailable() {
	}

}
