package model.meal;

import java.math.BigDecimal;

import model.MenuItemCategory;

public abstract class AbstractMenuItem implements IMenuItem {

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	private String name;
	private BigDecimal price;
	private MenuItemCategory category;

	protected AbstractMenuItem(String name, double price, MenuItemCategory category) {
		this.name = name;
		this.price = new BigDecimal(price);
		this.category = category;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public BigDecimal getDiscountPrice() {
		BigDecimal categoryDiscount = category.getDiscount();
		if(categoryDiscount != null)
			return price.subtract(categoryDiscount.divide(ONE_HUNDRED).multiply(price));
		else
			return getPrice();
	}

	@Override
	public MenuItemCategory getCategory() {
		return category;
	}
}
