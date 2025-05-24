package models;

import java.math.BigDecimal;
import java.util.Map;

import models.Menu.Category;
import services.DiscountService;
import services.MenuService;
import services.OrderService;

public class Cafe {

	private boolean isCafeOpen = true;
	private DiscountService discountService;
	private OrderService orderService;
	private MenuService menuService;

	public Cafe(DiscountService discountService, OrderService orderService, MenuService menuService) {
		this.discountService = discountService;
		this.orderService = orderService;
		this.menuService = menuService;
	}

	public Menu getMenu() {
		return menuService.getСurrentMenu();
	}

	public BigDecimal getOriginalOrderPrice(int orderId) {
		return orderService.getOriginalPrice(orderId);
	}

	public BigDecimal getOriginalPrice(IMenuItem menuItem, int count) {
		return orderService.getOriginalPrice(menuItem, count);
	}

	public BigDecimal getDiscountPrice(IMenuItem menuItem, int count) {

		Category itemCategory = menuService.getСurrentMenu().getCategory(menuItem);
		return orderService.getDiscountPrice(itemCategory, menuItem, count);
	}

	public Map<IMenuItem, BigDecimal> getDiscountPrice(Basket basket, String promocode) {
		return orderService.getDiscountPrice(basket, promocode);
	}

	public int createOrder() {
		return orderService.createOrder();
	}

	public boolean removeorder(int orderId) {
		return orderService.removeOrder(orderId);
	}

	public void open() {
		isCafeOpen = true;
	}

	public void close() {
		isCafeOpen = false;
	}

	public boolean isOpen() {
		return isCafeOpen;
	}
}