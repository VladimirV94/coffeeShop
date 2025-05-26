package model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import javax.management.AttributeNotFoundException;

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

	public BigDecimal getOrderTotalPrice(int orderId) throws AttributeNotFoundException {
		return orderService.getOrder(orderId).getTotalPrice();
	}

	public BigDecimal getOrderTotalPrice(int orderId, String promocode) throws AttributeNotFoundException {

		if (promocode.equals(this.promocode))
			return orderService.getOrder(orderId).getTotalPrice(promocodeDiscount);
		else
			throw new IllegalArgumentException("Uncorrect promocode");
	}

	public Map<IMenuItem, Integer> getMenuItemsByOrder(int order_id) throws AttributeNotFoundException {
		return orderService.getOrder(order_id).getItems();
	}

	public void addMenuItemToOrder(int orderId, String itemName, int menuItemCount) {
		menu.getItem(itemName).ifPresent(menuItem -> {
			try {
				orderService.addMenuItem(orderId, menuItem, menuItemCount);
			} catch (AttributeNotFoundException e) {
				e.printStackTrace();
			}
		});
	}

	public boolean checkPromocode(String promocode) {
		return Objects.equals(this.promocode, promocode);
	}
}