package com.mockito.yash.TeaCoffeeVendingMachine;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("serial")
public class TeaCoffeeVendingMachineImplTest {

	@InjectMocks
	private TeaCoffeeVendingMachineImpl teaCoffeeVendingMachineImpl;

	@Mock
	private MyScanner scanner;

	@Mock
	private ContainerCapacity containerCapacity;

	@Mock
	private PriceAndIngredients priceAndIngredients;

	private Appender appenderMock;

	@Before
	public void setupAppender() {
		appenderMock = mock(Appender.class);
		Logger.getRootLogger().addAppender(appenderMock);
	}

	@After
	public void removeAppender() {
		Logger.getRootLogger().removeAppender(appenderMock);
	}

	@Test
	public void shouldTakeOrderForTeaWhenChoiceIsOne() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(1, 5, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(15)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();

	}

	@Test
	public void shouldTakeOrderForCoffeeWhenChoiceIsTwo() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(2, 5, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(15)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();
	}

	@Test
	public void shouldTakeOrderForBlackTeaWhenChoiceIsThree() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(3, 5, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(14)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();
	}

	@Test
	public void shouldTakeOrderForBlackCoffeeWhenChoiceIsFour() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(4, 5, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(14)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();
	}

	@Test
	public void shouldGiveMenuAgainToUserWhenChoiceIsWrong() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(11, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(10)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(2)).nextInt();
	}

	@Test
	public void shouldReturnPriceAndIngredientsWhenOrderingForAnyDrink() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);

		PriceAndIngredients priceAndIngredients =teaCoffeeVendingMachineImpl.prepareDrink(DrinksAvailable.TEA, 3);
		
	}
	
	@Test
	public void shouldReturnPriceAndIngredientsWhenOrderingForAnyDrinkWithNotEnoughQuantity() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);

		PriceAndIngredients priceAndIngredients =teaCoffeeVendingMachineImpl.prepareDrink(DrinksAvailable.TEA, 10000);
		
		
	}

	@Test
	public void shouldDisplayPriceAndIngredientsWhenOrderingForAnyDrink() {
		List<IngredientsItems> ingredientsItemsList = Arrays.asList(new IngredientsItems("COFFEE", 4, 1),
				new IngredientsItems("WATER", 20, 3), new IngredientsItems("MILK", 80, 8),
				new IngredientsItems("SUGAR", 15, 2));
		Integer price = 15;

		when(priceAndIngredients.getIngredientsItems()).thenReturn(ingredientsItemsList);
		when(priceAndIngredients.getPrice()).thenReturn(price);
		when(containerCapacity.getAllOrders()).thenReturn(new ArrayList<PriceAndIngredients>());
		doCallRealMethod().when(containerCapacity).setAllOrders(any(PriceAndIngredients.class));

		teaCoffeeVendingMachineImpl.displayOrderIngredientsAndPrice(priceAndIngredients);

		verify(containerCapacity, times(1)).setAllOrders(priceAndIngredients);
		verify(containerCapacity).setAllOrders(priceAndIngredients);
		verify(appenderMock, times(5)).doAppend((LoggingEvent) anyObject());

	}

	@Test
	public void shouldRefillTheSelectedContainerWhenChoiceIsFive() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(5, 6, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(13)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();
		
	}

	@Test
	public void shouldGiveMessageContainerIsFullWhenContainerChoiceIsBetweenOneToFive() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(1, 20, 6);
		when(containerCapacity.getDefaultContainerCapacity()).thenReturn(containersCapacity);
		doCallRealMethod().when(containerCapacity).checkOverflow(any(String.class), any(Integer.class));

		teaCoffeeVendingMachineImpl.getContainerChoice();
		
		verify(appenderMock,times(9)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();
		
	}

	@Test
	public void shouldRefillContainerWhenContainerChoiceIsBetweenOneToFive() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(containerCapacity.getDefaultContainerCapacity()).thenReturn(getDefaultContainerCapacity());
		doCallRealMethod().when(containerCapacity).checkOverflow(any(String.class), any(Integer.class));

		when(scanner.nextInt()).thenReturn(1, 5, 5, 1, 20, 6, 9);

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(27)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(7)).nextInt();

	}

	@Test
	public void shouldGiveContainerMenuAgainWhenContainerChoiceIsWrong() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(1, 20, 6);
		when(containerCapacity.checkOverflow("TEA", 20)).thenReturn(false);
		doCallRealMethod().when(containerCapacity).setRefillingCounter();

		teaCoffeeVendingMachineImpl.getContainerChoice();
		
		verify(appenderMock,times(8)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(3)).nextInt();

	}

	@Test
	public void shouldReturnToMainMenuWhenContainerChoiceIsSix() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(6, 20);

		teaCoffeeVendingMachineImpl.getContainerChoice();
		
		verify(appenderMock,times(4)).doAppend((LoggingEvent) anyObject());
		verify(scanner).nextInt();

	}

	@Test
	public void shouldPrintContainerMenuAgainWhenContainerChoiceIsInvalid() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(8, 6);

		teaCoffeeVendingMachineImpl.getContainerChoice();
		
		verify(appenderMock,times(8)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(2)).nextInt();

	}

	@Test
	public void shouldResetTheContainersWhenChoiceIsSix() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(6, 9);
		doCallRealMethod().when(containerCapacity).setDefaultContainerCapacity();

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(10)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(2)).nextInt();

	}

	@Test
	public void shouldPrintContainersStatusWhenChoiceIsSeven() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(7, 9);
		doCallRealMethod().when(containerCapacity).toString();

		teaCoffeeVendingMachineImpl.getChoice();
		
		verify(appenderMock,times(10)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(2)).nextInt();

	}

	@Test
	public void shouldPrintStatisticsReportsWhenChoiceIsEightAndNoOrderIsPlaced() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(scanner.nextInt()).thenReturn(8, 9);

		teaCoffeeVendingMachineImpl.getChoice();

		verify(appenderMock,times(12)).doAppend((LoggingEvent) anyObject());
		verify(scanner,times(2)).nextInt();

	}

	@Test
	public void shouldPrintStatisticsReportsWhenChoiceIsEightAndMultipleOrdersArePlaced() {

		Map<String, Integer> containersCapacity = getDefaultContainerCapacity();
		List<PriceAndIngredients> priceAndIngredients = new ArrayList<PriceAndIngredients>();
		PriceAndIngredients p1 = new PriceAndIngredients();
		p1.setOrderName("TEA");
		p1.setNumberOfDrinksOrdered(2);
		p1.setPrice(10);
		PriceAndIngredients p2 = new PriceAndIngredients();
		p2.setOrderName("COFFEE");
		p2.setNumberOfDrinksOrdered(2);
		p2.setPrice(10);
		PriceAndIngredients p3 = new PriceAndIngredients();
		p3.setOrderName("TEA");
		p3.setNumberOfDrinksOrdered(2);
		p3.setPrice(10);
		priceAndIngredients.add(p1);
		priceAndIngredients.add(p2);
		priceAndIngredients.add(p3);

		when(containerCapacity.getContainersCapacityMap()).thenReturn(containersCapacity);
		when(containerCapacity.getAllOrders()).thenReturn(priceAndIngredients);
		doCallRealMethod().when(containerCapacity).getRefillingCounter();

		teaCoffeeVendingMachineImpl.printAllReports();
		
		verify(appenderMock,times(5)).doAppend((LoggingEvent) anyObject());

	}

	private Map<String, Integer> getDefaultContainerCapacity() {

		Map<String, Integer> containersCapacity = new HashMap<String, Integer>() {
			{
				put("TEA", 2000);
				put("COFFEE", 2000);
				put("SUGAR", 8000);
				put("WATER", 15000);
				put("MILK", 10000);
			}
		};
		return containersCapacity;
	}

}
