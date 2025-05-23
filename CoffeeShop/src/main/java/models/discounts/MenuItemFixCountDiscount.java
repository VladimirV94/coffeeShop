package models.discounts;

import java.math.BigDecimal;

import models.IMenuItem;

public class MenuItemFixCountDiscount extends AbstractMenuItemDiscount implements IFixCountDiscount{
	
	private BigDecimal fixCount;
	private FixCountDelegate fixCountDelegate;

	public MenuItemFixCountDiscount(IMenuItem menuItem, BigDecimal fixCount, boolean sumWithOthers) {
		super(menuItem, sumWithOthers);
		fixCountDelegate = new FixCountDelegate();
		this.fixCount = fixCount;
	}

	@Override
	public BigDecimal fixCount() {
		return fixCount;
	}

	@Override
	public BigDecimal getDiscountPrice() {
		return fixCountDelegate.calculatePrice(menuItem.getPrice(), fixCount);
	}
}
