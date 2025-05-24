package launches;

import java.util.Collection;

import apps.CustomerCafeApp;
import builders.DiscountFactory;
import builders.MenuBuilder;
import models.Cafe;
import models.Menu;
import models.Menu.Category;
import models.discounts.IDiscount;
import services.DiscountService;
import services.MenuService;
import services.OrderService;

public class LaunchCafe {

	public static void main(String[] args) {

		DiscountService discountService = new DiscountService();
		OrderService orderService = new OrderService(discountService);
		MenuService menuService = new MenuService();

		Menu menu = new MenuBuilder().setCategory(Category.FOOD)
			.createAndAddFood("Pizza", 100, 150)
			.createAndAddFood("Sushi", 300, 50)
			.createAndAddFood("Burger", 150, 100)
			.setCategory(Category.DRINK)
			.createAndAddDrink("Espresso", 100, 40)
			.createAndAddDrink("Americano", 120, 30)
			.setCategory(Category.DESSERTS)
			.createAndAddFood("Ice cream", 100, 100)
			.createAndAddDrink("Cocktail", 200, 250)
			.getMenu();

		Collection<IDiscount> discounts = new DiscountFactory()
			.createPercentDiscount(Category.DRINK, 20, true)
			.setPromoCode("DESSERTS10")
			.createPercentDiscount(Category.DESSERTS, 10, true)
			.getDiscounts();

		discountService.setDiscounts(discounts);
		menuService.setMenu(menu);

		Cafe cafe = new Cafe(discountService, orderService, menuService);

		new CustomerCafeApp(cafe).start();
	}
}
