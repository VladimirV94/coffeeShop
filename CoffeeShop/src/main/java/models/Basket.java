package models;

import java.util.LinkedHashMap;
import java.util.Map;

import models.Menu.Category;

public class Basket {

	private Map<IMenuItem, Integer> items = new LinkedHashMap<>();
	private Menu menu;

	public Basket(Menu menu) {
		this.menu = menu;
	}

	public Map<IMenuItem, Integer> getItems() {
		return items;
	}

	public void put(IMenuItem menuItem, int count) {
		items.put(menuItem, count);
	}

	public void remove(IMenuItem menuItem) {
		items.remove(menuItem);
	}

	public void clear() {
		items.clear();
	}

	public Category getCategory(IMenuItem menuItem) {
		return menu.getCategory(menuItem);
	}
}