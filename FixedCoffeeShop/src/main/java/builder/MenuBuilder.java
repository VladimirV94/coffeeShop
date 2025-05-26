package builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.MenuItemCategory;
import model.Menu;
import model.meal.Drink;
import model.meal.Food;
import model.meal.IMenuItem;

public class MenuBuilder {

	private MenuItemCategory menuItemCategory;

	List<IMenuItem> items = new ArrayList<>();

	public MenuBuilder setCategory(String menuItemCategory) {
		this.menuItemCategory = new MenuItemCategory(menuItemCategory);
		return this;
	}
	
	public MenuBuilder setCategory(String menuItemCategory, double discount) {
		this.menuItemCategory = new MenuItemCategory(menuItemCategory, new BigDecimal(discount));
		return this;
	}

	public MenuBuilder createFood(String name, double price, double calories) {
		
		items.add(new Food(name, price, calories, menuItemCategory));
		return this;
	}

	public MenuBuilder createDrink(String name, double price, double volume) {
		items.add(new Drink(name, price, volume, menuItemCategory));
		return this;
	}

	public Menu createAndFillMenu() {
		return new Menu(items);
	}
}
