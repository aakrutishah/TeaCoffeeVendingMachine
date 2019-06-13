package com.mockito.yash.TeaCoffeeVendingMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContainerCapacity {

	private Map<String, Integer> containersCapacityMap = new HashMap<String, Integer>();
	private List<PriceAndIngredients> allOrders = new ArrayList<PriceAndIngredients>();
	private Integer refillingCounter = 0;
	//private Map<String, Integer> refillingCounter = new HashMap<String, Integer>();

	public ContainerCapacity() {
		this.containersCapacityMap = getDefaultContainerCapacity();
	}

	@Override
	public String toString() {
		return this.getContainersCapacityMap().toString();

	}

	public Map<String, Integer> getContainersCapacityMap() {
		return containersCapacityMap;
	}

	public List<PriceAndIngredients> getAllOrders() {
		return allOrders;
	}

	public void setAllOrders(PriceAndIngredients allOrders) {
		this.getAllOrders().add(allOrders);
	}

	public void setDefaultContainerCapacity() {
		this.containersCapacityMap = getDefaultContainerCapacity();
	}
	
	public Integer getRefillingCounter() {
		return refillingCounter;
	}

	public void setRefillingCounter() {
		this.refillingCounter = this.getRefillingCounter() + 1;
	}
	
	@SuppressWarnings("serial")
	public Map<String, Integer> getDefaultContainerCapacity() {

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

	public boolean checkOverflow(String key, Integer amountToRefill) {
		if (this.getContainersCapacityMap().get(key) + amountToRefill > this.getDefaultContainerCapacity().get(key))
			return true;
		return false;
	}

}
