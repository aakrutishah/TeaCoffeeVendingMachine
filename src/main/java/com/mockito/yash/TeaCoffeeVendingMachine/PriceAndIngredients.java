package com.mockito.yash.TeaCoffeeVendingMachine;

import java.util.List;

public class PriceAndIngredients {
	private String orderName;
	private List<IngredientsItems> ingredientsItems;
	private Integer price;
	private Integer numberOfDrinksOrdered;

	public PriceAndIngredients(List<IngredientsItems> list, Integer price) {
		this.ingredientsItems = list;
		this.price = price;
	}

	public PriceAndIngredients() {

	}

	public List<IngredientsItems> getIngredientsItems() {
		return ingredientsItems;
	}

	public void setIngredientsItems(List<IngredientsItems> ingredientsItems) {
		this.ingredientsItems = ingredientsItems;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setNumberOfDrinksOrdered(Integer numberOfDrinksOrdered) {
		this.numberOfDrinksOrdered = numberOfDrinksOrdered;
	}

	public Integer getNumberOfDrinksOrdered() {
		return numberOfDrinksOrdered;
	}
	
	
}
