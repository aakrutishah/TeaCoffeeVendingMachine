package com.mockito.yash.TeaCoffeeVendingMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class TeaCoffeeVendingMachineImpl implements TeaCoffeeVendingMachine {

	private static final Logger logger = Logger.getLogger(TeaCoffeeVendingMachineImpl.class.getName());
	private MyScanner scanner = new MyScanner();
	private ContainerCapacity containerCapacity = new ContainerCapacity();

	public void getChoice() {
		Integer choice = 0, numberOfDrinksOrdered;
		PriceAndIngredients priceAndIngredients = null;

		do {
			printMenuForDrinks();
			logger.info("Enter Choice:");
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				logger.info("Enter Number of Tea:");
				numberOfDrinksOrdered = scanner.nextInt();
				priceAndIngredients = prepareDrink(DrinksAvailable.TEA, numberOfDrinksOrdered);
				displayOrderIngredientsAndPrice(priceAndIngredients);
				break;
			case 2:
				logger.info("Enter number of Coffee:");
				numberOfDrinksOrdered = scanner.nextInt();
				priceAndIngredients = prepareDrink(DrinksAvailable.COFFEE, numberOfDrinksOrdered);
				displayOrderIngredientsAndPrice(priceAndIngredients);
				break;
			case 3:
				logger.info("Enter number of Black Tea:");
				numberOfDrinksOrdered = scanner.nextInt();
				priceAndIngredients = prepareDrink(DrinksAvailable.BLACKTEA, numberOfDrinksOrdered);
				displayOrderIngredientsAndPrice(priceAndIngredients);
				break;
			case 4:
				logger.info("Enter number of Black Coffee:");
				numberOfDrinksOrdered = scanner.nextInt();
				priceAndIngredients = prepareDrink(DrinksAvailable.BLACKCOFFEE, numberOfDrinksOrdered);
				displayOrderIngredientsAndPrice(priceAndIngredients);
				break;
			case 5:
				getContainerChoice();
				break;
			case 6:
				containerCapacity.setDefaultContainerCapacity();
				logger.info("Containers has been reset successfully!");
				break;
			case 7:
				printContainerStatus();
				break;
			case 8:
				printAllReports();
				break;
			case 9:
				logger.info("====================Thank-you====================");
				break;
			default:
				logger.warn("Wrong Choice,Please try again!");
			}
		} while (choice != 9);
	}

	public void displayOrderIngredientsAndPrice(PriceAndIngredients priceAndIngredients) {
		logger.info("PRICE: " + priceAndIngredients.getPrice() + "\nINGREDIENTS: ");
		priceAndIngredients.getIngredientsItems()
				.forEach(ingredient -> logger.info(ingredient.getIngredientName() + "\t"));
		containerCapacity.setAllOrders(priceAndIngredients);
	}

	public PriceAndIngredients prepareDrink(DrinksAvailable drinksAvailable, Integer numberOfDrinksOrdered) {
		List<IngredientsItems> ingredientsItemsList = new ArrayList<IngredientsItems>();

		for (IngredientsItems ingredientsItems : drinksAvailable.getIngredients().getIngredientsItems()) {
			Integer consumptionOfMaterial = numberOfDrinksOrdered * ingredientsItems.getConsumptionOfMaterial();
			Integer wastageOfMaterial = numberOfDrinksOrdered * ingredientsItems.getWastageOfMaterial();

			ingredientsItemsList.add(new IngredientsItems(ingredientsItems.getIngredientName(), (consumptionOfMaterial),
					(wastageOfMaterial)));
			if ((containerCapacity.getContainersCapacityMap().get(ingredientsItems.getIngredientName())
					- (consumptionOfMaterial + wastageOfMaterial)) > 0)
				containerCapacity.getContainersCapacityMap().put(ingredientsItems.getIngredientName(),
						containerCapacity.getContainersCapacityMap().get(ingredientsItems.getIngredientName())
								- (consumptionOfMaterial + wastageOfMaterial));
			else {
				logger.warn("Enough Quantity is not available");
			}
		}

		PriceAndIngredients ingredients = new PriceAndIngredients();
		ingredients.setOrderName(drinksAvailable.name());
		ingredients.setNumberOfDrinksOrdered(numberOfDrinksOrdered);
		ingredients.setIngredientsItems(ingredientsItemsList);
		ingredients.setPrice((numberOfDrinksOrdered * drinksAvailable.getIngredients().getPrice()));
		return ingredients;
	}

	public void getContainerChoice() {
		Integer choice = 0, amountToRefill = 0;
		String key = "";
		do {
			printMenuForContainers();
			logger.info("Enter choice to refill container:");
			choice = scanner.nextInt();
			DrinksAvailable enumValues[] = DrinksAvailable.values();
			key = enumValues[choice].name();
			if (choice < 6) {
				logger.info("Enter amount to refill the container:");
				amountToRefill = scanner.nextInt();
				if (!containerCapacity.checkOverflow(key, amountToRefill)) {
					containerCapacity.getContainersCapacityMap().put(key,
							(containerCapacity.getContainersCapacityMap().get(key) + amountToRefill));
					containerCapacity.setRefillingCounter();
				} else
					logger.info("Container is already full !");
			} else if (choice == 6) {
				logger.info("Thank-you,Refilled Successfully");
			} else {
				logger.warn("Wrong Choice,Please try again!");
			}
		} while (choice != 6);

	}

	public void printAllReports() {
		List<PriceAndIngredients> priceAndIngredientsList = containerCapacity.getAllOrders();

		Map<String, Integer> drinksWisePrice = priceAndIngredientsList.stream().collect(
				Collectors.toMap(PriceAndIngredients::getOrderName, PriceAndIngredients::getPrice, (x, y) -> x + y));
		Map<String, Integer> drinksWiseNumberOfOrders = priceAndIngredientsList.stream().collect(Collectors.toMap(
				PriceAndIngredients::getOrderName, PriceAndIngredients::getNumberOfDrinksOrdered, (x, y) -> x + y));
		// DrinksWise Cups and Cost
		drinksWisePrice.forEach((x, y) -> logger
				.info("Drink: " + x + ", price: " + y + ", number of drinks: " + drinksWiseNumberOfOrders.get(x)));
		// Total Cups and Cost
		Integer priceSum = drinksWisePrice.values().stream().mapToInt(Integer::intValue).sum();
		Integer cupsSum = drinksWiseNumberOfOrders.values().stream().mapToInt(Integer::intValue).sum();
		logger.info("priceSum: " + priceSum);
		logger.info("cupsSum: " + cupsSum);
		// refilling
		logger.info("Refilling Count: " + containerCapacity.getRefillingCounter());
	}

	private void printMenuForDrinks() {
		logger.info("====================Menu====================");
		logger.info("=======Items========\t=======Price========");
		logger.info(
				"1.Tea\t\t\t\t10\n2.Coffee\t\t\t15\n3.BlackTea\t\t\t5\n4.BlackCoffee\t\t\t10\n5.Refill Container\t\t--\n6.Reset Container\t\t--\n7.Check Container Status\t--\n8.Check Total Sale\t\t--\n9.Exit\t\t\t\t--");
	}

	private void printMenuForContainers() {
		logger.info("\n\n\n====================Containers====================");
		logger.info(
				"1.Tea Container\n2.Coffee Container\n3.Water Container\n4.Milk Container\n5.Sugar Container\n6.Exit Refilling");

	}

	private void printContainerStatus() {
		logger.info(containerCapacity.toString());
	}

}
