package com.mockito.yash.TeaCoffeeVendingMachine;

@FunctionalInterface
interface TeaCoffeeVendingMachine {

	PriceAndIngredients prepareDrink(DrinksAvailable ingredients,Integer numberOfDrinksOrdered);
}
