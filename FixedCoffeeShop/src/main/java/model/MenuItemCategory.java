package model;

import java.math.BigDecimal;

public class MenuItemCategory {

	private String name;
	private BigDecimal discount;

	public MenuItemCategory(String name) {
		this.name = name;
	}

	public MenuItemCategory(String name, BigDecimal discount) {
		this.name = name;
		this.discount = discount;
	}

	public BigDecimal getDiscount()
	{
		return discount;
	}

	public BigDecimal setDiscount(BigDecimal discount)
	{
		return discount;
	}

	@Override
	public String toString() {
		return name;
	}
}
