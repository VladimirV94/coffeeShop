package launch;

import java.math.BigDecimal;

import app.CafeApp;
import builder.MenuBuilder;
import model.Cafe;
import model.Menu;
import service.OrderService;


public class LaunchCafe {

	public static void main(String[] args) {

		OrderService orderService = new OrderService();

		Menu menu = new MenuBuilder()
			.setCategory("Food")
			.createFood("Pizza", 100, 150)
			.createFood("Sushi", 300, 50)
			.createFood("Burger", 150, 100)
			.setCategory("Drink", 10)
			.createDrink("Espresso", 100, 40)
			.createDrink("Americano", 120, 30)
			.setCategory("Desserts")
			.createFood("Ice cream", 100, 100)
			.createDrink("Cocktail", 200, 250)
			.createAndFillMenu();

		Cafe cafe = new Cafe(menu, orderService);
		cafe.setPromocode("WELCOME10", new BigDecimal(10));
		new CafeApp(cafe).start();
	}
}
