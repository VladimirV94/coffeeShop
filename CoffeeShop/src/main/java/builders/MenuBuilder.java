package builders;

import models.Menu;
import models.Menu.Category;
import models.meal.Drink;
import models.meal.Food;

public class MenuBuilder {

	private Menu menu = new Menu();
	private Category menuItemCategory = Category.OTHER;

	public MenuBuilder setCategory(Category menuItemCategory) {
		this.menuItemCategory = menuItemCategory;
		return this;
	}

	public MenuBuilder createAndAddFood(String name, double price, double calories) {
		menu.addItem(menuItemCategory, new Food(name, price, calories));
		return this;
	}

	public MenuBuilder createAndAddDrink(String name, double price, double volume) {
		menu.addItem(menuItemCategory, new Drink(name, price, volume));
		return this;
	}

	public Menu getMenu() {
		return menu;
	}
}
