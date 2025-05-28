package model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import model.meal.IMenuItem;
import service.OrderService;

public class Cafe {

	private Menu menu;
	private OrderService orderService;

	private String promocode;
	private BigDecimal promocodeDiscount;

	public Cafe(Menu menu, OrderService orderService) {
		this.menu = menu;
		this.orderService = orderService;
	}

	public Menu getMenu() {
		return menu;
	}

	public int createOrder() {
		return orderService.createOrder();
	}

	public void setPromocode(String promocode, BigDecimal promocodeDiscount) {
		this.promocode = promocode;
		this.promocodeDiscount = promocodeDiscount;
	}

	public BigDecimal getOrderTotalPrice(int orderId) {
		Optional<Order> order = orderService.getOrder(orderId);
		if (order.isPresent())
			return order.get().getTotalPrice();
		throw new NoSuchElementException("Order with ID " + orderId + " not found.");
	}

	public BigDecimal getOrderTotalPrice(int orderId, String promocode) {

		if (promocode.equals(this.promocode))
		{	
			Optional<Order> order = orderService.getOrder(orderId);
			if (order.isPresent())
				return order.get().getTotalPrice(promocodeDiscount);
			throw new NoSuchElementException("Order with ID " + orderId + " not found.");
		}
		else
			throw new NoSuchElementException("Uncorrect promocode");
	}

	public Map<IMenuItem, Integer> getMenuItemsByOrder(int orderId) {
		Optional<Order> order = orderService.getOrder(orderId);
		if (order.isPresent())
			return order.get().getItems();;
		throw new NoSuchElementException("Order with ID " + orderId + " not found.");
	}

	public void addMenuItemToOrder(int orderId, String itemName, int menuItemCount) {
		menu.getItem(itemName).ifPresent(menuItem -> {
			orderService.addMenuItem(orderId, menuItem, menuItemCount);
		});
	}

	public boolean checkPromocode(String promocode) {
		return Objects.equals(this.promocode, promocode);
	}
}