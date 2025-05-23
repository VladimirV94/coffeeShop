package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

	List<IMenuItem> items = new ArrayList<>();

	private final int id;

	public Order(int id)
	{
		this.id = id;
	}

	public void addItem(IMenuItem item)
	{
		items.add(item);
	}

	public void removeItem(IMenuItem item)
	{
		items.remove(item);
	}

	public List<IMenuItem> getItems()
	{
		return new ArrayList<>(items);
	}

	public BigDecimal getTotalPrice()
	{
		return items.stream().map(IMenuItem::getPrice).reduce((a, b) -> a.add(b)).get();
	}

	public int getId()
	{
		return id;
	}
}
