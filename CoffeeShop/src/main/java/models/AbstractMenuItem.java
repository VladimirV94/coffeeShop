package models;

import java.math.BigDecimal;

public abstract class AbstractMenuItem implements IMenuItem {

	private String name;
	private BigDecimal price;

	protected AbstractMenuItem(String name, double price) {
		this.name = name;
		this.price = new BigDecimal(price);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}
}
