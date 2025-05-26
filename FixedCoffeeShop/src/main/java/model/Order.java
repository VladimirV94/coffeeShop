package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.meal.IMenuItem;

public class Order {

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	Map<IMenuItem, Integer> items = new LinkedHashMap<>();

	private final int id;

	public Order(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * Добавить позицию в заказ. Если позиция уже есть в заказе, то будет сделан
	 * инкремент количества данной позиции в заказе
	 * @param item
	 */
	public void addItem(IMenuItem item) {
		Integer oldCount = items.get(item);
		items.put(item, oldCount == null ? 1 : oldCount + 1);
	}
	
	/**
	 * Добавить несколько позиций в заказ. Если позиция уже есть в заказе, то будет сделано
	 * сложение уже существующего количества позиций в заказе с указанным количеством
	 * @param item
	 * @param count
	 */
	public void addItem(IMenuItem item, int count) {
		Integer oldCount = items.get(item);
		items.put(item, oldCount == null ? count : oldCount + count);
	}

	/**
	 * Получить позиции заказа и их количество
	 * @return
	 */
	public Map<IMenuItem, Integer> getItems() {
		return new LinkedHashMap<>(items);
	}

	/**
	 * Получить цену заказа с учётом скидок за категории
	 * 
	 * @return цена заказа
	 */
	public BigDecimal getTotalPrice() {

		BigDecimal totalPrice = new BigDecimal(0);

		for (var entry : items.entrySet()) {
			IMenuItem menuItem = entry.getKey();
			BigDecimal count = new BigDecimal(entry.getValue());
			totalPrice = totalPrice.add(menuItem.getDiscountPrice().multiply(count));
		}
		return totalPrice;
	}

	/**
	 * Получить цену заказа с учётом скидок за категории после чего применить скидку
	 * за промокод
	 * @param promocode промокод
	 * @return цена заказа
	 */
	public BigDecimal getTotalPrice(BigDecimal promocodeDiscount) {
		BigDecimal totalPrice = getTotalPrice();
		return totalPrice.subtract(promocodeDiscount.divide(ONE_HUNDRED).multiply(totalPrice));
	}
}