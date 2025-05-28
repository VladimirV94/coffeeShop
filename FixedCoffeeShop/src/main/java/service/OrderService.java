package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import model.Order;
import model.meal.IMenuItem;

public class OrderService {

	Map<Order, List<IMenuItem>> orders = new HashMap<>();

	private static int idCounter = 0;
	
	public int createOrder() {
		int orderId = getFreeId();
		orders.put(new Order(orderId), new ArrayList<>());
		return orderId;
	}

	public Optional<Order> getOrder(int orderId) {
		return orders.keySet().stream()
			.filter(order -> order.getId() == orderId)
			.findFirst();
	}

	private int getFreeId() {
		return idCounter++;
	}

	public void addMenuItem(int orderId, IMenuItem menuItem, int menuItemCount) {
		getOrder(orderId)
			.ifPresent(order -> order.addItem(menuItem, menuItemCount));
	}

	public BigDecimal getPrice(int orderId) {
		Optional<Order> order = getOrder(orderId);
		if (order.isPresent())
			return order.get().getTotalPrice();
		throw new NoSuchElementException("Order with ID " + orderId + " not found.");
	}

	public BigDecimal getPrice(int orderId, BigDecimal promocodeDiscount) {
		Optional<Order> order = getOrder(orderId);
		if (order.isPresent()) {
			return order.get().getTotalPrice(promocodeDiscount);
		}
		throw new NoSuchElementException("Order with ID " + orderId + " not found.");
	}
}