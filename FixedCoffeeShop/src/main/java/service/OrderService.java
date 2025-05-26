package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import javax.management.AttributeNotFoundException;
import javax.naming.LimitExceededException;

import model.Order;
import model.meal.IMenuItem;

public class OrderService {

	Map<Order, List<IMenuItem>> orders = new HashMap<>();

	public int createOrder() {
		int orderId = getFreeId();
		orders.put(new Order(orderId), new ArrayList<>());
		return orderId;
	}

	public Order getOrder(int orderId) throws AttributeNotFoundException {
		return orders.keySet().stream()
			.filter(order -> order.getId() == orderId)
			.findFirst()
			.orElseThrow(() -> new AttributeNotFoundException("Order id not found"));
	}

	private int getFreeId() {
		int freeId = 1;
		try {
			List<Integer> ordersId = orders.keySet().stream().map(Order::getId).toList();
			return IntStream.range(1, Integer.MAX_VALUE)
				.filter(id -> !ordersId.contains(id))
				.findFirst()
				.orElseThrow(() -> new LimitExceededException("All possible order's id are reserved"));
		} catch (LimitExceededException e) {
			e.printStackTrace();
		}
		return freeId;
	}

	public void addMenuItem(int orderId, IMenuItem menuItem, int menuItemCount) throws AttributeNotFoundException {
		Order orderById = getOrder(orderId);
		if (orderById != null) {
			orderById.addItem(menuItem, menuItemCount);
		}
	}

	public BigDecimal getPrice(int orderId) throws AttributeNotFoundException {
		Order order = getOrder(orderId);
		if (order != null)
			return order.getTotalPrice();
		throw new AttributeNotFoundException(orderId + ": not found");
	}

	public BigDecimal getPrice(int orderId, BigDecimal promocodeDiscount) throws AttributeNotFoundException {
		Order order = getOrder(orderId);
		if (order != null) {
			return order.getTotalPrice(promocodeDiscount);
		}
		throw new AttributeNotFoundException(orderId + ": not found");
	}
}