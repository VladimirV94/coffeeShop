package services;

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

import javax.naming.LimitExceededException;

import models.Basket;
import models.IMenuItem;
import models.Menu.Category;
import models.Order;

public class OrderService {

	private DiscountService discountService;

	public OrderService(DiscountService discountService) {
		this.discountService = discountService;
	}

	Map<Order, List<IMenuItem>> orders = new HashMap<>();

	public int createOrder() {
		int orderId = getFreeId();
		orders.put(new Order(orderId), new ArrayList<>());
		return orderId;
	}

	public boolean removeOrder(int orderId) {

		Order orderById = findOrderById(orderId);
		if (orderById != null) {
			orders.remove(orderById);
			return true;
		}
		return false;
	}

	private int getFreeId() {
		int freeId = 1;
		try {
			List<Integer> ordersId = orders.keySet().stream().map(Order::getId).toList();
			return IntStream.range(1, Integer.MAX_VALUE).filter(id -> !ordersId.contains(id)).findFirst()
					.orElseThrow(() -> new LimitExceededException("all possible order's id are reserved"));
		} catch (LimitExceededException e) {
			e.printStackTrace();
		}
		return freeId;
	}

	public void addMenuItemToOrder(int orderId, IMenuItem menuItem) {
		Objects.requireNonNull(menuItem);
		Order orderById = findOrderById(orderId);
		if (orderById != null) {
			orderById.addItem(menuItem);
		}
	}

	public BigDecimal getOriginalPrice(int orderId) {
		Order orderById = findOrderById(orderId);
		if (orderById != null) {
			orderById.getTotalPrice();
		}
		return new BigDecimal(0);
	}

	public BigDecimal getOriginalPrice(IMenuItem menuItem, int count) {
		return menuItem.getPrice().multiply(new BigDecimal(count));
	}

	public BigDecimal getDiscountPrice(Category itemCategory, IMenuItem menuItem, int count) {
		return discountService.calculatePrice(itemCategory, menuItem, count);
	}

	public Map<IMenuItem, BigDecimal> getDiscountPrice(Basket basket, String promocode) {
		return discountService.calculatePrice(basket, promocode);
	}

	public Collection<IMenuItem> getOrderMenuItems(int orderId) {
		Order orderById = findOrderById(orderId);
		if (orderById != null) {
			return orderById.getItems();
		}
		return Collections.emptyList();
	}

	private Order findOrderById(int orderId) {
		return orders.keySet().stream().filter(order -> order.getId() == orderId).findFirst().orElse(null);
	}
}
